import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./RecipesPage.css";

const categories = [
  "All","Punjabi","Rajasthani","Gujarati","South Indian","Bengali","Maharashtrian",
];

function RecipesPage() {
  const [activeCategory, setActiveCategory] = useState("All");
  const [recipes, setRecipes] = useState([]); // normalized items: { itemId, name, price, photo, description }
  const [quantities, setQuantities] = useState({}); // keyed by itemId (string)
  const [showPopup, setShowPopup] = useState(false);
  const [formData, setFormData] = useState({ name: "", hostel: "", room: "", time: "" });
  const navigate = useNavigate();

  // Normalize function: converts server shape into stable front-end shape
  const normalizeMenu = (raw) =>
    raw.map((r) => ({
      itemId: r.item_id ?? r.itemId ?? r.itemId,      // support both
      name: r.item_name ?? r.itemName ?? r.name,
      price: r.price ?? 0,
      photo: r.photo ?? r.image ?? r.photoUrl ?? null,
      description: r.description ?? "",
      extras: r.extras ?? "",
      instructions: r.instructions ?? "",
    }));

  // Fetch menu
  useEffect(() => {
    const fetchMenu = async () => {
      try {
        const culture = activeCategory === "All" ? "" : activeCategory;
        const url = `http://localhost:8080/food/menu${culture ? `?culture=${culture}` : ""}`;
        const res = await fetch(url, { credentials: "include" });
        if (!res.ok) {
          console.error("Menu fetch failed:", res.status, await res.text());
          throw new Error("Failed to fetch menu");
        }
        const raw = await res.json();
        const normalized = normalizeMenu(raw);

        // Init quantities only for the returned items; keep previous quantities for others
        const init = { ...quantities };
        normalized.forEach((it) => {
          const key = String(it.itemId);
          if (!(key in init)) init[key] = 0;
        });

        setRecipes(normalized);
        setQuantities(init);
      } catch (err) {
        console.error("fetchMenu error:", err);
        setRecipes([]);
        setQuantities({});
      }
    };
    fetchMenu();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeCategory]);

  const updateCartBackend = async (payload) => {
    // payload must match CartItemDTO: itemId, quantity, price, extras, instructions, photo
    try {
      const res = await fetch("http://localhost:8080/food/cart/add", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        // log body for debugging
        const text = await res.text();
        console.error("cart/add failed:", res.status, text);
      } else {
        // optional: receive updated Cart from server
        const updatedCart = await res.json();
        console.log("cart/add ok, cart:", updatedCart);
      }
    } catch (err) {
      console.error("updateCartBackend error:", err);
    }
  };

  const changeQty = async (itemId, delta) => {
    const key = String(itemId);
    setQuantities((prev) => {
      const newQty = Math.max((prev[key] || 0) + delta, 0);
      const next = { ...prev, [key]: newQty };
      return next;
    });

    // find item details to send required fields to backend
    const item = recipes.find((r) => String(r.itemId) === key);
    const payload = {
      itemId: Number(itemId),
      quantity: Math.max((quantities[String(itemId)] || 0) + delta, 0),
      price: item?.price ?? 0,
      extras: item?.extras ?? "",
      instructions: item?.instructions ?? "",
      photo: item?.photo ?? null
    };

    await updateCartBackend(payload);
  };

  const handleIncrease = (itemId) => changeQty(itemId, 1);
  const handleDecrease = (itemId) => changeQty(itemId, -1);

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmitWater = async (e) => {
    e.preventDefault();
    const waterOrder = {
      itemId: 122,
      quantity: 1,
      extras: "",
      instructions: `Booked by ${formData.name}, Hostel: ${formData.hostel}, Room: ${formData.room}, Time: ${formData.time}`,
      price: 40,
      cutleryRequired: false
    };
    try {
      const res = await fetch("http://localhost:8080/food/order/water", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(waterOrder),
      });
      if (!res.ok) throw new Error("water order failed: " + res.status);
      await res.json();
      alert("Water booked!");
      setShowPopup(false);
    } catch (err) {
      console.error(err);
      alert("Water booking failed");
    }
  };

  const placeOrder = async () => {
    const orders = Object.entries(quantities)
      .filter(([_, q]) => q > 0)
      .map(([k, q]) => ({ itemId: Number(k), quantity: q }));
    if (!orders.length) { alert("Add items"); return; }
    try {
      const res = await fetch("http://localhost:8080/food/order", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ orders }),
      });
      if (!res.ok) {
        console.error("place order failed:", res.status, await res.text());
        throw new Error("Order failed");
      }
      alert("Order placed");
      // reset quantities
      const reset = {};
      recipes.forEach(r => reset[String(r.itemId)] = 0);
      setQuantities(reset);
    } catch (err) {
      console.error(err);
      alert("Order failed");
    }
  };

  return (
    <div className="recipes-wrapper">
      <div className="top-menu">
        <button onClick={() => setShowPopup(true)} className="top-btn">Book Water</button>
        <button onClick={() => navigate("/cart")} className="top-btn">Cart</button>
      </div>

      <div className="recipes-title-container">
        <video className="recipes-video" autoPlay loop muted playsInline>
          <source src="/images/food_video.mp4" type="video/mp4" />
        </video>
        <h1 className="recipes-title">Explore Recipes</h1>
      </div>

      <div className="categories">{categories.map(c => (
        <button key={c} className={`category-btn ${activeCategory === c ? "active" : ""}`}
          onClick={() => setActiveCategory(c)}>{c}</button>
      ))}</div>

      <p>You have <strong>{recipes.length}</strong> recipes</p>

      <div className="recipes-grid">
        {recipes.map(r => (
          <div className="recipe-card" key={String(r.itemId)}>
            <img src={r.photo || "/images/default_food.jpg"} alt={r.name} className="recipe-img" />
            <div className="recipe-content">
              <h3>{r.name}</h3>
              <p>{r.description}</p>
              <p>Price: ₹{r.price}</p>
              <div className="quantity-controls">
                <button onClick={() => handleDecrease(r.itemId)}>-</button>
                <span>{quantities[String(r.itemId)] ?? 0}</span>
                <button onClick={() => handleIncrease(r.itemId)}>+</button>
              </div>
            </div>
          </div>
        ))}
      </div>

      <button onClick={placeOrder} className="place-order-btn">Place Order</button>

      {showPopup && (
  <div className="popup-overlay">
    <div className="popup">
      <h2>Book Water</h2>
      <form className="popup-form" onSubmit={handleSubmitWater}>
        <input
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
          placeholder="Name"
        />
        <input
          name="hostel"
          value={formData.hostel}
          onChange={handleChange}
          required
          placeholder="Hostel"
        />
        <input
          name="room"
          value={formData.room}
          onChange={handleChange}
          required
          placeholder="Room"
        />
        <input
          name="time"
          type="time"
          value={formData.time}
          onChange={handleChange}
          required
        />

        <div className="popup-buttons">
          <button type="submit">Submit</button>
          <button type="button" onClick={() => setShowPopup(false)}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  </div>
)}

    </div>
  );
}

export default RecipesPage;

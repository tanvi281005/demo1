import React, { useEffect, useState } from "react";
import "./CartPage.css";

const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [cutleryRequired, setCutleryRequired] = useState(false);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);

  // fetch cart
  useEffect(() => {
    const fetchCart = async () => {
      try {
        const res = await fetch("http://localhost:8080/food/cart", {
          method: "GET",
          credentials: "include",
        });
        if (res.status === 401) { alert("Login required"); setLoading(false); return; }
        if (!res.ok) throw new Error("cart fetch failed: " + res.status);
        const cart = await res.json();
        // backend should return items with itemId, itemName, price, quantity, photo
        const normalized = (cart.items || []).map(i => ({
          itemId: i.itemId ?? i.item_id,
          itemName: i.itemName ?? i.item_name ?? i.name,
          price: i.price,
          quantity: i.quantity,
          extras: i.extras,
          instructions: i.instructions,
          photo: i.photo ?? i.image
        }));
        setCartItems(normalized);
        setCutleryRequired(!!cart.cutleryRequired);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchCart();
  }, []);

  useEffect(() => {
    const sum = cartItems.reduce((s, it) => s + (it.price || 0) * (it.quantity || 0), 0);
    setTotal(sum);
  }, [cartItems]);

  const updateBackend = async (item) => {
    try {
      await fetch("http://localhost:8080/food/cart/add", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          itemId: item.itemId,
          quantity: item.quantity,
          price: item.price,
          extras: item.extras || "",
          instructions: item.instructions || "",
          photo: item.photo || ""
        }),
      });
    } catch (err) {
      console.error("updateBackend failed", err);
    }
  };

  const changeQty = (itemId, delta) => {
    setCartItems(prev => {
      const next = prev.map(it => {
        if (it.itemId === itemId) {
          const newQ = Math.max(0, (it.quantity || 0) + delta);
          const updated = { ...it, quantity: newQ };
          updateBackend(updated);
          return updated;
        }
        return it;
      });
      return next;
    });
  };

  const removeItem = (itemId) => {
    setCartItems(prev => prev.filter(it => it.itemId !== itemId));
    // inform backend
    updateBackend({ itemId, quantity: 0, price: 0, extras: "", instructions: "", photo: "" });
  };

  const placeOrder = async () => {
    try {
      const orders = cartItems.filter(it => it.quantity > 0).map(it => ({
        itemId: it.itemId,
        quantity: it.quantity,
        price: it.price,
        extras: it.extras,
        instructions: it.instructions
      }));
      const res = await fetch("http://localhost:8080/food/order", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ orders }),
      });
      if (!res.ok) throw new Error("place order failed: " + res.status);
      alert("Order placed");
      setCartItems([]);
    } catch (err) {
      console.error(err);
      alert("Order failed");
    }
  };

  if (loading) return <p>Loading cart...</p>;

  return (
    <div className="cart-wrapper">
      <h1>Your Cart</h1>
      {cartItems.length === 0 ? <p>Your cart is empty</p> : (
        <>
          <div className="cart-grid">
            {cartItems.map(it => (
              <div key={String(it.itemId)} className="cart-card">
                <img src={it.photo || "/images/default_food.jpg"} alt={it.itemName} className="cart-img" />
                <div>
                  <h3>{it.itemName}</h3>
                  <p>₹{it.price}</p>
                  <div>
                    <button onClick={() => changeQty(it.itemId, -1)}>-</button>
                    <span>{it.quantity}</span>
                    <button onClick={() => changeQty(it.itemId, +1)}>+</button>
                    <button onClick={() => removeItem(it.itemId)}>Remove</button>
                  </div>
                </div>
              </div>
            ))}
          </div>
          <h3>Grand Total: ₹{total}</h3>
          <button onClick={placeOrder}>Place Order</button>
        </>
      )}
    </div>
  );
};

export default CartPage;

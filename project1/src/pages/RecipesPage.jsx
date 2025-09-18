import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; 
import "./RecipesPage.css";

const categories = [
  "All",
  "Appetisers",
  "Starters",
  "Main Courses",
  "Side Dishes",
  "Desserts",
];

const recipes = [
  {
    id: 1,
    title: "Suzi's Crabcakes Inspired",
    author: "Suzi Perry",
    category: "Starters",
    img: "/images/food1.jpg",
  },
  {
    id: 2,
    title: "Creamy Prawn, Bacon & Broccoli Pasta",
    author: "Ricky Alberta",
    category: "Starters",
    img: "/images/food2.jpeg",
  },
  {
    id: 3,
    title: "Creamy Chicken & Pasta Bake",
    author: "Suzi Perry",
    category: "Starters",
    img: "/images/food3.jpeg",
  },
  {
    id: 4,
    title: "Italian Veggie Delight",
    author: "Maria Rossi",
    category: "Main Courses",
    img: "/images/food4.jpeg",
  },
  {
    id: 5,
    title: "Classic Spaghetti Pomodoro",
    author: "Luigi Romano",
    category: "Main Courses",
    img: "/images/food5.jpeg",
  }

];

function RecipesPage() {
  const [activeCategory, setActiveCategory] = useState("Starters");
  const [quantities, setQuantities] = useState({});
  const [showPopup, setShowPopup] = useState(false);
  const [formData, setFormData] = useState({ name: "", hostel: "", room: "", time: "" });

  const navigate = useNavigate();

  // Quantity handlers
  const handleIncrease = (id) => {
    setQuantities((prev) => ({ ...prev, [id]: (prev[id] || 0) + 1 }));
  };

  const handleDecrease = (id) => {
    setQuantities((prev) => ({
      ...prev,
      [id]: Math.max((prev[id] || 0) - 1, 0),
    }));
  };

  const filteredRecipes =
    activeCategory === "All"
      ? recipes
      : recipes.filter((r) => r.category === activeCategory);

  // Popup form handlers
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(
      `✅ Water booked!\nName: ${formData.name}\nHostel: ${formData.hostel}\nRoom: ${formData.room}\nTime: ${formData.time}`
    );
    setShowPopup(false);
    setFormData({ name: "", hostel: "", room: "", time: "" });
  };

  return (
    <div className="recipes-wrapper">
      {/* 🔹 Top Right Menu */}
      <div className="top-menu">
        <button onClick={() => setShowPopup(true)} className="top-btn">
          Book Water
        </button>
        <button onClick={() => navigate("/cart")} className="top-btn">
          Cart
        </button>
      </div>

      {/* 🔹 Title with background video */}
      <div className="recipes-title-container">
        <video className="recipes-video" autoPlay loop muted playsInline>
          <source src="/images/food_video.mp4" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
        <h1 className="recipes-title">Explore Recipes</h1>
      </div>

      {/* 🔹 Categories */}
      <div className="categories">
        {categories.map((cat) => (
          <button
            key={cat}
            className={`category-btn ${activeCategory === cat ? "active" : ""}`}
            onClick={() => setActiveCategory(cat)}
          >
            {cat}
          </button>
        ))}
      </div>

      <p className="recipe-count">
        You have <strong>{recipes.length}</strong> recipes to explore
      </p>

      {/* 🔹 Recipes grid */}
      <div className="recipes-grid">
        {filteredRecipes.map((r) => (
          <div className="recipe-card" key={r.id}>
            <img src={r.img} alt={r.title} className="recipe-img" />
            <div className="recipe-content">
              <h3 className="recipe-title">{r.title}</h3>
              <p className="recipe-author">{r.author}</p>

              {/* Quantity Controls */}
              <div className="quantity-controls">
                <button onClick={() => handleDecrease(r.id)}>-</button>
                <span>{quantities[r.id] || 0}</span>
                <button onClick={() => handleIncrease(r.id)}>+</button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* 🔹 Popup Form for Water Booking */}
      {showPopup && (
        <div className="popup-overlay">
          <div className="popup">
            <h2>Book Water</h2>
            <form onSubmit={handleSubmit} className="popup-form">
              <input
                type="text"
                name="name"
                placeholder="Enter your name"
                value={formData.name}
                onChange={handleChange}
                required
              />
              <input
                type="text"
                name="hostel"
                placeholder="Hostel Name"
                value={formData.hostel}
                onChange={handleChange}
                required
              />
              <input
                type="text"
                name="room"
                placeholder="Room No."
                value={formData.room}
                onChange={handleChange}
                required
              />
              <input
                type="time"
                name="time"
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

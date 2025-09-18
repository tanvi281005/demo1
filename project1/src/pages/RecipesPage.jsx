import React, { useState } from "react";
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

  // Track quantity per recipe
  const [quantities, setQuantities] = useState({});

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

  return (
    <div className="recipes-wrapper">
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
    </div>
  );
}

export default RecipesPage;

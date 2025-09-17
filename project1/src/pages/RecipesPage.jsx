import React, { useState } from "react";
import "./RecipesPage.css";

const categories = ["All", "Appetisers", "Starters", "Main Courses", "Side Dishes", "Desserts"];

const recipes = [
  {
    id: 1,
    title: "Suzi's Crabcakes Inspired",
    author: "Suzi Perry",
    category: "Starters",
    img: "https://source.unsplash.com/400x300/?pasta",
  },
  {
    id: 2,
    title: "Creamy Prawn, Bacon & Broccoli Pasta",
    author: "Ricky Alberta",
    category: "Starters",
    img: "https://source.unsplash.com/400x300/?salad",
  },
  {
    id: 3,
    title: "Creamy Chicken & Pasta Bake",
    author: "Suzi Perry",
    category: "Starters",
    img: "https://source.unsplash.com/400x300/?chicken",
  },
  {
    id: 4,
    title: "Italian Veggie Delight",
    author: "Maria Rossi",
    category: "Main Courses",
    img: "https://source.unsplash.com/400x300/?vegetables",
  },
  {
    id: 5,
    title: "Classic Spaghetti Pomodoro",
    author: "Luigi Romano",
    category: "Main Courses",
    img: "https://source.unsplash.com/400x300/?spaghetti",
  },
];

function RecipesPage() {
  const [activeCategory, setActiveCategory] = useState("Starters");

  const filteredRecipes =
    activeCategory === "All"
      ? recipes
      : recipes.filter((r) => r.category === activeCategory);

  return (
    <div className="recipes-wrapper">
      <h1 className="recipes-title">Explore Recipes</h1>

      {/* Categories */}
      <div className="categories">
        {categories.map((cat) => (
          <button
            key={cat}
            className={`category-btn ${
              activeCategory === cat ? "active" : ""
            }`}
            onClick={() => setActiveCategory(cat)}
          >
            {cat}
          </button>
        ))}
      </div>

      <p className="recipe-count">
        You have <strong>{recipes.length}</strong> recipes to explore
      </p>

      {/* Recipes grid */}
      <div className="recipes-grid">
        {filteredRecipes.map((r) => (
          <div className="recipe-card" key={r.id}>
            <img src={r.img} alt={r.title} className="recipe-img" />
            <div className="recipe-content">
              <h3 className="recipe-title">{r.title}</h3>
              <p className="recipe-author">{r.author}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default RecipesPage;

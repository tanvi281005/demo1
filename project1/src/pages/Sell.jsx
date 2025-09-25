import React, { useState } from "react";
import "./Sell.css";

const Sell = () => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    category: "",
    price: "",
    condition: "",
    photo: "",
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData({ ...formData, [id]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate all fields
    if (!formData.title || !formData.description || !formData.category || !formData.price || !formData.condition || !formData.photo) {
      alert("Please fill all fields.");
      return;
    }

    // Convert price to string for BigDecimal
    const itemDTO = {
      title: formData.title,
      description: formData.description,
      categoryName: formData.category,
      price: formData.price.toString(), // send as string for backend BigDecimal
      itemCondition: formData.condition,
      photo: formData.photo,
    };

    try {
      const response = await fetch("http://localhost:8080/market-items", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(itemDTO),
        credentials: "include", // include session if needed
      });

      if (response.ok) {
        alert("Item added successfully!");
        setFormData({
          title: "",
          description: "",
          category: "",
          price: "",
          condition: "",
          photo: "",
        });
      } else {
        const text = await response.text();
        alert("Failed to add item: " + text);
      }
    } catch (error) {
      console.error("Error adding item:", error);
      alert("Error adding item. See console for details.");
    }
  };

  return (
    <div className="sellPage">
      {/* Video background */}
      <video className="background-video" autoPlay loop muted playsInline>
        <source
          src="https://v1.pinimg.com/videos/mc/720p/8c/1d/ed/8c1dedb16848cf25a21ef3d943796b93.mp4"
          type="video/mp4"
        />
      </video>

      {/* Form content */}
      <div className="content">
        <div className="sellPage-container">
          <h1>Post Your Ad</h1>
          <form onSubmit={handleSubmit}>
            <label htmlFor="title">Title:</label>
            <input
              type="text"
              id="title"
              value={formData.title}
              onChange={handleChange}
              placeholder="Enter item title"
              required
            />

            <label htmlFor="description">Description:</label>
            <textarea
              id="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Enter description"
              required
            />

            <label htmlFor="category">Category:</label>
            <select
              id="category"
              value={formData.category}
              onChange={handleChange}
              required
            >
             <option value="">--Select Category--</option>
<option value="Electronic Devices">Electronic Devices</option>
<option value="Stationery & Books">Stationery & Books</option>
<option value="Coats & Formals">Coats & Formals</option>
<option value="Vehicles">Vehicles</option>
<option value="Miscellaneous">Miscellaneous</option>

            </select>

            <label htmlFor="price">Price:</label>
            <input
              type="number"
              id="price"
              value={formData.price}
              onChange={handleChange}
              placeholder="Enter price"
              min="0"
              step="0.01"
              required
            />

            <label htmlFor="condition">Condition:</label>
            <select
              id="condition"
              value={formData.condition}
              onChange={handleChange}
              required
            >
              <option value="">--Select Condition--</option>
              <option value="new">New</option>
              <option value="used">Used</option>
            </select>

            <label htmlFor="photo">Image URL:</label>
            <input
              type="text"
              id="photo"
              value={formData.photo}
              onChange={handleChange}
              placeholder="Enter image URL"
              required
            />

            <button type="submit">Add Item</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Sell;

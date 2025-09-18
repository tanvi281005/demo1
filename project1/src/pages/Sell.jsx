import React, { useState } from "react";
import "./Sell.css"; // Keep your existing styles here

const Sell = () => {
  const [formData, setFormData] = useState({
    category: "",
    name: "",
    description: "",
    price: "",
    condition: "",
    image: null,
  });

  const handleChange = (e) => {
    const { id, value, files } = e.target;
    if (id === "sell-image") {
      setFormData({ ...formData, image: files[0] });
    } else {
      setFormData({ ...formData, [id]: value });
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const { category, name, description, price, condition, image } = formData;

    if (!category || !name || !description || !price || !condition || !image) {
      alert("Please fill all the fields and upload an image.");
      return;
    }

    alert(
      `Item "${name}" in category "${category}" uploaded successfully!\nPrice: $${price}\nCondition: ${condition}`
    );

    setFormData({
      category: "",
      name: "",
      description: "",
      price: "",
      condition: "",
      image: null,
    });

    e.target.reset();
  };

  return (
    <div className="sellPage">
      {/* Video background */}
      <video className="background-video" autoPlay loop muted>
        <source src="https://v1.pinimg.com/videos/mc/720p/8c/1d/ed/8c1dedb16848cf25a21ef3d943796b93.mp4" type="video/mp4" />
        {/* Your browser does not support the video tag. */}
      </video>

      {/* Content on top of the video */}
      <div className="content">
        <div className="sellPage-container">
          <h1>Post your Ad</h1>
          <form onSubmit={handleSubmit}>
            <label htmlFor="category">Select Category:</label>
            <select
              id="category"
              value={formData.category}
              onChange={handleChange}
              required
            >
              <option value="">--Select Category--</option>
              <option value="electronics">Electronics</option>
              <option value="books">Books & Stationery</option>
              <option value="vehicles">Vehicles</option>
              <option value="coats">Coats & Formals</option>
              <option value="miscellaneous">Miscellaneous</option>
            </select>

            <label htmlFor="name">Item Name:</label>
            <input
              type="text"
              id="name"
              placeholder="Enter item name"
              value={formData.name}
              onChange={handleChange}
              required
            />

            <label htmlFor="description">Description:</label>
            <textarea
              id="description"
              placeholder="Enter item description"
              value={formData.description}
              onChange={handleChange}
              required
            ></textarea>

            <label htmlFor="price">Price:</label>
            <input
              type="number"
              id="price"
              placeholder="Enter price"
              min="0"
              value={formData.price}
              onChange={handleChange}
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

            <label htmlFor="sell-image">Upload Image:</label>
            <input
              type="file"
              id="sell-image"
              accept="image/*"
              onChange={handleChange}
              required
            />

            <button type="submit">Upload Item</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Sell;

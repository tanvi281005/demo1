import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Buy.css';

const Buy = () => {
  const headingText = "Hey, what are you looking for today?";
  const [items, setItems] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const navigate = useNavigate();

  const fetchAllItems = () => {
    fetch('http://localhost:8080/market-items')
      .then(res => res.json())
      .then(data => setItems(data))
      .catch(err => console.error(err));
  };

  const searchItems = (query) => {
    fetch(`http://localhost:8080/market-items/search?q=${query}`)
      .then(res => res.json())
      .then(data => setItems(data))
      .catch(err => console.error(err));
  };

  // Fetch all items on page load
  useEffect(() => {
    fetchAllItems();
  }, []);

  // Redirect to category page
  const handleCategoryClick = (categoryKey) => {
    navigate(`/category/${categoryKey}`);
  };

  return (
    <div className="buy-container">
      <div className="profile-icon" onClick={() => navigate('/profile')}>
        <img src="https://i.pinimg.com/736x/41/cc/e0/41cce034558636ad974ee86800a5508c.jpg" alt="Profile" />
      </div>

      <div className="animated-heading">
        {headingText.split("").map((char, index) => (
          <span key={index} style={{ animationDelay: `${index * 0.05}s` }}>
            {char === " " ? "\u00A0" : char}
          </span>
        ))}
      </div>

      <div className="search-container">
        <input
          type="text"
          placeholder="Search items..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && searchItems(searchQuery)}
        />
      </div>

      {/* Category buttons */}
      <div className="categories-row">
        <div
          className="category-button"
          style={{ backgroundImage: "url('https://i.pinimg.com/1200x/99/64/a2/9964a202c67115b1f40714082848c312.jpg')" }}
          onClick={() => handleCategoryClick('electronics')}
        >
          <div className="overlay">Electronic Devices</div>
        </div>
        <div
          className="category-button"
          style={{ backgroundImage: "url('https://i.pinimg.com/736x/e4/ae/17/e4ae17634d4b69536631bd674fc44463.jpg')" }}
          onClick={() => handleCategoryClick('stationery')}
        >
          <div className="overlay">Stationery & Books</div>
        </div>
        <div
          className="category-button"
          style={{ backgroundImage: "url('https://i.pinimg.com/736x/78/9a/1e/789a1e7bf4bceec73f1d285c1428fcc6.jpg')" }}
          onClick={() => handleCategoryClick('miscellaneous')}
        >
          <div className="overlay">Miscellaneous</div>
        </div>
      </div>

      <div className="categories-row">
        <div
          className="category-button"
          style={{ backgroundImage: "url('https://5.imimg.com/data5/HA/UA/RN/SELLER-101590291/black-coat-pant-formal-slim-fit-1450-500x500.jpg')" }}
          onClick={() => handleCategoryClick('coats')}
        >
          <div className="overlay">Coats & Formals</div>
        </div>
        <div
          className="category-button"
          style={{ backgroundImage: "url('https://i.pinimg.com/736x/11/87/02/11870269b885fddb29b190aa36ffe6d3.jpg')" }}
          onClick={() => handleCategoryClick('vehicles')}
        >
          <div className="overlay">Vehicles</div>
        </div>
      </div>

      {/* Display all items dynamically */}
      <div className="items-display">
        {items.map(item => (
          <div key={item.itemId} className="item-card">
            <img src={item.photo} alt={item.title} />
            <h3>{item.title}</h3>
            <p>{item.price}$ - {item.itemCondition}</p>
            <p>{item.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Buy;

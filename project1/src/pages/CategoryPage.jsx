import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import './Electronics.css'; 

const categoryData = {
  electronics: {
    heading: "Electronic Devices",
    apiCategory: "Electronic Devices",
    bgImage: "https://i.pinimg.com/736x/7b/b8/12/7bb812007991708f413fb44c36f4ffd1.jpg"
  },
  stationery: {
    heading: "Stationery & Books",
    apiCategory: "Stationery & Books",
    bgImage: "https://i.pinimg.com/736x/88/71/17/887117d90c4932905617a247eab26fd5.jpg"
  },
  coats: {
    heading: "Coats & Formals",
    apiCategory: "Coats & Formals",
    bgImage: "https://i.pinimg.com/1200x/d7/86/98/d7869880338da7c29e9716033c651af0.jpg"
  },
  vehicles: {
    heading: "Vehicles",
    apiCategory: "Vehicles",
    bgImage: "https://i.pinimg.com/736x/5e/1d/44/5e1d44ff80203d654a6178c30eecaa63.jpg"
  },
  miscellaneous: {
    heading: "Miscellaneous",
    apiCategory: "Miscellaneous",
    bgImage: "https://i.pinimg.com/736x/e1/a0/76/e1a076e18bcc62751c9bc4ec0cb81fe8.jpg"
  }
};

const CategoryPage = () => {
  const { category } = useParams();
  const [items, setItems] = useState([]);
  const catInfo = categoryData[category];
  const navigate = useNavigate();

  useEffect(() => {
    if (!catInfo) return;
    const apiCategory = encodeURIComponent(catInfo.apiCategory);

    fetch(`http://localhost:8080/market-items/category/${apiCategory}`, {
  credentials: "include" // ✅ add this line
})
  .then(res => res.json())
  .then(data => setItems(data))
  .catch(err => console.error(err));
  }, [category]);

  if (!catInfo) return <p>Category not found</p>;

  return (
    <div
      className="electronics-container"
      style={{
        backgroundImage: `url(${catInfo.bgImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundAttachment: 'fixed'
      }}
    >
      <header>
        <h1>{catInfo.heading}</h1>
        <div className="controls">
          <input type="text" placeholder="Search products..." />
        </div>
      </header>

      <div className="products">
        {items.length === 0 && <p>No items available.</p>}
        {items.map(item => (
          <div
            key={item.itemId}
            className="product-card"
            onClick={() => navigate(`/product/${item.itemId}`)}
            style={{ cursor: "pointer" }}
          >
            <img src={item.photo} alt={item.title} />
            <div className="product-info">
              <h3>{item.title}</h3>
              <p>{item.description}</p>
              <div className="product-meta">
                Condition: {item.itemCondition} <br />
                Price: ${item.price} <br />
                Added: {item.addedAt ? new Date(item.addedAt).toLocaleDateString() : 'N/A'}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CategoryPage;

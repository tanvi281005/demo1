import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import './ProductDetail.css';

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [negotiatedPrice, setNegotiatedPrice] = useState("");

  useEffect(() => {
    fetch(`http://localhost:8080/market-items/${id}`, {
      credentials: "include" // ✅ include session
    })
      .then(res => res.json())
      .then(data => setProduct(data))
      .catch(err => console.error(err));
  }, [id]);

  const handleBuyRequest = async () => {
    if (!negotiatedPrice) {
      alert("Please enter a price");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/transactions/request", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include", // ✅ ensures JSESSIONID is sent
        body: JSON.stringify({
          itemId: product.itemId,
          negotiatedPrice: parseFloat(negotiatedPrice) // only fields backend expects
        })
      });

      if (response.ok) {
        const text = await response.text();
        alert("Buy request sent! " + text);
        setNegotiatedPrice("");
      } else {
        const errorText = await response.text();
        alert("Failed: " + errorText);
      }
    } catch (err) {
      console.error(err);
      alert("Error occurred. Try again.");
    }
  };

  if (!product) return <div>Loading product...</div>;

  return (
    <div className="product-detail-container">
      <div className="product-detail-image">
        <img src={product.photo} alt={product.title} />
      </div>

      <div className="product-detail-info">
        <h2>{product.title}</h2>
        <p>{product.description}</p>
        <p><strong>Category:</strong> {product.categoryName}</p>
        <p><strong>Condition:</strong> {product.itemCondition}</p>
        <p><strong>Original Price:</strong> ${product.price}</p>
        <p><strong>Added on:</strong> {product.addedAt ? new Date(product.addedAt).toLocaleDateString() : "N/A"}</p>

        <div className="negotiation-box">
          <input
            type="number"
            placeholder="Enter your price"
            value={negotiatedPrice}
            onChange={(e) => setNegotiatedPrice(e.target.value)}
          />
          <button className="request-buy-btn" onClick={handleBuyRequest}>
            Request Buy
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;

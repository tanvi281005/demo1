import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import './BuySellProfile.css';

const BuySellProfile = () => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("User");
  const [sellingItems, setSellingItems] = useState([]);
  const [buyRequests, setBuyRequests] = useState([]);
  const [loading, setLoading] = useState(false);

  // Fetch profile, seller, and buyer transactions
  const fetchData = async () => {
    setLoading(true);
    try {
      const [profileRes, sellerRes, buyerRes] = await Promise.all([
        fetch("http://localhost:8080/user/buysellprofile", { credentials: "include" }),
        fetch("http://localhost:8080/transactions/seller", { credentials: "include" }),
        fetch("http://localhost:8080/transactions/buyer", { credentials: "include" }),
      ]);

      if (profileRes.ok) {
        const data = await profileRes.json();
        setUserName(data.name);
      }

      if (sellerRes.ok) {
        const data = await sellerRes.json();
        setSellingItems(data);
      }

      if (buyerRes.ok) {
        const data = await buyerRes.json();
        setBuyRequests(data);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  // Seller sets final price
  const handleSetFinalPrice = async (serviceId, price) => {
    if (!price) return;
    try {
      const res = await fetch(`http://localhost:8080/transactions/${serviceId}/final-price?finalPrice=${price}`, {
        method: "PUT",
        credentials: "include",
      });
      if (!res.ok) throw new Error(`Failed to set final price: ${res.status}`);
      await fetchData(); // refresh both seller and buyer views
    } catch (err) {
      console.error(err);
      alert("Failed to set final price");
    }
  };

  // Buyer approves/rejects
  const handleApprovePurchase = async (serviceId, approve) => {
    try {
      const res = await fetch(`http://localhost:8080/transactions/${serviceId}/approve?isApproved=${approve}`, {
        method: "PUT",
        credentials: "include",
      });
      if (!res.ok) throw new Error(`Failed to approve: ${res.status}`);
      await fetchData(); // refresh data
    } catch (err) {
      console.error(err);
      alert("Failed to approve/reject purchase");
    }
  };

  return (
    <div className="buysellprofile-container">
      <div style={{ textAlign: "right", marginBottom: "20px" }}>
        <button 
          style={{ padding: "10px 15px", borderRadius: "8px", cursor: "pointer", backgroundColor: "#800000", color: "white", border: "none" }}
          onClick={() => navigate("/buysellpage")}
        >
          Back to Hub
        </button>
      </div>

      <h2>Welcome, {userName}!</h2>
      {loading && <p>Loading...</p>}

      <div className="buysellprofile-columns">

        {/* Seller column */}
        <div className="column left">
          <h3>Your Items with Requests</h3>
          {sellingItems.length === 0 && <p>No items requested yet.</p>}
          {sellingItems.map(item => (
            <div key={item.serviceId} className="card">
              <p><strong>{item.title}</strong></p>
              <p>Buyer: {item.buyerName}</p>
              <p>Negotiated Price: ${item.negotiatedPrice}</p>
              <p>Final Price: ${item.finalPrice ?? "Not set"}</p>

              {/* Input only if final price not set yet */}
              {item.finalPrice === 0 && (
                <input 
                  type="number" 
                  placeholder="Set final price" 
                  defaultValue={item.finalPrice || ""} 
                  onBlur={(e) => handleSetFinalPrice(item.serviceId, e.target.value)}
                />
              )}
            </div>
          ))}
        </div>

        {/* Buyer column */}
        <div className="column right">
          <h3>Your Buy Requests</h3>
          {buyRequests.length === 0 && <p>No buy requests.</p>}
          {buyRequests.map(item => (
            <div key={item.serviceId} className="card">
              <p><strong>{item.title}</strong></p>
              <p>Seller: {item.sellerName}</p>
              <p>Negotiated Price: ${item.negotiatedPrice}</p>
              <p>Final Price: {item.finalPrice ? `$${item.finalPrice}` : "Pending"}</p>

              {/* Show approve/reject only if final price exists and not approved yet */}
              {item.finalPrice && item.isApproved === false && (
                <div className="approve-buttons">
                  <button onClick={() => handleApprovePurchase(item.serviceId, true)}>Approve</button>
                  <button onClick={() => handleApprovePurchase(item.serviceId, false)}>Reject</button>
                </div>
              )}

              {item.isApproved && <p>Status: Approved ✅</p>}
              {item.isApproved === false && item.finalPrice && <p>Status: Rejected ❌</p>}
            </div>
          ))}
        </div>

      </div>
    </div>
  );
};

export default BuySellProfile;

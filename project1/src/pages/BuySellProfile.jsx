import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import './BuySellProfile.css';

const BuySellProfile = () => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("User");
  const [sellingItems, setSellingItems] = useState([]);
  const [buyRequests, setBuyRequests] = useState([]);
  const [loading, setLoading] = useState(false);
  const [finalPriceInputs, setFinalPriceInputs] = useState({});

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
        console.log("Profile response:", data);
        setUserName(data.firstName);
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

  const handleSetFinalPrice = async (serviceId, price) => {
    if (!price || isNaN(price)) return;
    try {
      const res = await fetch(`http://localhost:8080/transactions/${serviceId}/final-price?finalPrice=${price}`, {
        method: "PUT",
        credentials: "include",
      });
      if (!res.ok) throw new Error(`Failed to set final price: ${res.status}`);
      await fetchData();
    } catch (err) {
      console.error(err);
      alert("Failed to set final price");
    }
  };

  const handleApprovePurchase = async (serviceId, approve) => {
    try {
      const res = await fetch(`http://localhost:8080/transactions/${serviceId}/approve?isApproved=${approve}`, {
        method: "PUT",
        credentials: "include",
      });
      if (!res.ok) throw new Error(`Failed to approve: ${res.status}`);
      await fetchData();
    } catch (err) {
      console.error(err);
      alert("Failed to approve/reject purchase");
    }
  };

  return (
    <div className="buysellprofile-container">

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

              {item.finalPrice === 0 ? (
                <div className="price-input-group">
  <input
    type="number"
    placeholder="Set final price"
    value={finalPriceInputs[item.serviceId] || ""}
    onChange={(e) =>
      setFinalPriceInputs({
        ...finalPriceInputs,
        [item.serviceId]: e.target.value,
      })
    }
  />
  <button
    className="save-price-button"
    onClick={() =>
      handleSetFinalPrice(item.serviceId, finalPriceInputs[item.serviceId])
    }
  >
    Save
  </button>
</div>

              ) : (
                <>
                  <p>Final Price: ${item.finalPrice}</p>
                  {item.isApproved === null && <p>Status: Pending ⏳</p>}
                  {/* {item.isApproved === true && (
                    <>
                      <p>Status: Approved ✅</p>
                      <p className="wallet-message seller">
                        💰 Final price of ${item.finalPrice} added to your wallet.
                      </p>
                    </>
                  )} */}
                  {item.isApproved === false && <p>Status: Rejected ❌</p>}
                </>
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

              {/* Final price hidden until seller sets it */}
              {item.finalPrice === 0 || item.finalPrice === null ? (
                <p>Status: Pending ⏳</p>
              ) : (
                <>
                  <p>Final Price: ${item.finalPrice}</p>
                  {item.isApproved === null && (
                    <div className="approve-buttons">
                      <button onClick={() => handleApprovePurchase(item.serviceId, true)}>Approve</button>
                      <button onClick={() => handleApprovePurchase(item.serviceId, false)}>Reject</button>
                    </div>
                  )}
                  {/* {item.isApproved === true && (
                <>
                  <p>Status: Approved ✅</p>
                  <p className="wallet-message buyer">
                    💸 Final price of ${item.finalPrice} deducted from your wallet.
                  </p>
                </>
              )} */}
                  {item.isApproved === true && (
                    <div className="counterparty-box">
                      <h4>Seller Details</h4>
                      <p><strong>Name:</strong> {item.sellerName}</p>
                      <p><strong>Email:</strong> {item.sellerEmail}</p>
                      <p><strong>Contact:</strong> {item.sellerContact}</p>
                    </div>
                  )}
                  {item.isApproved === false && <p>Status: Rejected ❌</p>}
                </>
              )}
            </div>
          ))}
        </div>

      </div>
    </div>
  );
};

export default BuySellProfile;

import React from 'react';
import { useNavigate } from 'react-router-dom';
import './BuySellHub.css';

const BuySellHub = () => {
    const navigate = useNavigate();

    return (
        <div className="buysell-container">
            {/* Top-right Profile Button */}
            <div style={{ position: "absolute", top: 20, right: 20 }}>
                <button
                    style={{
                        padding: "10px 15px",
                        borderRadius: "8px",
                        cursor: "pointer",
                        backgroundColor: "#800000",
                        color: "white",
                        border: "none",
                        fontWeight: "bold"
                    }}
                    onClick={() => navigate("/buysellprofile")}
                >
                    My BuySell Profile
                </button>
            </div>

            <h1>Buy & Sell Hub</h1>
            <div className="buttons-container">
                <div className="button buy" onClick={() => navigate('/buy')}>
                    <span className="icon buy">✔</span>
                </div>
                <div className="button sell" onClick={() => navigate('/sell')}>
                    <span className="icon sell">✔</span>
                </div>
            </div>
        </div>
    );
};

export default BuySellHub;

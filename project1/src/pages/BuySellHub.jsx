import React from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import './BuySellHub.css'; // Import the CSS file

const BuySellHub = () => {
    const navigate = useNavigate(); // Initialize navigate

    return (
        <div className="buysell-container">
            <h1>Buy & Sell Hub</h1>
            <div className="buttons-container">
                <div className="button buy" onClick={() => navigate('/buy')}>
                    <span className="icon buy">✔ </span>
                </div>
                <div className="button sell" onClick={() => navigate('/sell')}>
                    <span className="icon sell">✔ </span>
                </div>
            </div>
        </div>
    );
};

export default BuySellHub;

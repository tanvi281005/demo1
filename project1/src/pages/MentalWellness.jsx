import React from "react";
import { useNavigate } from "react-router-dom";
import "./MentalWellness.css";
import illustration from "/images/new.jpg";

const MentalWellness = () => {
  const navigate = useNavigate();

  return (
    <div className="page-container">
      <div className="wellness-card">
        <div className="image-section">
          <img 
            src={illustration} 
            alt="Mental Wellness Illustration" 
            className="wellness-image" 
          />
        </div>

        <div className="content-section">
          <h1>Mental Wellness</h1>
          <p>Choose how you would like to engage with our mental health resources.</p>
          <div className="button-group">
            <button className="btn btn-primary">Register as Counsellor</button>
            <button 
              className="btn btn-secondary" 
              onClick={() => navigate('/support')}
            >
              Take Counselling
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MentalWellness;

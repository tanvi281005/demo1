import React from "react";
import { useNavigate } from "react-router-dom";
import "./TransportServices.css";

export default function TransportServices() {
  const navigate = useNavigate();

  return (
    <div className="transport-wrapper">
      {/* Background bus video */}
      <video
        className="bus_background_video"
        autoPlay
        loop
        muted
        playsInline
      >
        <source src="/images/bus_video.mp4" type="video/mp4" />
        Your browser does not support the video tag.
      </video>

      {/* Overlay with options */}
      <div className="transport-overlay">
        <h1 className="transport-title">Choose Your Commute</h1>

        <div className="transport-options-vertical">
          <button
            type="button"
            className="transport-bar"
            onClick={() => navigate("/daily-commute")}
          >
            <div className="bar-left">
              <span className="bar-icon">🚌</span>
              <span className="bar-text">Daily Commute</span>
            </div>
            <span className="bar-arrow">→</span>
          </button>

          <button
            type="button"
            className="transport-bar transport-bar-urgent"
            onClick={() => navigate("/urgent")}
          >
            <div className="bar-left">
              <span className="bar-icon">⚡</span>
              <span className="bar-text">Urgent Commute</span>
            </div>
            <span className="bar-arrow">→</span>
          </button>
        </div>
      </div>
    </div>
  );
}

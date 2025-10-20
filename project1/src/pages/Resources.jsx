import React from "react";
import { useNavigate } from "react-router-dom";
import "./Resources.css";

export default function Resources() {
  const navigate = useNavigate();

  return (
    <div className="resources-wrapper">
      {/* Background image */}
      <div
        className="background-image"
        style={{ backgroundImage: "url(/images/backgroundar.jpg)" }}
      />

      {/* Foreground overlay */}
      <div className="resources-overlay">
        <h1 className="resources-title">Manage Your Resources</h1>

        <div className="resources-options-vertical">
          <button
            type="button"
            className="resources-bar"
            onClick={() => navigate("/upload")}
          >
            <div className="bar-left">
              <span className="bar-icon">📤</span>
              <span className="bar-text">Upload Resources</span>
            </div>
            <span className="bar-arrow">→</span>
          </button>

          <button
            type="button"
            className="resources-bar resources-bar-find"
            onClick={() => navigate("/find-resources")}
          >
            <div className="bar-left">
              <span className="bar-icon">🔍</span>
              <span className="bar-text">Find Resources</span>
            </div>
            <span className="bar-arrow">→</span>
          </button>
        </div>
      </div>
    </div>
  );
}

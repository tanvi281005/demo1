import { useNavigate } from "react-router-dom";
import "./TransportServices.css"; // Import the CSS file

function TransportServices() {
  const navigate = useNavigate();

  return (
    <div className="transport-container">
      <h2 className="transport-title">Choose Your Transport Service</h2>
      <div className="transport-grid">
        <div
          className="transport-card"
          onClick={() => navigate("/daily-commute")}
        >
          <span className="transport-emoji">🚍 Daily Commute</span>
        </div>
        <div
          className="transport-card"
          onClick={() => navigate("/urgent")}
        >
          <span className="transport-emoji">🚄 Urgent</span>
        </div>
      </div>
    </div>
  );
}

export default TransportServices;

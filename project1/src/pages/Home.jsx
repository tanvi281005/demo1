import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Home.css"; // move your CSS here

export default function Home() {
  const navigate = useNavigate();

  useEffect(() => {
    const panels = document.querySelectorAll(".panel");

    panels.forEach((panel) => {
      panel.addEventListener("click", () => {
        panels.forEach((p) => p.classList.remove("active"));
        panel.classList.add("active");
      });
    });
  }, []);

  return (
    <div className="home">
      <h1>Student Utility Hub</h1>

      <div className="accordion">
        <div className="panel transport">
          <span>Transport Bookings</span>
          <div className="content">
            <p>Book buses, cabs, or rides easily.</p>
            <button onClick={() => navigate("/transport")}>Enter</button>
          </div>
        </div>
        <div className="panel wellbeing">
          <span>Mental Wellbeing</span>
          <div className="content">
            <p>Access wellness resources and support.</p>
            <button onClick={() => navigate("/wellbeing")}>Enter</button>
          </div>
        </div>
        <div className="panel market">
          <span>Buy & Sell</span>
          <div className="content">
            <p>Trade items within campus securely.</p>
            <button onClick={() => navigate("/market")}>Enter</button>
          </div>
        </div>
        <div className="panel food">
          <span>Food & Water</span>
          <div className="content">
            <p>Order food and manage water bookings.</p>
            <button onClick={() => navigate("/food")}>Enter</button>
          </div>
        </div>
        <div className="panel academic">
          <span>Academic Resources</span>
          <div className="content">
            <p>Notes, assignments, and study material.</p>
            <button onClick={() => navigate("/academic")}>Enter</button>
          </div>
        </div>
      </div>
    </div>
  );
}

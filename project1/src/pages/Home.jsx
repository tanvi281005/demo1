import { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate
import "./Home.css"; // CSS file

const panelsData = [
  { title: "Transport Bookings", image: "/images/bus1.jpg", content: "Book buses, cabs, or rides easily.", route: "/transport" },
  { title: "Mental Wellbeing", image: "/images/wellness1.jpg", content: "Access wellness resources and support." },
  { title: "Buy & Sell", image: "/images/buysell1.jpg", content: "Trade items within campus securely." },
  { title: "Food & Water", image: "/images/food1.jpg", content: "Order food and manage water bookings." },
  { title: "Academic Resources", image: "/images/academic1.jpg", content: "Notes, assignments, and study material." },
];

export default function Home() {
  const [activeIndex, setActiveIndex] = useState(null);
  const navigate = useNavigate(); // Initialize navigate

  const handlePanelClick = (index) => {
    setActiveIndex(index);

    // If the clicked panel has a route, navigate there
    if (panelsData[index].route) {
      navigate(panelsData[index].route);
    }
  };

  return (
    <div className="container">
      <h1 className="title">Student Utility Hub</h1>

      <div className="panels">
        {panelsData.map((panel, index) => (
          <div
            key={index}
            className={`panel ${activeIndex === index ? "active" : ""}`}
            onClick={() => handlePanelClick(index)}
            style={{ backgroundImage: `url(${panel.image})` }}
          >
            <span className="panel-title">{panel.title}</span>

            {activeIndex === index && (
              <div className="panel-content">
                <p>{panel.content}</p>
                <button className="enter-btn">Enter</button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

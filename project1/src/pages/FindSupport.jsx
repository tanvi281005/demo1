import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './FindSupport.css';

const counsellors = [
  { id: 1, name: "Dr. Priya Sharma", specialization: "Substance Use", slot: "10:00 AM - 12:00 PM", icon: "🛠️" },
  { id: 2, name: "Mr. Rohan Verma", specialization: "Relationships", slot: "2:00 PM - 4:00 PM", icon: "🎁" },
  { id: 3, name: "Ms. Anjali Rao", specialization: "Anxiety", slot: "11:00 AM - 1:00 PM", icon: "🧃" },
  { id: 4, name: "Dr. Sameer Gupta", specialization: "Depression", slot: "3:00 PM - 5:00 PM", icon: "⚪" },
  { id: 5, name: "Ms. Kavya Iyer", specialization: "Relationships", slot: "9:00 AM - 11:00 AM", icon: "👩‍⚕️" },
  { id: 6, name: "Dr. Arjun Menon", specialization: "Career Guidance", slot: "1:00 PM - 3:00 PM", icon: "🌼" },
  { id: 7, name: "Ms. Nidhi Sharma", specialization: "Anxiety", slot: "4:00 PM - 6:00 PM", icon: "🧸" },
  { id: 8, name: "Dr. Meera Iyer", specialization: "Substance Use", slot: "10:00 AM - 12:00 PM", icon: "🦊" }
];

const filters = ["All", "Substance Use", "Relationships", "Anxiety", "Career Guidance", "Depression"];

const FindSupport = () => {
  const [activeFilter, setActiveFilter] = useState("All");

  const filteredCounsellors = activeFilter === "All"
    ? counsellors
    : counsellors.filter(c => c.specialization === activeFilter);

  return (
    <div className="support-page-body">
      <h1 className="support-header">🌿 Find the Right Support for You</h1>

      <div className="filter-container">
        {filters.map((filter) => (
          <button
            key={filter}
            className={`filter-btn ${activeFilter === filter ? "active" : ""}`}
            onClick={() => setActiveFilter(filter)}
          >
            {filter}
          </button>
        ))}
      </div>

      <div className="counsellor-grid">
        {filteredCounsellors.map(c => (
          <Link key={c.id} to={`/counsellor/${c.id}`} className="counsellor-card-link">
            <div className="counsellor-card">
              <div className="counsellor-avatar">{c.icon}</div>
              <div className="counsellor-info">
                <h2 className="counsellor-name">{c.name}</h2>
                <p><strong>Slot:</strong> {c.slot}</p>
                <p><strong>Specialization:</strong> {c.specialization}</p>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default FindSupport;

import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./FindSupport.css";

const filters = [
  { label: "All", value: "all" },
  { label: "Academics", value: "Academics" },
  { label: "Substance Addiction", value: "SubstanceAddiction" },
  { label: "Stress & Anxiety", value: "StressAndAnxiety" },
  { label: "Grief & Loss", value: "GriefAndLoss" },
  { label: "Personal Relationships", value: "PersonalRelationships" },
];

const FindSupport = () => {
  const [activeFilter, setActiveFilter] = useState("all");
  const [counsellors, setCounsellors] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Fetch counsellors from backend
  const fetchCounsellors = async (category) => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch(`http://localhost:8080/counsellor/fetch/${category}`);
      if (!response.ok) throw new Error("Failed to fetch counsellors");

      const data = await response.json();
      setCounsellors(data);
    } catch (err) {
      setError(err.message);
      setCounsellors([]);
    } finally {
      setLoading(false);
    }
  };

  // Fetch on mount & whenever filter changes
  useEffect(() => {
    fetchCounsellors(activeFilter);
  }, [activeFilter]);

  return (
    <div className="support-page-body">
      <h1 className="support-header">🌿 Find the Right Support for You</h1>

      <div className="filter-container">
        {filters.map((filter) => (
          <button
            key={filter.value}
            className={`filter-btn ${activeFilter === filter.value ? "active" : ""}`}
            onClick={() => setActiveFilter(filter.value)}
          >
            {filter.label}
          </button>
        ))}
      </div>

      {loading && <p className="loading-text">Loading counsellors...</p>}
      {error && <p className="error-text">❌ {error}</p>}

      <div className="counsellor-grid">
        {counsellors.length === 0 && !loading && (
          <p className="no-data-text">No counsellors found for this category.</p>
        )}

        {counsellors.map((c) => (
          <Link key={c.counsellorId} to={`/counsellor/${c.counsellorId}`} className="counsellor-card-link">
            <div className="counsellor-card">
              <div className="counsellor-avatar">👩‍⚕️</div>
              <div className="counsellor-info">
                <h2 className="counsellor-name">{c.selfDescription?.split(".")[0] || "Counsellor"}</h2>
                <p><strong>Specialization:</strong> {c.specialization.replace(/([A-Z])/g, " $1").trim()}</p>
                <p><strong>Students Counselled:</strong> {c.noOfStudentsCounselled}</p>
                <p><strong>Rating:</strong> ⭐ {c.rating?.toFixed(2) || "N/A"}</p>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default FindSupport;

import React, { useState, useEffect, useRef } from "react";
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

const POLL_INTERVAL = 5000; // 5 seconds

const FindSupport = () => {
  const [activeFilter, setActiveFilter] = useState("all");
  const [counsellors, setCounsellors] = useState([]);
  const [requests, setRequests] = useState([]);
  const [toasts, setToasts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const pollRef = useRef(null);

  // 🔔 Toast helper
  const pushToast = (message) => {
    const id = Date.now() + Math.random();
    setToasts((t) => [...t, { id, message }]);
    setTimeout(() => setToasts((t) => t.filter((x) => x.id !== id)), 4000);
  };

  // 🧠 Fetch counsellors list
  const fetchCounsellors = async (category) => {
    setLoading(true);
    setError(null);
    try {
      const url =
        category === "all"
          ? "http://localhost:8080/counsellor/fetch/all"
          : `http://localhost:8080/counsellor/fetch/${category}`;

      const response = await fetch(url, { credentials: "include" });
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

  useEffect(() => {
    fetchCounsellors(activeFilter);
  }, [activeFilter]);

  // ✅ Approve / Reject booking
  const handleDecision = async (serviceId, isApproved) => {
    try {
      const body = new URLSearchParams();
      body.append("serviceId", serviceId);
      body.append("isApproved", String(isApproved));

      const res = await fetch("http://localhost:8080/counsellingbook/approve", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: body.toString(),
      });

      if (!res.ok) {
        const txt = await res.text();
        throw new Error(txt || "Approval failed");
      }

      pushToast(isApproved ? "✅ Session approved" : "❌ Session rejected");

      // remove locally
      setRequests((prev) => prev.filter((r) => r.service_id !== serviceId));
    } catch (err) {
      console.error("Approval error:", err);
      pushToast("Error processing request");
    }
  };

  // 🔁 Fetch pending requests from backend using session
  const fetchPendingRequests = async () => {
    console.log("⏳ Fetching pending requests...");
    try {
      const res = await fetch("http://localhost:8080/counsellingbook/pending", {
        credentials: "include",
      });
      console.log("📡 Response status:", res.status);
      if (!res.ok) return;
      const data = await res.json();
      console.log("📦 Pending:", data);
      setRequests(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error("❌ Error fetching pending:", e);
    }
  };

  // 🔁 Poll every few seconds
  useEffect(() => {
    fetchPendingRequests();
    pollRef.current = setInterval(fetchPendingRequests, POLL_INTERVAL);
    return () => clearInterval(pollRef.current);
  }, []);

  return (
    <div className="support-page-body">
      <h1 className="support-header">🌿 Find the Right Support for You</h1>

      <div className="filter-container">
        {filters.map((filter) => (
          <button
            key={filter.value}
            className={`filter-btn ${
              activeFilter === filter.value ? "active" : ""
            }`}
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
          <Link
            key={c.counsellorId}
            to={`/counsellor/${c.counsellorId}`}
            className="counsellor-card-link"
          >
            <div className="counsellor-card">
              <div className="counsellor-avatar">👩‍⚕️</div>
              <div className="counsellor-info">
                <h2 className="counsellor-name">
                  {c.selfDescription?.split(" ").slice(0, 3).join(" ") ||
                    "Counsellor"}
                </h2>
                <p>
                  <strong>Specialization:</strong>{" "}
                  {c.specialization?.replace(/([A-Z])/g, " $1").trim()}
                </p>
                <p>
                  <strong>Students Counselled:</strong>{" "}
                  {c.noOfStudentsCounselled}
                </p>
                <p>
                  <strong>Rating:</strong> ⭐ {c.rating?.toFixed(2) || "N/A"}
                </p>
              </div>
            </div>
          </Link>
        ))}
      </div>

      {/* 🔔 Popup for pending requests */}
      {requests.length > 0 && (
        <div className="popup-overlay" role="dialog" aria-modal="true">
          <div className="popup-box">
            <h2>🧾 New Counselling Requests</h2>
            {requests.map((r) => (
              <div key={r.service_id} className="popup-request">
                <p>
                  <strong>Student:</strong> {r.student_name || r.student_id}{" "}
                  <br />
                  <strong>Time Slot:</strong>{" "}
                  {r.timeapproved
                    ? r.timeapproved.substring(0, 5)
                    : "Pending approval"}{" "}
                  <br />
                  <strong>Mode:</strong> {r.counselling_mode}
                </p>
                <div className="popup-buttons">
                  <button onClick={() => handleDecision(r.service_id, true)}>
                    ✅ Approve
                  </button>
                  <button onClick={() => handleDecision(r.service_id, false)}>
                    ❌ Reject
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Toasts */}
      <div className="toast-container" aria-live="polite">
        {toasts.map((t) => (
          <div key={t.id} className="toast">
            {t.message}
          </div>
        ))}
      </div>
    </div>
  );
};

export default FindSupport;

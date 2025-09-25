import { useState, useEffect } from "react";
import "./DailyCommute.css";

function DailyCommute() {
  const [form, setForm] = useState({ to: "" });
  const [destinations, setDestinations] = useState([]);
  const [buses, setBuses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Fetch destinations on mount
  useEffect(() => {
  setError(null);
  fetch("http://localhost:8080/transport/fetchdestination", {
    method: "GET",
    credentials: "include",
    headers: { "Accept": "application/json" }
  })
    .then(async (res) => {
      if (res.status === 401) {
        throw new Error("Unauthorized. Please log in.");
      }
      if (!res.ok) {
        const t = await res.text().catch(() => "");
        throw new Error("Failed to fetch destinations: " + res.status + " " + t);
      }
      // Read text first — if empty, return []
      const text = await res.text().catch(() => "");
      return text ? JSON.parse(text) : [];
    })
    .then(data => setDestinations(data || []))
    .catch(err => {
      console.error("Fetch destinations error:", err);
      setError(err.message);
      setDestinations([]);
    });
}, []);


  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Use GET with query param since this is a read-only fetch
  const handleSubmit = async () => {
    setError(null);

    if (!form.to) {
      alert("Please select a destination.");
      return;
    }

    console.log("calling daily-commute with destination:", form.to);

    setLoading(true);
    try {
      const url = `http://localhost:8080/transport/daily-commute?destination=${encodeURIComponent(form.to)}`;

      const res = await fetch(url, {
        method: "GET",
        credentials: "include", // important so JSESSIONID is sent
      });

      console.log("daily-commute HTTP status:", res.status);

      if (res.status === 401) {
        setBuses([]);
        setError("Unauthorized — please log in.");
        return;
      }
      if (!res.ok) {
        const text = await res.text().catch(() => "");
        throw new Error(`Failed to fetch buses: ${res.status} ${text}`);
      }

      const data = await res.json();
      setBuses(data || []);
    } catch (err) {
      console.error("Fetch buses error:", err);
      setError(err.message || "Network error");
      setBuses([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="daily-container">
      {/* Destination selection form */}
      <div className="daily-form">
        <select
          name="to"
          value={form.to}
          onChange={handleChange}
          className="daily-input"
        >
          <option value="" disabled>Select Destination</option>
          {destinations.map((dest, idx) => (
            <option key={idx} value={dest}>{dest}</option>
          ))}
        </select>

        <button onClick={handleSubmit} className="daily-button" disabled={loading}>
          {loading ? "Searching..." : "Find Buses"}
        </button>
      </div>

      {error && <div style={{ color: "red", marginTop: 8 }}>{error}</div>}

      {/* Display filtered routeId and timings */}
      {buses.length > 0 && (
        <div className="results-container">
          {buses.map((bus) => (
            <div key={bus.routeId} className="bus-card">
              <p><strong>Route ID:</strong> {bus.routeId}</p>
              <p><strong>Timings:</strong> {bus.times?.join(", ") || "N/A"}</p>
            </div>
          ))}
        </div>
      )}

      {buses.length === 0 && !loading && !error && <div style={{ marginTop: 12 }}>No buses found.</div>}
    </div>
  );
}

export default DailyCommute;

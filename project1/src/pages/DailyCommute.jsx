import { useState, useEffect } from "react";
import "./DailyCommute.css";

function DailyCommute() {
  const [form, setForm] = useState({ to: "" });
  const [destinations, setDestinations] = useState([]);
  const [buses, setBuses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedTimings, setSelectedTimings] = useState({}); 

  // Fetch destinations when page loads
  useEffect(() => {
    setError(null);
    fetch("http://localhost:8080/transport/fetchdestination", {
      method: "GET",
      credentials: "include",
      headers: { Accept: "application/json" },
    })
      .then(async (res) => {
        if (res.status === 401) throw new Error("Unauthorized. Please log in.");
        if (!res.ok) {
          const text = await res.text().catch(() => "");
          throw new Error("Failed to fetch destinations: " + text);
        }
        const text = await res.text();
        return text ? JSON.parse(text) : [];
      })
      .then((data) => setDestinations(data || []))
      .catch((err) => {
        console.error("Fetch destinations error:", err);
        setError(err.message);
        setDestinations([]);
      });
  }, []);

 const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // update selected timing for a route
  const handleTimingChange = (routeId, timing) => {
    setSelectedTimings(prev => ({ ...prev, [routeId]: timing }));
  };

  // Fetch buses by destination
  const handleSubmit = async () => {
    if (!form.to) {
      alert("Please select a destination.");
      return;
    }

    setLoading(true);
    setError(null);
    setBuses([]);

    try {
      const url = `http://localhost:8080/transport/daily-commute?destination=${encodeURIComponent(form.to)}`;
      const res = await fetch(url, {
        method: "GET",
        credentials: "include",
      });

      if (res.status === 401) {
        throw new Error("Unauthorized — please log in.");
      }
      if (!res.ok) {
        const text = await res.text().catch(() => "");
        throw new Error(`Failed to fetch buses: ${text}`);
      }

      const data = await res.json();
      setBuses(data || []);
    } catch (err) {
      console.error("Fetch buses error:", err);
      setError(err.message || "Network error");
    } finally {
      setLoading(false);
    }
  };

   const handleBook = async (bus) => {
    const timing = selectedTimings[bus.routeId];
    if (!timing) {
      alert("Please select a timing before booking.");
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/transport/book", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          routeId: bus.routeId,
          timeChosen: timing
        })
      });

      if (res.status === 401) throw new Error("Unauthorized — please log in.");
      if (!res.ok) {
        const text = await res.text().catch(() => "");
        throw new Error(`Failed to book: ${text}`);
      }

      alert(`Successfully booked route ${bus.name || bus.routeId} at ${timing}`);
    } catch (err) {
      console.error("Booking error:", err);
      alert(err.message || "Network error while booking");
    }
  };

  return (
    <div className="daily-container">
      {/* Destination Selection */}
      <div className="daily-form">
        <select
          name="to"
          value={form.to}
          onChange={handleChange}
          className="daily-input"
        >
          <option value="" disabled>
            Select Destination
          </option>
          {destinations.map((dest, idx) => (
            <option key={idx} value={dest}>
              {dest}
            </option>
          ))}
        </select>

        <button
          onClick={handleSubmit}
          className="daily-button"
          disabled={loading}
        >
          {loading ? "Searching..." : "Find Buses"}
        </button>
      </div>

      {error && <div className="error-text">{error}</div>}

      {/* Show routes and their timing dropdowns */}
      {buses.length > 0 && (
        <div className="results-container">
          {buses.map((bus) => (
            <div key={bus.routeId} className="bus-card">
              <h3>{bus.name || `Route ${bus.routeId}`}</h3>
              <p>
                <strong>Origin: </strong> {bus.origin}
              </p>
              <p>
                <strong>Destination:</strong> {bus.destination}
              </p>
              <p>
                <strong>Price:</strong> {bus.price}
              </p>

              <label><strong>Select Timing:</strong></label>
              {/* <select className="timing-dropdown">
  {Array.isArray(bus.timings)
    ? bus.timings.map((time, i) => (
        <option key={i} value={time}>{time}</option>
      ))
    : typeof bus.timings === "string"
    ? bus.timings.split(",").map((time, i) => (
        <option key={i} value={time.trim()}>{time.trim()}</option>
      ))
    : <option disabled>No timings available</option>}
</select> */}
<select
                className="timing-dropdown"
                value={selectedTimings[bus.routeId] || ""}
                onChange={(e) => handleTimingChange(bus.routeId, e.target.value)}
              >
                <option value="" disabled>Select Timing</option>
                {Array.isArray(bus.timings) && bus.timings.map((time, i) => (
                  <option key={i} value={time}>{time}</option>
                ))}
              </select>
              <button
                className="daily-button"
                style={{ marginTop: "8px" }}
                onClick={() => handleBook(bus)}
              >
                Book
              </button>

            </div>
          ))}
        </div>
      )}

      {!loading && buses.length === 0 && !error && (
        <div className="no-data-text">No buses found.</div>
      )}
    </div>
  );
}

export default DailyCommute;

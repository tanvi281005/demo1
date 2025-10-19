import { useState, useEffect } from "react";
import "./FindResources.css";

function AcademicResources() {
  const [form, setForm] = useState({ subject: "", type: "" });
  const [resources, setResources] = useState([]);
  const [loadingResources, setLoadingResources] = useState(false);
  const [error, setError] = useState(null);
  const [searchAttempted, setSearchAttempted] = useState(false);

  // ✅ Fetch notifications on page load
  useEffect(() => {
    fetch("http://localhost:8080/resources/notifications/fetch", {
      method: "GET",
      credentials: "include",
    })
      .then((res) => (res.ok ? res.json() : []))
      .then((notifications) => {
        if (notifications.length > 0) {
          notifications.forEach((n) => {
            alert(`${n.title}\n${n.message}`);
          });
        }
      })
      .catch((err) => console.error("Notification fetch error:", err));
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!form.subject || !form.type) {
      alert("Please enter subject code and select material type.");
      return;
    }

    setLoadingResources(true);
    setError(null);
    setResources([]);
    setSearchAttempted(true);

    try {
      const url = `http://localhost:8080/resources/fetch?subject=${encodeURIComponent(
        form.subject
      )}&type=${encodeURIComponent(form.type)}`;
      const res = await fetch(url, {
        method: "GET",
        credentials: "include",
      });

      if (res.status === 401) throw new Error("Unauthorized — please log in.");
      if (!res.ok) throw new Error("Failed to fetch resources.");

      const data = await res.json();
      setResources(data || []);
    } catch (err) {
      console.error("Fetch resources error:", err);
      setError(err.message || "Network error");
    } finally {
      setLoadingResources(false);
    }
  };

  return (
    <div className="resources-container">
      {/* Filters */}
      <div className="resources-form">
        {/* 🧾 Subject Code Input */}
        <input
          type="text"
          name="subject"
          placeholder="Enter Subject Code"
          value={form.subject}
          onChange={handleChange}
          className="resources-input"
        />

        {/* 📂 Material Type Dropdown */}
        <select
          name="type"
          value={form.type}
          onChange={handleChange}
          className="resources-input"
        >
          <option value="" disabled>
            Select Resource Type
          </option>
          <option value="PYQ">Previous Year Papers</option>
          <option value="Notes">Notes</option>
          <option value="Assignment">Assignment</option>
          <option value="Tutorials">Tutorials</option>
          <option value="Books">Books</option>
        </select>

        <button
          onClick={handleSubmit}
          className="resources-button"
          disabled={loadingResources}
        >
          {loadingResources ? "Searching..." : "Find Resources"}
        </button>
      </div>

      {error && <div className="error-text">{error}</div>}

      {/* Show resources if available */}
      {!loadingResources && resources.length > 0 && (
        <div className="results-container">
          {resources.map((res) => (
            <div key={res.resourceId} className="resource-card">
              <p>
                <strong>Subject:</strong> {res.subjectCode || "N/A"}
              </p>
              <p>
                <strong>Type:</strong> {res.resourceType}
              </p>
              <a
                href={`http://localhost:8080/resources/download/${res.resourceId}`}
                target="_blank"
                rel="noopener noreferrer"
                className="resource-link"
              >
                View / Download PDF
              </a>
            </div>
          ))}
        </div>
      )}

      {/* 🛎️ Show "Notify Me" only after search + no resources */}
      {!loadingResources &&
        searchAttempted &&
        resources.length === 0 &&
        !error && (
          <div className="no-data-text">
            No resources found. <br />
            <button
              className="notify-button"
              onClick={async () => {
                try {
                  const res = await fetch(
                    `http://localhost:8080/resources/notify-me?subject=${encodeURIComponent(
                      form.subject
                    )}&type=${encodeURIComponent(form.type)}`,
                    { method: "POST", credentials: "include" }
                  );

                  const msg = await res.text();
                  alert(msg);
                } catch {
                  alert("Failed to register for notifications.");
                }
              }}
            >
              Notify Me
            </button>
          </div>
        )}
    </div>
  );
}

export default AcademicResources;

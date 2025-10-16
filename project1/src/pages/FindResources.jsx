import { useState, useEffect } from "react";
import "./FindResources.css";

function AcademicResources() {
  const [form, setForm] = useState({ subject: "", type: "" });
  const [subjects, setSubjects] = useState([]);
  const [resources, setResources] = useState([]);
  const [loadingSubjects, setLoadingSubjects] = useState(true);
  const [loadingResources, setLoadingResources] = useState(false);
  const [error, setError] = useState(null);

  // Fetch subjects on page load
  useEffect(() => {
    setError(null);
    setLoadingSubjects(true);

    fetch("http://localhost:8080/resources/fetch-subjects", {
      method: "GET",
      credentials: "include",
      headers: { Accept: "application/json" },
    })
      .then((res) => {
        if (res.status === 401) throw new Error("Unauthorized. Please log in.");
        if (!res.ok) throw new Error("Failed to fetch subjects.");
        return res.json(); // directly parse JSON
      })
      .then((data) => setSubjects(data || []))
      .catch((err) => {
        console.error("Fetch subjects error:", err);
        setError(err.message);
        setSubjects([]);
      })
      .finally(() => setLoadingSubjects(false));
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!form.subject || !form.type) {
      alert("Please select both subject and resource type.");
      return;
    }

    setLoadingResources(true);
    setError(null);
    setResources([]);

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
        <select
          name="subject"
          value={form.subject}
          onChange={handleChange}
          className="resources-input"
        >
          <option value="" disabled>
            {loadingSubjects ? "Loading subjects..." : "Select Subject"}
          </option>
          {subjects.map((subj, idx) => (
            <option key={idx} value={subj}>
              {subj}
            </option>
          ))}
        </select>

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
          disabled={loadingResources || loadingSubjects}
        >
          {loadingResources ? "Searching..." : "Find Resources"}
        </button>
      </div>

      {error && <div className="error-text">{error}</div>}

      {/* Show resources */}
      {!loadingResources && resources.length > 0 && (
        <div className="results-container">
          {resources.map((res) => (
  <div key={res.resourceId} className="resource-card">
    <p><strong>Subject:</strong> {res.subjectCode || "N/A"}</p>
    <p><strong>Type:</strong> {res.resourceType}</p>
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

      {!loadingResources && resources.length === 0 && !error && (
        <div className="no-data-text">No resources found.</div>
      )}
    </div>
  );
}

export default AcademicResources;

import { useState } from "react";
import "./CounsellorForm.css";

function CounsellorForm() {
  const [form, setForm] = useState({
    specialization: "",
    selfDescription: "",
    timings: [""],
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleTimingChange = (index, value) => {
    const updated = [...form.timings];
    updated[index] = value;
    setForm({ ...form, timings: updated });
  };

  const addTimingField = () =>
    setForm({ ...form, timings: [...form.timings, ""] });

  const handleSubmit = (e) => {
  e.preventDefault();

  // Build payload
  const payload = {
    specialization: form.specialization,
    selfDescription: form.selfDescription,
    timings: form.timings.filter(time => time !== ""), // remove empty timings
  };

  // Send POST request to backend
  fetch("http://localhost:8080/counsellor/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify(payload),
  })
    .then(async (res) => {
      if (!res.ok) {
        const text = await res.text();
        throw new Error(text);
      }
      return res.text();
    })
    .then((message) => {
      alert(message);  // Success message from backend
      setForm({ specialization: "", selfDescription: "", timings: [""] }); // Reset form
    })
    .catch((err) => {
      console.error(err);
      alert("❌ Registration failed: " + err.message);
    });
};


  return (
      <div className="counsellor-form">
        <div className="inside">
        <h2 className="counsellor-title">🧠 Register as Counsellor</h2>

        <select
          name="specialization"
          value={form.specialization}
          onChange={handleChange}
          className="counsellor-input"
          required
        >
          <option value="" disabled hidden>
            Select Specialization
          </option>
          <option value="Academics">Academics</option>
          <option value="SubstanceAddiction">Substance Addiction</option>
          <option value="StressAndAnxiety">Stress & Anxiety</option>
          <option value="GriefAndLoss">Grief & Loss</option>
          <option value="PersonalRelationships">
            Personal Relationships
          </option>
        </select>

        <textarea
          name="selfDescription"
          placeholder="Write a short self description..."
          value={form.selfDescription}
          onChange={handleChange}
          className="counsellor-input"
          rows="4"
        />

        <label className="timing-label">Available Timings:</label>
        {form.timings.map((time, i) => (
          <input
            key={i}
            type="time"
            value={time}
            onChange={(e) => handleTimingChange(i, e.target.value)}
            className="counsellor-input"
          />
        ))}

        <button
          type="button"
          onClick={addTimingField}
          className="counsellor-button small-btn"
        >
          ➕ Add Another Timing
        </button>

        <button onClick={handleSubmit} className="counsellor-button">
          Submit
        </button>
        </div>
      </div>
    
  );
}

export default CounsellorForm;

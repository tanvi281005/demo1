import { useState } from "react";
import "./UrgentForm.css"; // Import the CSS file

function UrgentForm() {
  const [form, setForm] = useState({ from: "", to: "", urgency: "" });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    alert(
      `Urgent request: From ${form.from} to ${form.to}, Reason: ${form.urgency}`
    );
  };

  return (
    <div className="urgent-container">
      <h2 className="urgent-title">Urgent Transport Request</h2>
      <div className="urgent-form">
        <input
          name="from"
          placeholder="From"
          onChange={handleChange}
          className="urgent-input"
        />
        <input
          name="to"
          placeholder="To"
          onChange={handleChange}
          className="urgent-input"
        />
        <select
          name="urgency"
          onChange={handleChange}
          className="urgent-input"
        >
          <option value="">Select Urgency</option>
          <option value="medical">Medical Emergency</option>
          <option value="work">Work Deadline</option>
          <option value="personal">Personal</option>
        </select>
        <button onClick={handleSubmit} className="urgent-button">
          Submit Request
        </button>
      </div>
    </div>
  );
}

export default UrgentForm;

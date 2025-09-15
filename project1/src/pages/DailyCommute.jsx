import { useState } from "react";
import "./DailyCommute.css"; // Import the CSS file

function DailyCommute() {
  const [form, setForm] = useState({ from: "", to: "", date: "" });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    alert(`Searching buses from ${form.from} to ${form.to} on ${form.date}`);
  };

  return (
    <div className="daily-container">
      <h2 className="daily-title">Book Your Daily Commute</h2>
      <div className="daily-form">
        <input
          name="from"
          placeholder="From"
          onChange={handleChange}
          className="daily-input"
        />
        <input
          name="to"
          placeholder="To"
          onChange={handleChange}
          className="daily-input"
        />
        <input
          type="date"
          name="date"
          onChange={handleChange}
          className="daily-input"
        />
        <button onClick={handleSubmit} className="daily-button">
          Find Buses
        </button>
      </div>
    </div>
  );
}

export default DailyCommute;

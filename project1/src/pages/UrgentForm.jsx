import { useState } from "react";
import "./UrgentForm.css";

function UrgentForm() {
  const [form, setForm] = useState({
    from: "",
    to: "",
    name: "",
    number: "",
    nurse: "",
    necessity: "",
    friendNumber: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    alert(
      `🚑 Urgent Request\n
      From: ${form.from}\n
      To: ${form.to}\n
      Name: ${form.name}\n
      Phone: ${form.number}\n
      Nurse Required: ${form.nurse}\n
      Medical Necessity: ${form.necessity}\n
      Friend's Number: ${form.friendNumber}`
    );
  };

  return (
    <div className="urgent-container">
      <div className="urgent-form">
        <h2 className="urgent-title">🚑 Urgent Transport Request</h2>
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
        <input
          name="name"
          placeholder="Your Name"
          onChange={handleChange}
          className="urgent-input"
        />
        <input
          name="number"
          placeholder="Your Phone Number"
          type="tel"
          onChange={handleChange}
          className="urgent-input"
        />
        <select name="nurse" onChange={handleChange} className="urgent-input">
          <option value="">Nurse Required?</option>
          <option value="Yes">Yes</option>
          <option value="No">No</option>
        </select>
        <textarea
          name="necessity"
          placeholder="Any Medical Necessity (if any)"
          onChange={handleChange}
          className="urgent-input"
          rows="3"
        />
        <input
          name="friendNumber"
          placeholder="Friend's Number (if coming along)"
          type="tel"
          onChange={handleChange}
          className="urgent-input"
        />
        <button onClick={handleSubmit} className="urgent-button">
          Submit Request
        </button>
      </div>
    </div>
  );
}

export default UrgentForm;

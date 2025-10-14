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

  const handleSubmit = async (e) => {
  e.preventDefault();

  const data = {
    fromPos: form.from,
    toPos: form.to,
    patientCondition: form.name,
    ambulanceNo: form.number,
    nurseRequired: form.nurse === "Yes",
    necessity: form.necessity,
  };

  try {
    const response = await fetch("http://localhost:8080/urgent", {
      method: "POST",
      credentials: "include", // 🔑 sends session cookie (JSESSIONID)
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json",
      },
      body: JSON.stringify(data),
    });

    console.log("urgent response status:", response.status);

    if (response.ok) {
      const text = await response.text();
      alert("✅ " + text);
    } else if (response.status === 401) {
      alert("⚠️ Please log in first!");
    } else if (response.status === 403) {
      alert("🚫 Forbidden — your session might have expired. Please log in again.");
    } else {
      const errorText = await response.text();
      alert("❌ Error: " + errorText);
    }
  } catch (err) {
    console.error("Fetch /urgent error:", err);
    alert("❌ Failed to send request: " + err.message);
  }
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
        <select
          name="nurse"
          value={form.nurse}
          onChange={handleChange}
          className="urgent-input"
          required
        >
          <option value="" disabled hidden>
            Nurse Required?
          </option>
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

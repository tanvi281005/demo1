import { useState } from "react";
import "./DailyCommute.css";

function DailyCommute() {
  const [form, setForm] = useState({ from: "", to: "", date: "" });
  const [buses, setBuses] = useState([]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    if (!form.from || !form.to || !form.date) {
      alert("Please fill in all fields.");
      return;
    }

    // Dummy bus data (random for demo)
    const dummyBuses = [
      { id: 1, name: "Express Line", fare: "₹150", times: ["8:00 AM", "10:00 AM", "2:00 PM"] },
      { id: 2, name: "City Rider", fare: "₹120", times: ["9:00 AM", "1:00 PM", "5:00 PM"] },
      { id: 3, name: "Metro Travels", fare: "₹200", times: ["7:30 AM", "11:30 AM", "6:00 PM"] },
    ];

    setBuses(dummyBuses);
  };

  const handleBook = (bus, time) => {
    if (!time) {
      alert("Please select a time before booking.");
      return;
    }
    alert(`Booked ${bus.name} at ${time} for ${bus.fare}`);
  };

  return (
    <div className="daily-container">
      {/* 🔴 Removed the <h2 className="daily-title">Book Your Daily Commute</h2> */}

      {/* Form */}
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

      {/* Results */}
      {buses.length > 0 && (
        <div className="results-container">
          {buses.map((bus) => (
            <div key={bus.id} className="bus-card">
              <h4 className="bus-name">{bus.name}</h4>
              <p className="bus-fare">Fare: {bus.fare}</p>
              <select
                className="time-dropdown"
                defaultValue=""
                id={`time-${bus.id}`}
              >
                <option value="" disabled>
                  Select time
                </option>
                {bus.times.map((time, idx) => (
                  <option key={idx} value={time}>
                    {time}
                  </option>
                ))}
              </select>
              <button
                className="book-button"
                onClick={() => {
                  const timeSelect = document.getElementById(`time-${bus.id}`);
                  handleBook(bus, timeSelect.value);
                }}
              >
                Book
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default DailyCommute;

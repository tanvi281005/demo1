import { useState } from "react";

function DailyCommute() {
  const [form, setForm] = useState({ from: "", to: "", date: "" });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    alert(`Searching buses from ${form.from} to ${form.to} on ${form.date}`);
  };

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center p-6">
      <h2 className="text-2xl font-bold text-blue-700 mb-6">Book Your Daily Commute</h2>
      <div className="bg-white shadow-md rounded-lg p-6 w-full max-w-md">
        <input
          name="from"
          placeholder="From"
          onChange={handleChange}
          className="w-full p-3 mb-4 border rounded-lg focus:ring-2 focus:ring-blue-400"
        />
        <input
          name="to"
          placeholder="To"
          onChange={handleChange}
          className="w-full p-3 mb-4 border rounded-lg focus:ring-2 focus:ring-blue-400"
        />
        <input
          type="date"
          name="date"
          onChange={handleChange}
          className="w-full p-3 mb-4 border rounded-lg focus:ring-2 focus:ring-blue-400"
        />
        <button
          onClick={handleSubmit}
          className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 transition"
        >
          Find Buses
        </button>
      </div>
    </div>
  );
}

export default DailyCommute;

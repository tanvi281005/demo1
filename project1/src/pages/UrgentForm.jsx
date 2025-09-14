import { useState } from "react";

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
    <div className="min-h-screen bg-gradient-to-b from-red-100 to-red-300 flex flex-col items-center p-6">
      <h2 className="text-2xl font-bold text-red-700 mb-6">Urgent Transport Request</h2>
      <div className="bg-white shadow-md rounded-lg p-6 w-full max-w-md">
        <input
          name="from"
          placeholder="From"
          onChange={handleChange}
          className="w-full p-3 mb-4 border rounded-lg focus:ring-2 focus:ring-red-400"
        />
        <input
          name="to"
          placeholder="To"
          onChange={handleChange}
          className="w-full p-3 mb-4 border rounded-lg focus:ring-2 focus:ring-red-400"
        />
        <select
          name="urgency"
          onChange={handleChange}
          className="w-full p-3 mb-4 border rounded-lg focus:ring-2 focus:ring-red-400"
        >
          <option value="">Select Urgency</option>
          <option value="medical">Medical Emergency</option>
          <option value="work">Work Deadline</option>
          <option value="personal">Personal</option>
        </select>
        <button
          onClick={handleSubmit}
          className="w-full bg-red-600 text-white py-3 rounded-lg hover:bg-red-700 transition"
        >
          Submit Request
        </button>
      </div>
    </div>
  );
}

export default UrgentForm;

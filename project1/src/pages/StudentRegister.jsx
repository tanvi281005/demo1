import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./StudentRegister.css";
const StudentRegister = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    first_name: "",
    middle_name: "",
    last_name: "",
    email: "",
    phone: "",
    year: "",
    branch: "",
    gender: "",
    hostel: "",
    guardian_name: "",
    guardian_number: "",
    wallet: "0.00",
    dob: "",
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData({ ...formData, [id]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Normally, send data to backend and handle errors

    alert("Registration successful!");
    navigate("/"); // Redirect to Home page
  };

  return (
    <div className="studentPage">
      <div className="studentPage-container">
        <h1>Student Registration</h1>
        <form onSubmit={handleSubmit}>
          <label htmlFor="first_name">First Name:</label>
          <input type="text" id="first_name" value={formData.first_name} onChange={handleChange} required />

          <label htmlFor="middle_name">Middle Name:</label>
          <input type="text" id="middle_name" value={formData.middle_name} onChange={handleChange} />

          <label htmlFor="last_name">Last Name:</label>
          <input type="text" id="last_name" value={formData.last_name} onChange={handleChange} required />

          <label htmlFor="email">Email:</label>
          <input type="email" id="email" value={formData.email} onChange={handleChange} required />

          <label htmlFor="phone">Phone:</label>
          <input type="text" id="phone" value={formData.phone} onChange={handleChange} required />

          <label htmlFor="year">Year:</label>
          <input type="number" id="year" value={formData.year} onChange={handleChange} min="1" max="5" />

          <label htmlFor="branch">Branch:</label>
          <input type="text" id="branch" value={formData.branch} onChange={handleChange} />

          <label htmlFor="gender">Gender:</label>
          <select id="gender" value={formData.gender} onChange={handleChange}>
            <option value="">--Select Gender--</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
            <option value="other">Other</option>
          </select>

          <label htmlFor="hostel">Hostel:</label>
          <input type="text" id="hostel" value={formData.hostel} onChange={handleChange} />

          <label htmlFor="guardian_name">Guardian Name:</label>
          <input type="text" id="guardian_name" value={formData.guardian_name} onChange={handleChange} />

          <label htmlFor="guardian_number">Guardian Phone:</label>
          <input type="text" id="guardian_number" value={formData.guardian_number} onChange={handleChange} />

          <label htmlFor="dob">Date of Birth:</label>
          <input type="date" id="dob" value={formData.dob} onChange={handleChange} />

          <button type="submit">Register</button>
        </form>
        <div className="login-text">
          Already have an account? <a onClick={() => navigate("/login")}>Login here</a>
        </div>
      </div>
    </div>
  );
};

export default StudentRegister;

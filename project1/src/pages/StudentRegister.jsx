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

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Map frontend keys to backend Student model
      const payload = {
        firstName: formData.first_name,
        middleName: formData.middle_name,
        lastName: formData.last_name,
        email: formData.email,
        phone: formData.phone,
        year: formData.year ? parseInt(formData.year) : null,
        branch: formData.branch,
        gender: formData.gender ? formData.gender.charAt(0).toUpperCase() + formData.gender.slice(1) : null, 
        hostel: formData.hostel,
        guardianName: formData.guardian_name,
        guardianNumber: formData.guardian_number,
        wallet: parseFloat(formData.wallet),
        dob: formData.dob ? formData.dob : null, // backend expects LocalDate string yyyy-MM-dd
      };

      const response = await fetch("http://localhost:8080/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        const errorText = await response.text();
        alert("Registration failed: " + errorText);
        return;
      }

      const data = await response.json();
      alert("Registration successful!");
      console.log("Saved student:", data);

      // navigate("/home"); // Redirect to Home page
    } catch (error) {
      console.error("Error:", error);
      alert("Something went wrong. Please try again.");
    }
  };

  return (
    <div className="studentPage">
      <div className="studentPage-container">
        <h1>Student Registration</h1>
        <form onSubmit={handleSubmit}>
          {/* Same inputs as before */}
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
          Already have an account? <a onClick={() => navigate("/api/login")}>Login here</a>
        </div>
      </div>
    </div>
  );
};

export default StudentRegister;

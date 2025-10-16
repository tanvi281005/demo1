import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const StudentLogin = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ email: "", dob: "" });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData({ ...formData, [id]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

  try {
    const response = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include", 
      body: JSON.stringify({
        email: formData.email,
        dob: formData.dob
      })
    });

    if (response.ok) {
      alert("Login successful!");
      navigate("/home"); // protected page
    } else {
      const errorText = await response.text();
      alert("Login failed: " + errorText);
    }
  } catch (error) {
    console.error("Login error:", error);
    alert("Something went wrong. Please try again.");
  }
};


  return (
    <div className="studentPage">
      <div className="studentPage-container">
        <h1>Student Login</h1>
        <form onSubmit={handleSubmit}>
          <label htmlFor="email">Email:</label>
          <input type="email" id="email" value={formData.email} onChange={handleChange} required />

          <label htmlFor="dob">Password:</label>
          <input type="text" id="dob" value={formData.dob} onChange={handleChange} required />

          <button type="submit">Login</button>
        </form>
        <div className="login-text">
          Don't have an account? <a onClick={() => navigate("/")}>Register here</a>
        </div>
      </div>
    </div>
  );
};

export default StudentLogin;

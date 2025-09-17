import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const StudentLogin = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData({ ...formData, [id]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Normally, authenticate user here

    alert("Login successful!");
    navigate("/home"); // Redirect to Home page
  };

  return (
    <div className="studentPage">
      <div className="studentPage-container">
        <h1>Student Login</h1>
        <form onSubmit={handleSubmit}>
          <label htmlFor="email">Email:</label>
          <input type="email" id="email" value={formData.email} onChange={handleChange} required />

          <label htmlFor="password">Password:</label>
          <input type="password" id="password" value={formData.password} onChange={handleChange} required />

          <button type="submit">Login</button>
        </form>
        <div className="login-text">
          Don't have an account? <a onClick={() => navigate("/register")}>Register here</a>
        </div>
      </div>
    </div>
  );
};

export default StudentLogin;

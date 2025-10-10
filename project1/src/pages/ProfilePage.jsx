import React, { useEffect, useState } from "react";
import "./ProfilePage.css";

const ProfilePage = () => {
  const [student, setStudent] = useState(null);
  const [editMode, setEditMode] = useState(false);
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await fetch("http://localhost:8080/profile", {
          method: "GET",
          credentials: "include", 
        });
        if (response.ok) {
          const data = await response.json();
          setStudent(data);
        } else {
          console.error("Failed to fetch profile");
        }
      } catch (error) {
        console.error("Error fetching profile:", error);
      }
    };
    fetchProfile();
  }, []);

  if (!student) {
    return <div className="loading">Loading...</div>;
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setStudent((prev) => ({ ...prev, [name]: value }));
  };

  const handleEditToggle = () => {
    setEditMode(!editMode);
  };

 const handleSave = async () => {
  try {
    const response = await fetch("http://localhost:8080/profile/update", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify(student),
    });

    if (response.ok) {
      const updatedStudent = await response.json();
      setStudent(updatedStudent);
      setEditMode(false);
    } else {
      console.error("Failed to update profile");
    }
  } catch (error) {
    console.error("Error updating profile:", error);
  }
};


  return (
    <div className="profile-container">
      <div className="profile-card">
        <div className="profile-header">
          <h1>Welcome, {student.firstName}</h1>
          <div className="profile-actions">
            <button onClick={handleEditToggle} className="edit-btn">
              {editMode ? "Cancel" : "Edit Profile"}
            </button>
            {editMode && (
              <button onClick={handleSave} className="save-btn">
                Save
              </button>
            )}
          </div>
        </div>

        <div className="profile-main">
          {/* <div className="profile-picture">
            <img src="/profile-pic-placeholder.png" alt="Student" />
          </div> */}

          <div className="profile-info-grid">
            {[
              { label: "First Name", name: "firstName" },
              { label: "Middle Name", name: "middleName" },
              { label: "Last Name", name: "lastName" },
              { label: "Email", name: "email" },
              { label: "Phone", name: "phone" },
              { label: "Gender", name: "gender" },
              { label: "Year", name: "year" },
              { label: "Branch", name: "branch" },
              { label: "Hostel", name: "hostel" },
              { label: "Guardian Name", name: "guardianName" },
              { label: "Guardian Phone", name: "guardianNumber" },
              { label: "Wallet Balance", name: "wallet" },
              { label: "Date of Birth", name: "dob" },
              { label: "Age", name: "age" },
            ].map((field) => (
              <div className="profile-field" key={field.name}>
                <label>{field.label}</label>
                <input
                  type="text"
                  name={field.name}
                  value={student[field.name] || ""}
                  readOnly={!editMode}
                  onChange={handleChange}
                />
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;

import { useState } from "react";
import "./UploadResourceForm.css";

function UploadResourceForm() {
  const [form, setForm] = useState({
    subjectCode: "",
    subjectName: "",
    materialType: "",
    file: null,
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setForm({
      ...form,
      [name]: files ? files[0] : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.subjectCode || !form.subjectName || !form.materialType || !form.file) {
      alert("⚠️ Please fill in all fields and upload a file.");
      return;
    }

    const formData = new FormData();
    formData.append("subjectCode", form.subjectCode);
    formData.append("course", form.subjectName);
    formData.append("resourceType", form.materialType);
    formData.append("file", form.file);

    try {
      const response = await fetch("http://localhost:8080/resources/upload", {
        method: "POST",
        credentials: "include",
        body: formData,
      });

      console.log("Upload response status:", response.status);

      if (response.ok) {
        const text = await response.text();
        alert("✅ " + text);
      } else if (response.status === 401) {
        alert("⚠️ Please log in first!");
      } else if (response.status === 403) {
        alert("🚫 Forbidden — you are not allowed to upload or your session expired.");
      } else {
        const errorText = await response.text();
        alert("❌ Error: " + errorText);
      }
    } catch (err) {
      console.error("Fetch /resources/upload error:", err);
      alert("❌ Failed to upload resource: " + err.message);
    }
  };

  return (
    <div className="resource-container">
      <video className="background-video" autoPlay loop muted playsInline>
        <source src="/images/ar_video.mp4" type="video/mp4" />
        Your browser does not support the video tag.
      </video>

      <form className="resource-form" onSubmit={handleSubmit}>
        <h2 className="resource-title">📘 Upload Resource</h2>

        <input
          name="subjectCode"
          placeholder="Subject Code"
          value={form.subjectCode}
          onChange={handleChange}
          className="resource-input"
        />

        <input
          name="subjectName"
          placeholder="Subject Name"
          value={form.subjectName}
          onChange={handleChange}
          className="resource-input"
        />

        <select
          name="materialType"
          value={form.materialType}
          onChange={handleChange}
          className="resource-input"
          required
        >
          <option value="" disabled hidden>
            Select Material Type
          </option>
          <option value="PYQ">Previous Year Papers</option>
          <option value="Notes">Notes</option>
          <option value="Assignment">Assignment</option>
          <option value="Tutorials">Tutorials</option>
          <option value="Books">Books</option>
        </select>

        <input
          type="file"
          name="file"
          accept="application/pdf"
          onChange={handleChange}
          className="resource-input"
          required
        />

        <button type="submit" className="resource-button">
          Upload
        </button>
      </form>
    </div>
  );
}

export default UploadResourceForm;

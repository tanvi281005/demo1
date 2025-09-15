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

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!form.subjectCode || !form.subjectName || !form.materialType || !form.file) {
      alert("⚠️ Please fill in all fields and upload a file.");
      return;
    }

    alert(
      `📘 Resource Upload\n
      Subject Code: ${form.subjectCode}\n
      Subject Name: ${form.subjectName}\n
      Material Type: ${form.materialType}\n
      File: ${form.file.name}`
    );
  };

  return (
    <div className="resource-container">
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
        >
          <option value="">Select Material Type</option>
          <option value="Notes">Notes</option>
          <option value="Assignment">Assignment</option>
          <option value="Question Paper">Question Paper</option>
          <option value="Other">Other</option>
        </select>

        <input
          type="file"
          name="file"
          accept="application/pdf"
          onChange={handleChange}
          className="resource-input"
        />

        <button type="submit" className="resource-button">
          Upload
        </button>
      </form>
    </div>
  );
}

export default UploadResourceForm;

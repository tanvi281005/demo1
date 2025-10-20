import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./CounsellorDetails.css";

const CounsellorDetails = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const counsellor = counsellors[id];

  const [selectedSlot, setSelectedSlot] = useState("");
  const [confirmationMsg, setConfirmationMsg] = useState("");

  useEffect(() => {
    if (!counsellor) {
      setConfirmationMsg("Counsellor not found.");
    }
  }, [counsellor]);

  const handleBooking = () => {
    if (!selectedSlot) {
      alert("Please select a time slot.");
      return;
    }

    const today = new Date();
    const dateStr = today.toLocaleDateString(undefined, {
      year: "numeric",
      month: "long",
      day: "numeric",
    });

    setConfirmationMsg(
      `Your slot with ${counsellor.name} on ${dateStr} at ${selectedSlot} has been booked! 🎉`
    );
  };

  if (!counsellor) {
    return (
      <div className="text-center text-2xl font-bold text-red-600 p-8">
        {confirmationMsg}
      </div>
    );
  }

  return (
    <div className="counsellor-page">
      <div className="counsellor-container">
        <button className="back-button" onClick={() => navigate(-1)}>
          ← Back
        </button>

        <div className="counsellor-header">
          <img
            src={counsellor.img}
            alt={counsellor.name}
            className="counsellor-image"
          />
          <h1 className="counsellor-name">{counsellor.name}</h1>
        </div>

        <p className="counsellor-specialization">
          Specialization: {counsellor.specialization}
        </p>
        <p className="counsellor-experience">{counsellor.experience}</p>

        <div className="stars">
          {Array.from({ length: 5 }, (_, i) => (
            <span key={i} className={`star ${i < counsellor.rating ? "" : "empty"}`}>
              ★
            </span>
          ))}
        </div>

        <p className="counsellor-description">{counsellor.description}</p>

        <h3 className="slots-title">Available Time Slots</h3>
        <div className="slot-buttons">
          {counsellor.slots.map((slot, index) => (
            <button
              key={index}
              className={`slot-button ${selectedSlot === slot ? "selected" : ""}`}
              onClick={() => setSelectedSlot(slot)}
            >
              {slot}
            </button>
          ))}
        </div>

        <button className="book-button" onClick={handleBooking}>
          Book Slot
        </button>

        {confirmationMsg && (
          <p className="confirmation-message">{confirmationMsg}</p>
        )}
      </div>
    </div>
  );
};

export default CounsellorDetails;

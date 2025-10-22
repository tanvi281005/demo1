import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./CounsellorDetails.css";

const CounsellorDetails = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [counsellorData, setCounsellorData] = useState(null);
  const [selectedSlot, setSelectedSlot] = useState("");
  const [confirmationMsg, setConfirmationMsg] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchCounsellorDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8080/counsellor/${id}`);
        if (!response.ok) {
          throw new Error("Failed to fetch counsellor details");
        }
        const data = await response.json();
        setCounsellorData(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCounsellorDetails();
  }, [id]);

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
      `Your slot with ${counsellorData.counsellor.specialization} counsellor on ${dateStr} at ${selectedSlot} has been booked! 🎉`
    );
  };

  if (loading) {
    return <div className="text-center text-lg p-8">Loading...</div>;
  }

  if (error) {
    return <div className="text-center text-red-600 p-8">Error: {error}</div>;
  }

  if (!counsellorData || !counsellorData.counsellor) {
    return <div className="text-center text-red-600 p-8">Counsellor not found.</div>;
  }

  const { counsellor, availableTimings } = counsellorData;

  return (
    <div className="counsellor-page">
      <div className="counsellor-container">
        <button className="back-button" onClick={() => navigate(-1)}>
          ← Back
        </button>

        <div className="counsellor-header">
          <img
            src="https://cdn-icons-png.flaticon.com/512/4359/4359931.png"
            alt="Counsellor"
            className="counsellor-image"
          />
          <h1 className="counsellor-name">Counsellor #{counsellor.counsellorId}</h1>
        </div>

        <p className="counsellor-specialization">
          Specialization: {counsellor.specialization.replaceAll("_", " ")}
        </p>
        <p className="counsellor-experience">
          Students Counselled: {counsellor.noOfStudentsCounselled}
        </p>

        <div className="stars">
          {Array.from({ length: 5 }, (_, i) => (
            <span
              key={i}
              className={`star ${i < Math.round(counsellor.rating) ? "" : "empty"}`}
            >
              ★
            </span>
          ))}
        </div>

        <p className="counsellor-description">{counsellor.selfDescription}</p>

        <h3 className="slots-title">Available Time Slots</h3>
        <div className="slot-buttons">
          {availableTimings && availableTimings.length > 0 ? (
            availableTimings.map((slot, index) => (
              <button
                key={index}
                className={`slot-button ${
                  selectedSlot === slot.timing ? "selected" : ""
                }`}
                onClick={() => setSelectedSlot(slot.timing)}
              >
                {slot.timing}
              </button>
            ))
          ) : (
            <p className="text-gray-500 text-center w-full">
              No slots available
            </p>
          )}
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

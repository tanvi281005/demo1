import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./CounsellorDetails.css";

const counsellors = {
  1: {
    name: "Dr. Priya Sharma",
    specialization: "Substance Use",
    experience: "10 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/4359/4359931.png",
    slots: ["10:00 AM - 11:00 AM", "11:30 AM - 12:30 PM", "2:00 PM - 3:00 PM"],
    description:
      "Dr. Priya Sharma is a compassionate expert helping individuals overcome substance challenges with a holistic approach.",
    rating: 5,
  },
  2: {
    name: "Mr. Rohan Verma",
    specialization: "Relationships",
    experience: "7 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/5691/5691395.png",
    slots: ["2:00 PM - 3:00 PM", "3:30 PM - 4:30 PM", "5:00 PM - 6:00 PM"],
    description:
      "Mr. Rohan Verma specializes in relationship counselling with a focus on communication and emotional healing.",
    rating: 5,
  },
  3: {
    name: "Ms. Anjali Rao",
    specialization: "Anxiety",
    experience: "6 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/4359/4359970.png",
    slots: ["11:00 AM - 12:00 PM", "1:00 PM - 2:00 PM", "3:00 PM - 4:00 PM"],
    description:
      "Ms. Anjali Rao supports individuals struggling with anxiety through mindfulness and therapeutic strategies.",
    rating: 4,
  },
  4: {
    name: "Dr. Sameer Gupta",
    specialization: "Depression",
    experience: "9 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/4140/4140047.png",
    slots: ["3:00 PM - 5:00 PM", "6:00 PM - 7:00 PM"],
    description:
      "Dr. Sameer Gupta provides empathetic care for those facing depression with evidence-based approaches.",
    rating: 5,
  },
  5: {
    name: "Ms. Kavya Iyer",
    specialization: "Relationships",
    experience: "5 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/4140/4140048.png",
    slots: ["9:00 AM - 10:00 AM", "10:30 AM - 11:30 AM", "12:00 PM - 1:00 PM"],
    description:
      "Ms. Kavya Iyer helps couples and individuals build healthy, fulfilling relationships through therapy.",
    rating: 4,
  },
  6: {
    name: "Dr. Arjun Menon",
    specialization: "Career Guidance",
    experience: "8 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/706/706830.png",
    slots: ["1:00 PM - 2:00 PM", "2:30 PM - 3:30 PM", "4:00 PM - 5:00 PM"],
    description:
      "Dr. Arjun Menon guides young professionals and students toward meaningful and goal-oriented careers.",
    rating: 5,
  },
  7: {
    name: "Ms. Nidhi Sharma",
    specialization: "Anxiety",
    experience: "6 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/3940/3940056.png",
    slots: ["4:00 PM - 5:00 PM", "5:30 PM - 6:30 PM"],
    description:
      "Ms. Nidhi Sharma helps individuals manage anxiety with a focus on lifestyle, therapy, and coping tools.",
    rating: 4,
  },
  8: {
    name: "Dr. Meera Iyer",
    specialization: "Substance Use",
    experience: "11 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/4359/4359906.png",
    slots: ["10:00 AM - 12:00 PM", "1:00 PM - 2:00 PM"],
    description:
      "Dr. Meera Iyer provides professional support for individuals overcoming substance dependency with compassion.",
    rating: 5,
  },
};

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

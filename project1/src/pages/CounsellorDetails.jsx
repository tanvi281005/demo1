import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./CounsellorDetails.css"; // Custom styles for stars, background, etc.

const counsellors = {
  "Priya Sharma": {
    name: "Dr. Priya Sharma",
    specialization: "Substance Use",
    experience: "10 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/4359/4359931.png",
    slots: ["10:00 AM - 11:00 AM", "11:30 AM - 12:30 PM", "2:00 PM - 3:00 PM"],
    description:
      "Dr. Priya Sharma is a compassionate expert helping individuals overcome substance challenges with a holistic approach.",
    rating: 4.8,
  },
  "Rohan Verma": {
    name: "Mr. Rohan Verma",
    specialization: "Relationships",
    experience: "7 years experience",
    img: "https://cdn-icons-png.flaticon.com/512/5691/5691395.png",
    slots: ["2:00 PM - 3:00 PM", "3:30 PM - 4:30 PM", "5:00 PM - 6:00 PM"],
    description:
      "Mr. Rohan Verma specializes in relationship counselling with a focus on communication and emotional healing.",
    rating: 4.5,
  },
  // Add other counsellors similarly...
};

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

const CounsellorDetails = () => {
  const navigate = useNavigate();
  const query = useQuery();
  const name = query.get("name");
  const counsellor = counsellors[name];

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
    <div className="min-h-screen flex items-center justify-center p-6 bg-dot-pattern font-georgia">
      <div className="bg-white rounded-3xl shadow-2xl max-w-xl w-full p-6 mx-4">
        <button
          onClick={() => navigate(-1)}
          className="mb-6 px-5 py-2 bg-[#800000] text-white rounded-full hover:bg-[#660000] transition"
        >
          ← Back
        </button>

        {/* Header */}
        <div className="flex items-center justify-center gap-6 mb-6">
          <img
            src={counsellor.img}
            alt={counsellor.name}
            className="w-20 h-20 rounded-full object-cover border-4 border-[#800000]/30 shadow-md"
          />
          <h1 className="text-3xl font-extrabold text-[#800000] text-center">
            {counsellor.name}
          </h1>
        </div>

        <p className="text-md text-gray-700 mb-2 text-center">
          Specialization: {counsellor.specialization}
        </p>
        <p className="text-[#7b3f00] font-semibold mb-3 text-center">
          {counsellor.experience}
        </p>

        {/* Stars */}
        <div className="flex justify-center mb-4">
          {Array.from({ length: 5 }, (_, i) => (
            <span
              key={i}
              className={`star ${i < Math.round(counsellor.rating) ? "" : "empty"}`}
            >
              ★
            </span>
          ))}
        </div>

        <p className="text-gray-600 text-center mb-5 px-4">
          {counsellor.description}
        </p>

        <div className="flex flex-col items-center gap-5">
          <div className="w-full max-w-xs">
            <h2 className="text-lg font-semibold text-[#800000] mb-2 text-center">
              Available Time Slots
            </h2>
            <div className="flex flex-wrap gap-3 justify-center">
              {counsellor.slots.map((slot, idx) => (
                <button
                  key={idx}
                  className={`px-4 py-2 border border-[#800000] rounded-lg text-[#800000] hover:bg-[#800000] hover:text-white transition ${
                    selectedSlot === slot ? "selected" : ""
                  }`}
                  onClick={() => setSelectedSlot(slot)}
                >
                  {slot}
                </button>
              ))}
            </div>
          </div>

          <button
            onClick={handleBooking}
            className="mt-6 px-6 py-3 bg-[#800000] text-white rounded-full font-semibold hover:bg-[#660000] transition w-full max-w-xs"
          >
            Book Slot
          </button>

          {confirmationMsg && (
            <p className="mt-4 text-green-600 font-semibold text-center">
              {confirmationMsg}
            </p>
          )}
        </div>
      </div>
    </div>
  );
};

export default CounsellorDetails;

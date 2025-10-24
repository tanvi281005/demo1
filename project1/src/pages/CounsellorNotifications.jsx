import React, { useEffect, useState } from "react";
import "./CounsellorNotifications.css";

const CounsellorNotifications = ({ studentId }) => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const res = await fetch("http://localhost:8080/counsellingbook/pending", {
          method: "GET",
          credentials: "include",
        });
        if (res.ok) {
          const data = await res.json();
          setNotifications(data); // array of pending sessions
        }
      } catch (err) {
        console.error("Error fetching notifications:", err);
      }
    };

    fetchNotifications();

    // optional: poll every 10s
    const interval = setInterval(fetchNotifications, 10000);
    return () => clearInterval(interval);
  }, []);

  const handleResponse = async (serviceId, isApproved) => {
    try {
      const res = await fetch("http://localhost:8080/counsellingbook/approve", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ serviceId, isApproved }),
      });
      if (res.ok) {
        alert(`Session ${isApproved ? "approved ✅" : "rejected ❌"}`);
        setNotifications(notifications.filter(n => n.serviceId !== serviceId));
      }
    } catch (err) {
      console.error("Error updating session:", err);
    }
  };

  if (notifications.length === 0) return null;

  return (
    <div className="notif-overlay">
      {notifications.map((n) => (
        <div key={n.serviceId} className="notif-popup">
          <p>
            Student <strong>{n.studentName || n.studentId}</strong> has booked a
            slot at <strong>{n.slot}</strong> ({n.mode})
          </p>
          <div className="notif-actions">
            <button onClick={() => handleResponse(n.serviceId, true)}>Accept</button>
            <button onClick={() => handleResponse(n.serviceId, false)}>Reject</button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default CounsellorNotifications;

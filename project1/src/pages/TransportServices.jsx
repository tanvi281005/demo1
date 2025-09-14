import { useNavigate } from "react-router-dom";

function TransportServices() {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-100 to-blue-300 flex flex-col items-center p-6">
      <h2 className="text-3xl font-bold text-blue-800 mb-8">Choose Your Transport Service</h2>
      <div className="grid gap-6 w-full max-w-md">
        <div
          onClick={() => navigate("/daily-commute")}
          className="bg-white shadow-lg rounded-xl p-6 text-center cursor-pointer hover:scale-105 transition"
        >
          <span className="text-2xl">🚍 Daily Commute</span>
        </div>
        <div
          onClick={() => navigate("/urgent")}
          className="bg-white shadow-lg rounded-xl p-6 text-center cursor-pointer hover:scale-105 transition"
        >
          <span className="text-2xl">🚄 Urgent</span>
        </div>
      </div>
    </div>
  );
}

export default TransportServices;

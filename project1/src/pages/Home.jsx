import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Home() {
  const navigate = useNavigate();

  useEffect(() => {
    const panels = document.querySelectorAll(".panel");

    panels.forEach((panel) => {
      panel.addEventListener("click", () => {
        panels.forEach((p) => p.classList.remove("active"));
        panel.classList.add("active");
      });
    });
  }, []);

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-br from-[#1e1e2f] to-[#2a2a40] text-white">
      <h1 className="mb-6 text-4xl font-bold text-center">Student Utility Hub</h1>

      <div className="accordion flex w-[90%] h-[70vh] overflow-hidden rounded-2xl gap-3">
        {/* Transport */}
        <div className="panel transport flex-1 flex items-center justify-center text-white text-xl font-bold cursor-pointer relative rounded-2xl overflow-hidden transition-all duration-500 ease-in-out">
          <span className="relative z-10">Transport Bookings</span>
          <div className="content absolute bottom-5 left-5 right-5 opacity-0 transition-opacity duration-500 text-sm leading-relaxed z-10">
            <p>Book buses, cabs, or rides easily.</p>
            <button
              onClick={() => navigate("/transport")}
              className="mt-3 px-4 py-2 rounded-lg bg-red-500 text-white font-semibold hover:bg-red-600"
            >
              Enter
            </button>
          </div>
          <div className="absolute inset-0 bg-[url('/images/bus1.jpg')] bg-cover bg-center opacity-80 hover:opacity-100 transition" />
        </div>

        {/* Wellbeing */}
        <div className="panel wellbeing flex-1 flex items-center justify-center text-white text-xl font-bold cursor-pointer relative rounded-2xl overflow-hidden transition-all duration-500 ease-in-out">
          <span className="relative z-10">Mental Wellbeing</span>
          <div className="content absolute bottom-5 left-5 right-5 opacity-0 transition-opacity duration-500 text-sm leading-relaxed z-10">
            <p>Access wellness resources and support.</p>
            <button
              onClick={() => navigate("/wellbeing")}
              className="mt-3 px-4 py-2 rounded-lg bg-red-500 text-white font-semibold hover:bg-red-600"
            >
              Enter
            </button>
          </div>
          <div className="absolute inset-0 bg-[url('/images/wellness1.jpg')] bg-cover bg-center opacity-80 hover:opacity-100 transition" />
        </div>

        {/* Market */}
        <div className="panel market flex-1 flex items-center justify-center text-white text-xl font-bold cursor-pointer relative rounded-2xl overflow-hidden transition-all duration-500 ease-in-out">
          <span className="relative z-10">Buy & Sell</span>
          <div className="content absolute bottom-5 left-5 right-5 opacity-0 transition-opacity duration-500 text-sm leading-relaxed z-10">
            <p>Trade items within campus securely.</p>
            <button
              onClick={() => navigate("/market")}
              className="mt-3 px-4 py-2 rounded-lg bg-red-500 text-white font-semibold hover:bg-red-600"
            >
              Enter
            </button>
          </div>
          <div className="absolute inset-0 bg-[url('/images/buysell1.jpg')] bg-cover bg-center opacity-80 hover:opacity-100 transition" />
        </div>

        {/* Food */}
        <div className="panel food flex-1 flex items-center justify-center text-white text-xl font-bold cursor-pointer relative rounded-2xl overflow-hidden transition-all duration-500 ease-in-out">
          <span className="relative z-10">Food & Water</span>
          <div className="content absolute bottom-5 left-5 right-5 opacity-0 transition-opacity duration-500 text-sm leading-relaxed z-10">
            <p>Order food and manage water bookings.</p>
            <button
              onClick={() => navigate("/food")}
              className="mt-3 px-4 py-2 rounded-lg bg-red-500 text-white font-semibold hover:bg-red-600"
            >
              Enter
            </button>
          </div>
          <div className="absolute inset-0 bg-[url('/images/food1.jpg')] bg-cover bg-center opacity-80 hover:opacity-100 transition" />
        </div>

        {/* Academic */}
        <div className="panel academic flex-1 flex items-center justify-center text-white text-xl font-bold cursor-pointer relative rounded-2xl overflow-hidden transition-all duration-500 ease-in-out">
          <span className="relative z-10">Academic Resources</span>
          <div className="content absolute bottom-5 left-5 right-5 opacity-0 transition-opacity duration-500 text-sm leading-relaxed z-10">
            <p>Notes, assignments, and study material.</p>
            <button
              onClick={() => navigate("/academic")}
              className="mt-3 px-4 py-2 rounded-lg bg-red-500 text-white font-semibold hover:bg-red-600"
            >
              Enter
            </button>
          </div>
          <div className="absolute inset-0 bg-[url('/images/academic1.jpg')] bg-cover bg-center opacity-80 hover:opacity-100 transition" />
        </div>
      </div>
    </div>
  );
}

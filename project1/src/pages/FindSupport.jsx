import React from 'react';
import { Link } from 'react-router-dom';

// Example counsellors data — replace with your real data or fetch from API
const counsellors = [
  { id: 1, name: "Dr. Priya Sharma", specialization: "Substance Use" },
  { id: 2, name: "Mr. Rohan Verma", specialization: "Relationships" },
  { id: 3, name: "Ms. Anjali Rao", specialization: "Anxiety" },
  { id: 4, name: "Dr. Sameer Gupta", specialization: "Depression" },
  { id: 5, name: "Ms. Kavya Iyer", specialization: "Relationships" },
  { id: 6, name: "Dr. Arjun Menon", specialization: "Career Guidance" },
  { id: 7, name: "Ms. Nidhi Sharma", specialization: "Anxiety" },
  { id: 8, name: "Dr. Meera Iyer", specialization: "Substance Use" }
];

const FindSupport = () => {
  return (
    <div className="max-w-4xl mx-auto p-6">
      <h1 className="text-3xl font-bold mb-6 text-center">Find Support</h1>
      <ul className="space-y-4">
        {counsellors.map(c => (
          <li key={c.id} className="border border-gray-300 rounded p-4 hover:shadow-lg transition">
            <Link to={`/counsellor/${c.id}`} className="text-xl font-semibold text-[#800000] hover:underline">
              {c.name}
            </Link>
            <p className="text-gray-600">Specialization: {c.specialization}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FindSupport;

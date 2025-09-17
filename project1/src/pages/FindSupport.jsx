import React, { useState } from 'react';
import './FindSupport.css';

// Data for the counsellors - in a real app, this would come from an API
const counsellorsData = [
  { id: 1, name: 'Dr. Priya Sharma', slot: '10:00 AM - 12:00 PM', specialization: 'Substance Use', imageUrl: 'https://cdn-icons-png.flaticon.com/512/4359/4359931.png' },
  { id: 2, name: 'Mr. Rohan Verma', slot: '2:00 PM - 4:00 PM', specialization: 'Relationships', imageUrl: 'https://cdn-icons-png.flaticon.com/512/5691/5691395.png' },
  { id: 3, name: 'Ms. Anjali Rao', slot: '11:00 AM - 1:00 PM', specialization: 'Anxiety', imageUrl: 'https://cdn-icons-png.flaticon.com/512/5705/5705405.png' },
  { id: 4, name: 'Dr. Sameer Gupta', slot: '3:00 PM - 5:00 PM', specialization: 'Depression', imageUrl: 'https://cdn-icons-png.flaticon.com/512/2548/2548613.png' },
  { id: 5, name: 'Ms. Kavya Iyer', slot: '9:00 AM - 11:00 AM', specialization: 'Relationships', imageUrl: 'https://cdn-icons-png.flaticon.com/512/921/921089.png' },
  { id: 6, name: 'Dr. Arjun Menon', slot: '1:00 PM - 3:00 PM', specialization: 'Career', imageUrl: 'https://cdn-icons-png.flaticon.com/512/4359/4359947.png' },
  { id: 7, name: 'Ms. Nidhi Sharma', slot: '4:00 PM - 6:00 PM', specialization: 'Anxiety', imageUrl: 'https://cdn-icons-png.flaticon.com/512/3940/3940403.png' },
  { id: 8, name: 'Dr. Meera Iyer', slot: '10:00 AM - 12:00 PM', specialization: 'Substance Use', imageUrl: 'https://cdn-icons-png.flaticon.com/512/3940/3940414.png' }
];

const filterCategories = ['All', 'Substance Use', 'Relationships', 'Anxiety', 'Career', 'Depression'];

const FindSupport = () => {
  const [activeFilter, setActiveFilter] = useState('All');

  const handleFilterClick = (category) => {
    setActiveFilter(category);
  };

  const filteredCounsellors = activeFilter === 'All'
    ? counsellorsData
    : counsellorsData.filter(c => c.specialization === activeFilter || (c.specialization === 'Career Guidance' && activeFilter === 'Career'));

  return (
    <div className="support-page-body">
      {/* Header */}
      <h1 className="support-header">🌿 Find the Right Support for You</h1>

      {/* Filter Buttons */}
      <div className="filter-container">
        {filterCategories.map(category => (
          <button
            key={category}
            className={`filter-btn ${activeFilter === category ? 'active' : ''}`}
            onClick={() => handleFilterClick(category)}
          >
            {category}
          </button>
        ))}
      </div>

      {/* Grid of Cards */}
      <div className="counsellor-grid">
        {filteredCounsellors.map(counsellor => (
          <div key={counsellor.id} className="counsellor-card-link">
            <div className="counsellor-card">
              <img src={counsellor.imageUrl} className="counsellor-avatar" alt={counsellor.name} />
              <div className="counsellor-info">
                <h2 className="counsellor-name">{counsellor.name}</h2>
                <p><strong>Slot:</strong> {counsellor.slot}</p>
                <p><strong>Specialization:</strong> {counsellor.specialization}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FindSupport;
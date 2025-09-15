import React, { useState } from 'react';
import './Vehicles.css';

const vehiclesData = [
    {
        name: "Mountain Bicycle",
        description: "Durable mountain bike suitable for off-road trails.",
        image: "https://i.pinimg.com/1200x/06/8e/f6/068ef62e4976683f1e22bbc6b6e060d4.jpg",
        condition: "New",
        price: 350,
        dateAdded: "2025-09-10"
    },
    {
        name: "Road Bicycle",
        description: "Lightweight road bike for city commuting and fitness.",
        image: "https://i.pinimg.com/736x/47/77/52/477752ba3bc5600f9cd9a761e04cd47c.jpg",
        condition: "New",
        price: 400,
        dateAdded: "2025-09-12"
    },
    {
        name: "Yamaha Scooter",
        description: "Efficient and stylish scooter for city travel.",
        image: "https://i.pinimg.com/736x/ac/e7/65/ace765f21b4c75a77ab21bb8d750abe1.jpg",
        condition: "New",
        price: 1200,
        dateAdded: "2025-09-08"
    },
    {
        name: "Honda Motorcycle",
        description: "Reliable motorcycle with excellent fuel efficiency.",
        image: "https://i.pinimg.com/1200x/24/09/40/240940417bef1a5648b8ec3138259c5c.jpg",
        condition: "New",
        price: 2500,
        dateAdded: "2025-09-14"
    }
];

const Vehicles = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [sortOption, setSortOption] = useState('');

    const handleBuy = (name) => {
        alert(`Request to buy "${name}" sent!`);
    };

    const filteredVehicles = vehiclesData
        .filter(vehicle => vehicle.name.toLowerCase().includes(searchTerm.toLowerCase()))
        .sort((a, b) => {
            if (sortOption === "price-asc") return a.price - b.price;
            if (sortOption === "price-desc") return b.price - a.price;
            if (sortOption === "date-newest") return new Date(b.dateAdded) - new Date(a.dateAdded);
            if (sortOption === "date-oldest") return new Date(a.dateAdded) - new Date(b.dateAdded);
            return 0;
        });

    return (
        <div 
            className="container_vehicles"
            style={{ 
                background: "url('https://i.pinimg.com/736x/11/87/02/11870269b885fddb29b190aa36ffe6d3.jpg') no-repeat center center fixed",
                backgroundSize: 'cover',
                minHeight: '100vh',
                color: '#333',
                fontFamily: "'Montserrat', Arial, sans-serif"
            }}
        >
            <header className="header_vehicles">
                <h1>Vehicles</h1>
                <div className="controls_vehicles">
                    <input 
                        type="text" 
                        placeholder="Search vehicles..." 
                        value={searchTerm} 
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                    <select value={sortOption} onChange={(e) => setSortOption(e.target.value)}>
                        <option value="">Sort by</option>
                        <option value="price-asc">Price: Low to High</option>
                        <option value="price-desc">Price: High to Low</option>
                        <option value="date-newest">Date: Newest First</option>
                        <option value="date-oldest">Date: Oldest First</option>
                    </select>
                </div>
            </header>

            <div className="products_grid">
                {filteredVehicles.map(vehicle => (
                    <div className="product_card" key={vehicle.name}>
                        <img src={vehicle.image} alt={vehicle.name} />
                        <div className="product_info">
                            <h3>{vehicle.name}</h3>
                            <p>{vehicle.description}</p>
                            <div className="product_meta">
                                Condition: {vehicle.condition} <br />
                                Price: ${vehicle.price} <br />
                                Added: {vehicle.dateAdded}
                            </div>
                            <button className="request_buy" onClick={() => handleBuy(vehicle.name)}>
                                Request Buy
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Vehicles;

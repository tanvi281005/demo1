import React, { useState } from 'react';
import './Miscellaneous.css';

const productsData = [
    {
        name: "Cloth Stand",
        description: "Sturdy cloth stand for organizing your clothes at home.",
        image: "https://i.pinimg.com/736x/b3/e3/9a/b3e39a9f9b642a96206bc21925795a0d.jpg",
        condition: "New",
        price: 40,
        dateAdded: "2025-09-10"
    },
    {
        name: "Room Decor Set",
        description: "Beautiful room decor items to enhance your living space.",
        image: "https://i.pinimg.com/736x/e0/11/77/e011777f8c267d1d362bfd0d62ac5369.jpg",
        condition: "New",
        price: 60,
        dateAdded: "2025-09-12"
    },
    {
        name: "Curtains",
        description: "Elegant curtains to brighten up any room.",
        image: "https://i.pinimg.com/736x/73/22/7a/73227a0afd7cdd298641aeabc8cccd47.jpg",
        condition: "New",
        price: 35,
        dateAdded: "2025-09-08"
    },
    {
        name: "Leather Bag",
        description: "Durable leather bag suitable for work and travel.",
        image: "https://i.pinimg.com/736x/cd/a4/a7/cda4a787a9d7ea405b1ef07929f2d954.jpg",
        condition: "New",
        price: 80,
        dateAdded: "2025-09-14"
    },
    {
        name: "Canvas Backpack",
        description: "Stylish and spacious canvas backpack for daily use.",
        image: "https://i.pinimg.com/1200x/d3/5b/aa/d35baafdf8def2880a0b9a98c7a25ab0.jpg",
        condition: "New",
        price: 45,
        dateAdded: "2025-09-11"
    }
];

const Miscellaneous = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [sortOption, setSortOption] = useState('');

    const handleBuy = (name) => {
        alert(`Request to buy "${name}" sent!`);
    };

    const filteredProducts = productsData
        .filter(product => product.name.toLowerCase().includes(searchTerm.toLowerCase()))
        .sort((a, b) => {
            if (sortOption === "price-asc") return a.price - b.price;
            if (sortOption === "price-desc") return b.price - a.price;
            if (sortOption === "date-newest") return new Date(b.dateAdded) - new Date(a.dateAdded);
            if (sortOption === "date-oldest") return new Date(a.dateAdded) - new Date(b.dateAdded);
            return 0;
        });

    return (
        <div
            className="container_misc"
            style={{
                background: "url('https://i.pinimg.com/736x/e1/a0/76/e1a076e18bcc62751c9bc4ec0cb81fe8.jpg') no-repeat center center fixed",
                backgroundSize: 'cover',
                minHeight: '100vh',
                color: '#333',
                fontFamily: "'Montserrat', Arial, sans-serif"
            }}
        >
            <header className="header_misc">
                <h1>Miscellaneous</h1>
                <div className="controls_misc">
                    <input
                        type="text"
                        placeholder="Search products..."
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
                {filteredProducts.map(product => (
                    <div className="product_card" key={product.name}>
                        <img src={product.image} alt={product.name} />
                        <div className="product_info">
                            <h3>{product.name}</h3>
                            <p>{product.description}</p>
                            <div className="product_meta">
                                Condition: {product.condition} <br />
                                Price: ${product.price} <br />
                                Added: {product.dateAdded}
                            </div>
                            <button className="request_buy" onClick={() => handleBuy(product.name)}>
                                Request Buy
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Miscellaneous;

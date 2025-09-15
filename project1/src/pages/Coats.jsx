import React, { useState } from 'react';
import './Coats.css';

const productsData = [
    {
        name: "Red Tie",
        description: "Elegant red tie for formal occasions.",
        image: "https://i.pinimg.com/1200x/4e/60/94/4e60944aab9a296c7cb8f95fbcdb4655.jpg",
        condition: "New",
        price: 15,
        dateAdded: "2025-09-10"
    },
    {
        name: "Black Formal Set",
        description: "Classic black formal suit set for business events.",
        image: "https://i.pinimg.com/1200x/dc/fa/7d/dcfa7d9a42eaff5ee17693d02ff213af.jpg",
        condition: "New",
        price: 120,
        dateAdded: "2025-09-12"
    },
    {
        name: "White Shirt",
        description: "Crisp white shirt suitable for office and formal events.",
        image: "https://i.pinimg.com/736x/12/c1/1c/12c11c3fbe9ea08a0b26089ffaf37f56.jpg",
        condition: "New",
        price: 30,
        dateAdded: "2025-09-08"
    },
    {
        name: "Manufacturing Lab Coat",
        description: "Protective lab coat for manufacturing and lab work.",
        image: "https://i.pinimg.com/736x/c5/17/a2/c517a2609e2807a31410004e619acc64.jpg",
        condition: "New",
        price: 45,
        dateAdded: "2025-09-14"
    },
    {
        name: "Chemistry Lab Coat",
        description: "Durable chemistry lab coat with long sleeves.",
        image: "https://i.pinimg.com/1200x/16/3e/d1/163ed1279052979e5368305a51ceceb4.jpg",
        condition: "New",
        price: 50,
        dateAdded: "2025-09-11"
    }
];

const CoatsFormals = () => {
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
            className="container_coats"
            style={{
                background: "url('https://i.pinimg.com/1200x/d7/86/98/d7869880338da7c29e9716033c651af0.jpg') no-repeat center center fixed",
                backgroundSize: 'cover',
                minHeight: '100vh',
                color: '#333',
                fontFamily: "'Montserrat', Arial, sans-serif"
            }}
        >
            <header className="header_coats">
                <h1>Coats & Formals</h1>
                <div className="controls_coats">
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

export default CoatsFormals;

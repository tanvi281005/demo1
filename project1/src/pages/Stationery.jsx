import React, { useState } from 'react';
import './Stationery.css';

const productsData = [
    {
        name: "Scientific Calculator",
        description: "High-precision scientific calculator suitable for students.",
        image: "https://i.pinimg.com/736x/26/ee/b7/26eeb7a5356d4b9174c9a23b9055d4f8.jpg",
        condition: "New",
        price: 30,
        dateAdded: "2025-09-10"
    },
    {
        name: "Engineering Drawing Set",
        description: "Complete set of rulers, compasses, and protractors for engineering drawings.",
        image: "https://i.pinimg.com/1200x/d1/05/77/d10577d5eea40bead4280f95fd927543.jpg",
        condition: "New",
        price: 45,
        dateAdded: "2025-09-08"
    },
    {
        name: "A4 Paper Set (500 Sheets)",
        description: "High-quality A4 paper pack suitable for printing and notes.",
        image: "https://i.pinimg.com/1200x/72/25/53/72255393d6903c2eb5bdfd6a9e9e8895.jpg",
        condition: "New",
        price: 20,
        dateAdded: "2025-09-12"
    },
    {
        name: "Book: 'Mathematics Essentials' by John Doe",
        description: "Comprehensive mathematics textbook covering algebra and calculus.",
        image: "https://i.pinimg.com/1200x/ba/06/b8/ba06b8a9948a589e0ae4ba8a58e306b4.jpg",
        condition: "New",
        price: 50,
        dateAdded: "2025-09-14"
    },
    {
        name: "Book: 'Physics Fundamentals' by Jane Smith",
        description: "Introductory physics textbook with practical examples.",
        image: "https://i.pinimg.com/736x/b4/a1/84/b4a1840c9a2f3961b5d721e0dea0010d.jpg",
        condition: "New",
        price: 55,
        dateAdded: "2025-09-11"
    }
];

const Products = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [sortOption, setSortOption] = useState('');

    const handleBuy = (name) => {
        alert(`Request to buy "${name}" sent!`);
    };

    const filteredProducts = productsData
        .filter(product => product.name.toLowerCase().includes(searchTerm.toLowerCase()))
        .sort((a, b) => {
            if(sortOption === "price-asc") return a.price - b.price;
            if(sortOption === "price-desc") return b.price - a.price;
            if(sortOption === "date-newest") return new Date(b.dateAdded) - new Date(a.dateAdded);
            if(sortOption === "date-oldest") return new Date(a.dateAdded) - new Date(b.dateAdded);
            return 0;
        });

    return (
        <div 
            className="container_products" 
            style={{ 
                background: "url('https://i.pinimg.com/1200x/a7/2a/b8/a72ab8093e5d210f44a37a448407e38b.jpg') no-repeat center center fixed",
                backgroundSize: 'cover',
                minHeight: '100vh',
                color: '#333',
                fontFamily: "'Montserrat', Arial, sans-serif",
            }}
        >
            <header className="header_products">
                <h1>Stationery & Books</h1>
                <div className="controls_products">
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

export default Products;

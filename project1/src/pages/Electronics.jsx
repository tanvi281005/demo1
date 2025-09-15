import React, { useState } from 'react';
import './Electronics.css';

const initialProducts = [
    {
        name: "Laptop XYZ",
        description: "High-performance laptop suitable for work and gaming.",
        image: "https://i.pinimg.com/736x/1d/9e/34/1d9e347684c6be3f86b7058fecb6b47e.jpg",
        condition: "New",
        price: 1200,
        dateAdded: "2025-09-10"
    },
    {
        name: "Smartphone ABC",
        description: "Latest model smartphone with high-resolution camera.",
        image: "https://i.pinimg.com/736x/67/a9/7d/67a97d7dfa6a9f02bfd992a4afa03178.jpg",
        condition: "New",
        price: 800,
        dateAdded: "2025-09-12"
    },
    {
        name: "Wireless Headphones",
        description: "Noise-canceling over-ear headphones.",
        image: "https://i.pinimg.com/1200x/aa/a8/bb/aaa8bb3103cf6841772f0460dca4c187.jpg",
        condition: "Used",
        price: 150,
        dateAdded: "2025-09-08"
    },
    {
        name: "Smartwatch 123",
        description: "Waterproof smartwatch with health tracking features.",
        image: "https://i.pinimg.com/1200x/5b/c5/9f/5bc59f3488e344d2e272dd00181cda0f.jpg",
        condition: "New",
        price: 250,
        dateAdded: "2025-09-14"
    }
];

function ElectronicDevices() {
    const [products, setProducts] = useState(initialProducts);
    const [searchText, setSearchText] = useState('');
    const [sortOption, setSortOption] = useState('');

    const filterAndSortProducts = () => {
        let filtered = initialProducts.filter(p =>
            p.name.toLowerCase().includes(searchText.toLowerCase())
        );

        if(sortOption === "price-asc") {
            filtered.sort((a, b) => a.price - b.price);
        }
        if(sortOption === "price-desc") {
            filtered.sort((a, b) => b.price - a.price);
        }
        if(sortOption === "date-newest") {
            filtered.sort((a, b) => new Date(b.dateAdded) - new Date(a.dateAdded));
        }
        if(sortOption === "date-oldest") {
            filtered.sort((a, b) => new Date(a.dateAdded) - new Date(b.dateAdded));
        }

        return filtered;
    };

    const handleBuy = (name) => {
        alert(`Request to buy "${name}" sent!`);
    };

    const filteredProducts = filterAndSortProducts();

    return (
        <div className="electronics-container">
            <header>
                <h1>Electronic Devices</h1>
                <div className="controls">
                    <input 
                        type="text" 
                        placeholder="Search products..." 
                        value={searchText}
                        onChange={(e) => setSearchText(e.target.value)}
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
            <div className="products">
                {filteredProducts.map((product, index) => (
                    <div key={index} className="product-card">
                        <img src={product.image} alt={product.name} />
                        <div className="product-info">
                            <h3>{product.name}</h3>
                            <p>{product.description}</p>
                            <div className="product-meta">
                                Condition: {product.condition} <br />
                                Price: ${product.price} <br />
                                Added: {product.dateAdded}
                            </div>
                            <button className="request-buy" onClick={() => handleBuy(product.name)}>Request Buy</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ElectronicDevices;

import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './ProductDetail.css';

function ProductDetail() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [negotiatedPrice, setNegotiatedPrice] = useState('');

    useEffect(() => {
        fetch(`http://localhost:8080/market-items/${id}`)
            .then(res => res.json())
            .then(data => setProduct(data))
            .catch(err => console.error("Error fetching product:", err));
    }, [id]);

    const handleBuyRequest = () => {
        alert(`Request to buy ${product.title} with negotiated price ${negotiatedPrice}`);
        // later you’ll POST to /transactions/request
    };

    if (!product) return <div>Loading...</div>;

    return (
        <div className="product-detail-container">
            <img src={product.photo} alt={product.title} className="product-detail-img" />
            <div className="product-detail-info">
                <h1>{product.title}</h1>
                <p>{product.description}</p>
                <p><strong>Condition:</strong> {product.itemCondition}</p>
                <p><strong>Original Price:</strong> ${product.price}</p>
                <input 
                    type="number" 
                    placeholder="Enter your price" 
                    value={negotiatedPrice}
                    onChange={(e) => setNegotiatedPrice(e.target.value)} 
                />
                <button onClick={handleBuyRequest}>Request Buy</button>
            </div>
        </div>
    );
}

export default ProductDetail;

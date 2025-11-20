import {useEffect, useState} from 'react';
import ProductList from '../../components/ProductList';

export default function ProductPage() {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  const loadProducts = async () => {
    try {
        const response = await fetch("http://localhost:8080/product");
        if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
        
        const result = await response.json();
    
        if(result.length > 1) result.sort((a, b) => a.id - b.id)
        setProducts(result);
    } catch (err) {
        setError(err.message);
    }
  };

  useEffect(() => {
    loadProducts();
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-6">Our Product Catalog</h1>

      {error && (
        <p className="text-red-600 mb-4">Error: {error}</p>
      )}

      <ProductList products={products} loadProducts={loadProducts}/>
    </div>
  );
}

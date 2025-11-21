import {useEffect, useState} from 'react';
import ProductList from '../../components/ProductList';
import toast, { Toaster } from 'react-hot-toast';
import "./ProductPage.css"

export default function ProductPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadProducts = async () => {
    try {
      const response = await fetch("http://localhost:8080/product");
      if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
      
      const result = await response.json();
    
      if(result.length > 1) result.sort((a, b) => a.id - b.id)
      setProducts(result);
    } catch (err) {
      toast.error(`Error: ${err.message}`)
    }finally{
      setLoading(false)
    }
  };

  useEffect(() => {
    loadProducts();
  }, []);

  if(loading) return <div className="text-center text-xl font-semibold loading-dots">Loading<span>.</span><span>.</span><span>.</span></div>

  return (
    <div className="p-8">
      <div><Toaster /></div>
      <h1 className="text-3xl font-bold mb-6">Our Product Catalog</h1>

      <ProductList products={products} loadProducts={loadProducts}/>
    </div>
  );
}

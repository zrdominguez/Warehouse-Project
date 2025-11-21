import "./ProductList.css"

import ProductCard from "../ProductCard";
import { useState } from "react";

export default function ProductList({products, loadProducts}) {
  const [error, setError] = useState(null);
  
  if (!products || products.length === 0) {
    return <p className="text-gray-500">No products available.</p>;
  }

  return (
    <div className="grid grid-cols-3 gap-6">
      <button
      className="h-36 flex flex-col items-center justify-center border-2 border-gray-400 rounded-xl p-6 hover:bg-orange-300 transition cursor-pointer"
      onClick={()=> setOpenModal(true)}
      >
        <span className="text-4xl font-bold">+</span>
        <span className="mt-2">Create Product</span>
      </button>
      {products.map(product => (
        <ProductCard key={product.id} product={product} loadProducts={loadProducts}/>
      ))}
    </div>
  );
}

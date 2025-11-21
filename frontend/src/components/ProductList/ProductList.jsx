import "./ProductList.css"

import ProductCard from "../ProductCard";
import { useState } from "react";
import CreateProductModal from "../../modals/CreateProductModal/CreateProductModal";
import toast from "react-hot-toast";

export default function ProductList({products, loadProducts}) {
  const [openModal, setOpenModal] = useState(false);
  
  const handleSubmit = async product =>{
    try{
      const response = await fetch(
        "http://localhost:8080/product",
        {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(product)
        }
      );
      if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
      loadProducts();
    }catch(error){
      toast.error(`Error: ${err.message}`)
    }finally{
      setOpenModal(false);
    }
  }
  
  if (!products || products.length === 0) {
    return <p className="text-gray-500">No products available.</p>;
  }

  // --- GROUP PRODUCTS ---
  const instrumentProducts = products.filter((p) =>
    ["STRING", "BRASS", "WOODWIND", "PERCUSSION"].includes(p.productType)
  );

  const bookProducts = products.filter((p) =>
    ["DRAMA", "ROMANCE", "NONFICTION", "HORROR", "MYSTERY"].includes(p.productType)
  );

  const electronicProducts = products.filter((p) =>
    ["LAPTOPS", "SMARTPHONES", "GAME_CONSOLES"].includes(p.productType)
  );

  return (
    <div>
      <CreateProductModal
      open={openModal}
      onClose={() => setOpenModal(false)}
      onSubmit={handleSubmit}
      />

      <button
      className="h-36 flex flex-col items-center justify-center border-2 border-gray-400 rounded-xl p-6 hover:bg-orange-300 transition cursor-pointer"
      onClick={()=> setOpenModal(true)}
      >
        <span className="text-4xl font-bold">+</span>
        <span className="mt-2">Create Product</span>
      </button>

      {/* ------------------- INSTRUMENTS ------------------- */}
      <h2 className="text-2xl font-bold mb-4">Instruments</h2>
      <div className="grid grid-cols-3 gap-6 mb-10">
        {instrumentProducts.length === 0 && (
          <p className="text-gray-400 col-span-3">No instrument products.</p>
        )}
        {instrumentProducts.map((product) => (
          <ProductCard key={product.id} product={product} loadProducts={loadProducts} />
        ))}
      </div>

      {/* ------------------- BOOKS ------------------- */}
      <h2 className="text-2xl font-bold mb-4">Books</h2>
      <div className="grid grid-cols-3 gap-6 mb-10">
        {bookProducts.length === 0 && (
          <p className="text-gray-400 col-span-3">No book products.</p>
        )}
        {bookProducts.map((product) => (
          <ProductCard key={product.id} product={product} loadProducts={loadProducts} />
        ))}
      </div>

      {/* ------------------- ELECTRONICS ------------------- */}
      <h2 className="text-2xl font-bold mb-4">Electronics</h2>
      <div className="grid grid-cols-3 gap-6">
        {electronicProducts.length === 0 && (
          <p className="text-gray-400 col-span-3">No electronic products.</p>
        )}
        {electronicProducts.map((product) => (
          <ProductCard key={product.id} product={product} loadProducts={loadProducts} />
        ))}
      </div>
    </div>
  );
}

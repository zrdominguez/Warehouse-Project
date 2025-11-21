import { useState } from "react";
import "./ProductCard.css"
import toast, { Toaster } from 'react-hot-toast';
import ConfirmDeleteToast from "../../custom-toasts/ConfirmDeleteToast/ConfirmDeleteToast";
import EditProductModal from "../../modals/EditProductModal";

export default function ProductCard({product, loadProducts}) {

  const [openModal, setOpenModal] = useState(false)
  
  
  const handleDelete = onConfirm => {
    toast.custom(t => (
      <ConfirmDeleteToast t={t} onConfirm={onConfirm} />
    ))
  };

  const onConfirm = async productId => {
    try {
      const response = await fetch(
        `http://localhost:8080/product/${productId}`,
        { method: "DELETE" }
      );
      if (!response.ok) throw new Error("Failed to delete");
      loadProducts(); 
    } catch (error) {
      toast.error(`Error: ${error.message}`)
    }
  }

  const handleOnSubmit = async (product, productId) => {
    try{
      const response = await fetch(
        `http://localhost:8080/product/${productId}`,
        {
          method: 'PUT',
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

  return (
      <div
        className="h-36 border rounded-lg p-4 shadow-sm bg-white relative hover:shadow-md transition"
      > 
        <EditProductModal
        open={openModal}
        onClose={() => setOpenModal(false)}
        onSubmit={handleOnSubmit}
        product={product}
        />
        <div><Toaster /></div>
        <button
          onClick={() => handleDelete(()=> onConfirm(product.id))}
          className="absolute top-3 right-3 text-gray-500 hover:text-gray-700 hover:scale-105 transition"
        >
          ✕
        </button>
        <h3 className="text-lg font-medium text-neutral-950">{product.name}</h3>
        <p className="text-gray-600 text-sm">SKU: {product.sku}</p>
        <p className="text-sm text-gray-700 mt-2">
          Type: {product?.productType}
        </p>
        <button
        className="text-white mt-2 w-15  border-2 rounded-xl bg-gray-400 hover:bg-gray-600 transition"
          onClick={() => setOpenModal(true)}
        >
          Edit
        </button>
      </div>
  );
}

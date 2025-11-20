import { useState } from "react";
import Modal from "../Modal";

export default function AddProductModal({ open, onClose, onSubmit, products}) {
  const [quantity, setQuantity] = useState(1);
  const [productId, setProductId] = useState("");
 

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ quantity, productId });
    onClose();
  };

  return (
    <Modal open={open} onClose={onClose} title="Create Product">
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block mb-1 font-medium">Product</label>
          <select
            className="w-full border rounded-md px-3 py-2"
            value={productId}
            onChange={e => setProductId(e.target.value)}
            required
          >
            <option value="" disabled>Select a product</option>
            {products.map(product => (
              <option
              className="bg-gray-400" 
              key={product.id} 
              value={product.id}>
                {product.name}
              </option>
            ))}
          </select>
        </div>
        
        <div>
          <label className="block mb-1 font-medium">Quantity</label>
          <input
            type="number"
            min="1"
            className="w-full border rounded-md px-3 py-2"
            value={quantity}
            onChange={e => setQuantity(e.target.value)}
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Create
        </button>
      </form>
    </Modal>
  );
}

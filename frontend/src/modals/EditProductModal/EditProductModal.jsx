
// const PRODUCTTYPES = [
//     "OTHER",
//     "STRING",
//     "WOODWIND",
//     "BRASS",
//     "PERCUSSION"];

import { useState } from "react";
import Modal from "../Modal";

export default function EditProductModal({open, onClose, onSubmit, product}) {
    const [name, setName] = useState(product.name);
    const [description, setDescription] = useState(product.description);
    const [sku, setSku] = useState(product.sku);

    const handleSubmit = e => {
        e.preventDefault();
        onSubmit({name, description, sku}, product.id);
        onClose();
    }

    return (
    <Modal open={open} onClose={onClose} title="Edit Product">
        <form onSubmit={handleSubmit} className="space-y-4">
            <div>
                <label className="block mb-1 font-medium">Name</label>
                <input
                placeholder={name}
                className="w-full border rounded-md px-3 py-2"
                value={name}
                onChange={e => setName(e.target.value)}
                />
            </div>
            <div>
               <label className="block mb-1 font-medium">SKU</label>
                <input
                type="text"
                placeholder={sku}
                className="w-full border rounded-md px-3 py-2"
                value={sku}
                onChange={e => setSku(e.target.value.toUpperCase().trim())}
                title="SKU must match the format PDR-###### (letters and numbers only)"
                pattern="^PRD-[A-Z0-9]{6}$"
                maxLength={10}
                required
                /> 
            </div>
            <div>
                <label className="block mb-1 font-medium">Description</label>
                <textarea
                placeholder={description}
                className="w-full border rounded-md px-3 py-2"
                value={description}
                rows={5}
                onChange={e => setDescription(e.target.value)}
                required
                />
            </div>

            <button
            type="submit"
            className="w-full bg-gray-600 text-white py-2 rounded-md hover:bg-gray-700"
            >
                Confirm
            </button>
        </form>
    </Modal>)
}

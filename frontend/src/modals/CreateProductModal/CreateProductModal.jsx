import { use, useState } from "react";
import Modal from "../Modal";

const PRODUCTTYPES = [
    "STRING",
    "WOODWIND",
    "BRASS",
    "PERCUSSION",
    "DRAMA",
    "ROMANCE",
    "NONFICTION",
    "HORROR",
    "MYSTERY",
    "LAPTOPS",
    "SMARTPHONES",
    "GAME_CONSOLES",
];

export default function CreateProductModal({open, onClose, onSubmit}) {
    const [name, setName] = useState("");
    const [productType, setProductType] = useState("");
    const [description, setDescription] = useState("")
    
    const handleSubmit = e => {
        e.preventDefault();
        onSubmit({name, productType, description});
        onClose();
    }

    return(
    <Modal open={open} onClose={onClose} title="Create New Product">
        <form onSubmit={handleSubmit} className="space-y-4">
            <div>
                <label className="block mb-1 font-medium">Name</label>
                <input
                placeholder="Name of Product"
                className="w-full border rounded-md px-3 py-2"
                value={name}
                onChange={e => setName(e.target.value)}
                required
                />
            </div>

            <div>
                <label className="block mb-1 font-medium">Type</label>
                <select
                    className="w-full border rounded-md px-3 py-2"
                    value={productType}
                    onChange={e => setProductType(e.target.value)}
                    required
                >
                    <option value="" disabled>Select a Type</option>
                    {PRODUCTTYPES.map((type, index) => (
                        <option
                        className="bg-gray-400" 
                        key={index}
                        value={type}>
                            {type}
                        </option>
                    ))}
                </select>
            </div>
            
            <div>
                <label className="block mb-1 font-medium">Description</label>
                <textarea
                placeholder={"Please describe the new product"}
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
                Create
            </button>
        </form>
    </Modal>
    )
}

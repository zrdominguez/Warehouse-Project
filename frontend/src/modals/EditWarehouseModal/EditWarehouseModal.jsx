import { useState } from "react";
import Modal from "../Modal";

const WAREHOUSETYPES = ["INSTRUMENTS"];

export default function EditWarehouseModal({open, onClose, onSubmit, warehouse}) {
    
    const [name, setName] = useState(warehouse.name);
    const [warehouseType, setWarehouseType] = useState(warehouse.warehouseType);
    const [capacity, setCapacity] = useState(warehouse.capacity);
    const [location, setLocation] = useState(warehouse.location);

    const handleSubmit = e => {
        e.preventDefault({userId : 1, name, warehouseType, capacity, location});
        console.log()
        onSubmit({userId : 1, name, warehouseType, capacity, location}, warehouse.id);
        onClose();
    }

    return (
    <Modal open={open} onClose={onClose} title="Edit Warehouse">
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
                <label className="block mb-1 font-medium">Type</label>
                <select
                    className="w-full border rounded-md px-3 py-2"
                    value={warehouseType}
                    onChange={e => setWarehouseType(e.target.value)}
                    required
                >
                    <option value="" disabled>Select a Type</option>
                    {WAREHOUSETYPES.map((type, index) => (
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
               <label className="block mb-1 font-medium">Capacity</label>
                <input
                type="number"
                placeholder="0"
                min="0"
                className="w-full border rounded-md px-3 py-2"
                value={capacity}
                onChange={e => setCapacity(e.target.value)}
                required
                /> 
            </div>
            
            <div>
                <label className="block mb-1 font-medium">Location</label>
                <input
                placeholder="City, State"
                className="w-full border rounded-md px-3 py-2"
                value={location}
                onChange={e => setLocation(e.target.value)}
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

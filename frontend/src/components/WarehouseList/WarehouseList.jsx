import CreateWarehouseModal from "../../modals/CreateWarehouseModal";
import WarehouseCard from "../WarehouseCard";
import "./WarehouseList.css";
import { useState } from "react";
import toast from "react-hot-toast";

export default function WarehouseList({warehouses, loadWarehouses}) {
    
    const [openModal, setOpenModal] = useState(false);
    
    const handleSubmit = async warehouse => {
        try{
            const response = await fetch(
                "http://localhost:8080/warehouse",
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(warehouse)
                }
            );
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            loadWarehouses();
        }catch(error){
            toast.error(`Error: ${err.message}`)
        }finally{
            setOpenModal(false);
        }
    }

    return (
        <div className="p-4">
            <CreateWarehouseModal
                open={openModal}
                onClose={() => setOpenModal(false)}
                onSubmit={handleSubmit}
            />
            <h1 className="text-xl font-semibold mb-3">Warehouses</h1>

            <div className="grid grid-cols-6 gap-4">
                <button
                    className="flex flex-col items-center justify-center border-2 border-gray-400 h-40 rounded-xl p-6 hover:bg-orange-300 transition cursor-pointer"
                    onClick={()=> setOpenModal(true)}
                >
                    <span className="text-4xl font-bold">+</span>
                    <span className="mt-2">Add Warehouse</span>
                </button>
                {warehouses.map((warehouse) => (
                <WarehouseCard key={warehouse.id} warehouse={warehouse} loadWarehouses={loadWarehouses}/>
                ))}
            </div>
        </div>
  );
}

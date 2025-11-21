import Modal from "../Modal"
import { useState, useEffect } from "react"
import "./TransferProductModal.css"
import { useParams } from "react-router-dom";

export default function TransferProductModal({open, onClose, onSubmit, productId}) {
    const {warehouseId} = useParams();
    const [warehouses, setWarehouses] = useState([]);
    const [quantity, setQuantity] = useState(1);
    const [loading, setLoading] = useState(true);
    const [targetWarehouse, setTargetWarehouse] = useState({});
    const reset = () =>{
      setQuantity(1);
    }
    console.log(targetWarehouse)
    const handleSubmit = e => {
        console.log(warehouseId, targetWarehouse, { quantity, productId })
        e.preventDefault();
        onSubmit(warehouseId, targetWarehouse, { quantity, productId });
        reset();
        onClose();
    };

    const loadWarehouses = async () => {
        try{
          const response = await fetch("http://localhost:8080/warehouse");
          if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
          const result = await response.json();
          if(result.length > 1) result.sort((a, b) => a.id - b.id)
          setWarehouses(result);
        }catch(error){
          toast.error(`Error: ${error.message}`)
        }finally{
          setLoading(false);
        }
    }

    useEffect(() => {
        loadWarehouses();
    }, [])


    return (
      <Modal open={open} onClose={onClose} title="Transfer to Different Warehouse">
        {loading ? 
            <div className="text-center text-xl font-semibold loading-dots">Loading<span>.</span><span>.</span><span>.</span></div>
            :
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <label className="block mb-1 font-medium">Product</label>
                  <select
                    className="w-full border rounded-md px-3 py-2"
                    value={targetWarehouse}
                    onChange={e => setTargetWarehouse(e.target.value)}
                    required
                  >
                    <option value="" disabled>Select a Warehouse</option>
                    {warehouses.map(warehouse => (
                      <option
                      className="bg-gray-400" 
                      key={warehouse.id} 
                      value={warehouse.id}>
                        {`${warehouse.name} (${warehouse.warehouseType})`}
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
                  className="w-full bg-gray-600 text-white py-2 rounded-md hover:bg-gray-700"
                >
                  Create
                </button>
            </form>
        }
      </Modal>
    );
}

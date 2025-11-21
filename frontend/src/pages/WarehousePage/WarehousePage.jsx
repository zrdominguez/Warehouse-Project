import { useEffect, useState } from "react"
import WarehouseList from "../../components/WarehouseList";
import "./WarehousePage.css"
import toast from "react-hot-toast";

export default function WarehousePage() {

    const [warehouses, setWarehouses] = useState([]);
    const [loading, setLoading] = useState(true);

    const loadWarehouses = async () => {
        try{
            const response = await fetch("http://localhost:8080/warehouse");
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            const result = await response.json();
            if(result.length > 1) result.sort((a, b) => a.id - b.id)
            setWarehouses(result);
        }catch(error){
            toast.error(`Error: ${err.message}`)
        }finally{
            setLoading(false);
        }
    }

    useEffect(() => {
        loadWarehouses();
    },[])

    if(loading) return <div className="text-center text-xl font-semibold loading-dots">Loading<span>.</span><span>.</span><span>.</span></div>
    
  return (
    <div>
      <WarehouseList warehouses={warehouses} loadWarehouses={loadWarehouses}/>
    </div>
  )
}

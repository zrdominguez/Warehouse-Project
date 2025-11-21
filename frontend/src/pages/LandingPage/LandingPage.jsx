import { useState, useEffect } from "react";
import WarehouseCarousel from "../../components/WarehouseCarousel";
import "./LandingPage.css";

export default function LandingPage() {
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
    <div className="w-screen flex flex-col justify-center gap-20">
      <WarehouseCarousel warehouses={warehouses} direction={""}/>
      <WarehouseCarousel warehouses={warehouses} direction={"reverse"}/>
    </div>

  )
}

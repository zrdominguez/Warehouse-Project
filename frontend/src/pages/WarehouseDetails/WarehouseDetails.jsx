import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./WarehouseDetails.css";
import SectionProducts from "../../components/SectionProducts";
import toast from "react-hot-toast";

export default function WarehouseDetails() {
    const {warehouseId} = useParams();
    const [warehouse, setWarehouse] = useState(null);
    const [loading, setLoading] = useState(true);
    const [selectedTab, setSelectedTab] = useState(null);
    

    const loadWarehouse = async() => {
      try{
        const response = await fetch(`http://localhost:8080/warehouse/${warehouseId}`);
        if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
        const result = await response.json();
        setWarehouse(result);
      }catch(error){
        toast.error(`Error: ${err.message}`)
      }finally{
        setLoading(false);
      }
    }
    
    useEffect(() => {
      loadWarehouse();
    }, [warehouseId]);

    if(loading) return <div className="text-center text-xl font-semibold loading-dots">Loading<span>.</span><span>.</span><span>.</span></div>


    return (
    <div className="w-screen mx-auto py-10 px-4">
      <div className="mb-8 max-w-5xl pl-100">
        <h1 className="text-3xl font-bold">{warehouse.name}</h1>
        <p className="text-gray-600 mt-3">{warehouse.location}</p>
        <p className="text-gray-600">Capacity: {warehouse.current_load}/{warehouse.capacity}</p>
      </div>

      {/* ----- Tabs ----- */}
      <div className=" border-gray-300 w-xl justify-self-center">
        <nav className="flex space-x-4 justify-center">
          {warehouse?.sections.map((section, index) => (
            <button
              key={section.id}
              onClick={() => setSelectedTab(index)}
              className={`pb-1 pt-1 px-4 text-sm font-medium flex-1 bg-amber-100 border rounded-lg  
                ${
                  selectedTab === index
                    ? "border-b-2 border-black texy-bold text-black"
                    : "text-gray-600 hover:text-gray-800 hover:scale-105 hover:cursor-pointer" 
                }
              `}
            >
              {section.name == "GAME_CONSOLES" ? "GAME CONSOLES" : section.name}
            </button>
          ))}
        </nav>
      </div>
      <div className="mt-6">
        {warehouse?.sections[selectedTab] && (
          <SectionProducts section={warehouse?.sections[selectedTab]} loadWarehouse={loadWarehouse} />
        )}
      </div>
    </div>
  );
}

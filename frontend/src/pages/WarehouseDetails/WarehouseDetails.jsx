import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./WarehouseDetails.css";
import SectionProducts from "../../components/SectionProducts";

export default function WarehouseDetails() {
    const {warehouseId} = useParams();
    const [warehouse, setWarehouse] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [selectedTab, setSelectedTab] = useState(null);
    

    const loadWarehouse = async() => {
        try{
            const response = await fetch(`http://localhost:8080/warehouse/${warehouseId}`);
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            const result = await response.json();
            setWarehouse(result);
        }catch(error){
            setError(error);
        }finally{
            setLoading(false);
        }
    }
    
    useEffect(() => {
        loadWarehouse();
    }, [warehouseId]);

    const handleErrorClick = () => {
        window.location.reload();
    }

    if(loading) return <div className="text-center text-xl font-semibold loading-dots">Loading<span>.</span><span>.</span><span>.</span></div>
    if(error) return (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
            <strong className="font-bold">Error! </strong>
            <span className="block sm:inline">{error.message}</span>
            <span
                className="absolute top-0 bottom-0 right-0 px-4 py-3 error-x"
                onClick={handleErrorClick}>
                <svg className="fill-current h-6 w-6 text-red-500" role="button" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><title>Close</title><path d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z"/></svg>
            </span>
        </div>
    );



    return (
    <div className="w-screen mx-auto py-10 px-4">
      <div className="mb-8 max-w-5xl pl-100">
        <h1 className="text-3xl font-bold">{warehouse.name}</h1>
        <p className="text-gray-600">{warehouse.location}</p>
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
              {section.name}
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

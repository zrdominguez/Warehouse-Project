import { useEffect, useState } from "react";
import AddProductModal from "../../modals/AddProductModal";

export default function SectionProducts({section, loadWarehouse}) {

    const [openModal, setOpenModal] = useState(false);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const[allProducts, setAllProducts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try{
                const response = await fetch('http://localhost:8080/product');
                if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
                const result = await response.json();
                setAllProducts(result);
            }catch(error){
                setError(error);
            }finally{
                setLoading(false);
            }
        }
        fetchData();
    },[])

    const handleOnSubmit = async (product) => {
        setLoading(true);
        try{
            const response = await fetch(
                "http://localhost:8080/warehouse/1/sections/1/products",
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(product)
                }
            );
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            loadWarehouse();
        }catch(error){
            setError(error);
        }finally{
            setLoading(false);
            setOpenModal(false);
        }
    }

    if(loading) return (<></>);
    if(error) return (<></>);

    return (
        <div>
            <AddProductModal
                open={openModal}
                onClose={() => setOpenModal(false)}
                onSubmit={handleOnSubmit}
                products={allProducts}
            />
            <div className="mb-4 flex items-center justify-between">
                <h2 className="text-xl font-semibold">
                    Products in {section.name}
                </h2>
                <button 
                className="w-8 h-8 rounded-lg pb-0.5 border-2 bg-gray-400 border-gray-400 hover:bg-orange-300 transition"
                onClick={() => setOpenModal(true)}
                >
                    +
                </button>
            </div>
      

            {section.products.length === 0 ? (
                <p className="text-gray-500">No products in this section.</p>
            ) : (
                <div className="grid grid-cols-1 gap-4">
                    {section.products.map((product) => (
                        <div
                        key={product.id}
                        className="border rounded-lg p-4 shadow-sm bg-white"
                        >
                            <h3 className="text-lg font-medium text-neutral-950">{product.name}</h3>
                            <p className="text-gray-600 text-sm">SKU: {product.sku}</p>
                            <p className="text-gray-800 font-semibold">
                                Qty: {product.quantity}
                            </p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

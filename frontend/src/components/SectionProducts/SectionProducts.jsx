import { useEffect, useState } from "react";
import AddProductModal from "../../modals/AddProductModal";
import toast, { Toaster } from 'react-hot-toast';
import ConfirmDeleteToast from "../../custom-toasts/ConfirmDeleteToast/ConfirmDeleteToast";
import { useParams } from "react-router-dom";
import TransferProductModal from "../../modals/TransferProductModal/TransferProductModal";

export default function SectionProducts({section, loadWarehouse}) {
    const {warehouseId} = useParams();
    const [openAddModal, setOpenAddModal] = useState(false);
    const [transferModal, setTransferModal] = useState(false);
    const [allProducts, setAllProducts] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try{
                const response = await fetch('http://localhost:8080/product');
                if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
                const result = await response.json();
                setAllProducts(result);
            }catch(error){
                toast.error(`Error: ${error.message}`)
            }
        }
        fetchData();
    },[])

    const handleOnSubmit = async (product, warehouseId, sectionId) => {
        try{
            const response = await fetch(
                `http://localhost:8080/warehouse/${warehouseId}/sections/${sectionId}/products`,
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(product)
                }
            );
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            toast.success("Product Added!")
            loadWarehouse();
        }catch(error){
            toast.error(`Error: ${error.message}`)
        }finally{
            setOpenAddModal(false);
        }
    }

    const handleDelete = onConfirm => {
        toast.custom(t => (
            <ConfirmDeleteToast t={t} onConfirm={onConfirm} />
        ));
    }

    const onConfirm = async (sectionId, productId) => {
        try{
            const response = await fetch(
                `http://localhost:8080/warehouse/sections/${sectionId}/product/${productId}`,
                {
                    method: 'DELETE',
                }
            );
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            loadWarehouse();
        }catch(error){
            toast.error(`Error: ${error.message}`)
        }finally{
            setOpenAddModal(false);
        }
    }

    const handleAddQuantity = async (product, sectionId) => {
        try{
            const response = await fetch(
                `http://localhost:8080/section/${sectionId}/products/${product.id}`,
                {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({quantity : product.quantity + 1})
                }
            );
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            loadWarehouse();
            toast.success("Product Transfer!")
        }catch(error){
            toast.error(`Error: ${error.message}`)
        }
    }
    
    const handleSubtractQuantity = async (product, sectionId) => {
        try{
            const response = await fetch(
                `http://localhost:8080/section/${sectionId}/products/${product.id}`,
                {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({quantity : product.quantity - 1})
                }
            );
            if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
            loadWarehouse();
        }catch(error){
            toast.error(`Error: ${error.message}`)
        }
    }

    const handleTransfer = async (sourceWarehouseId, targetWarehouseId, product) => {
        try{
            const response = await fetch(
                `http://localhost:8080/warehouse/${sourceWarehouseId}/transfer/${targetWarehouseId}`,
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
            toast.error(`Error: ${error.message}`)
        }
    }

    return (
        <div className="pl-100 pr-100">
            {/* Toaster alert */}
            <div><Toaster /></div>
            
            {/* Add Modal */}
            <AddProductModal
            open={openAddModal}
            onClose={() => setOpenAddModal(false)}
            onSubmit={handleOnSubmit}
            products={allProducts}
            warehouseId={warehouseId}
            sectionId={section.id}
            />
            <TransferProductModal
            open={transferModal}
            onClose={() => setTransferModal(false)}
            onSubmit={handleTransfer}
            productId={selectedProduct}
            />
            <div className="mb-4 flex items-center justify-start gap-10">
                <h2 className="text-xl font-semibold">
                    Products in {section.name == "GAME_CONSOLES" ? "GAME CONSOLES" : section.name}
                </h2>
                <button 
                className="w-8 h-8 rounded-lg pb-0.5 border-2 bg-gray-400 border-gray-400 hover:bg-orange-300 transition"
                onClick={() => setOpenAddModal(true)}
                >
                    +
                </button>
            </div>
        
            {section.products.length === 0 ? (
                <p className="text-gray-500">No products in this section.</p>
            ) : (
                <div className="grid grid-cols-3 gap-4">
                    {section.products.map((product) => (
                        <div
                        key={product.id}
                        className="border rounded-lg p-4 shadow-sm bg-white relative"
                        >   
                            <button
                            onClick={() => handleDelete( () => onConfirm(section.id, product.id))}
                            className="absolute top-3 right-3 text-gray-500 hover:text-gray-700 hover:cursor-pointer hover:scale-105"
                            >
                                ✕
                            </button>
                            <h3 className="text-lg font-medium text-neutral-950">{product.name}</h3>
                            <p className="text-gray-600 text-sm">SKU: {product.sku}</p>
                            <div className="flex gap-2 items-center">
                                <p className="text-gray-800 font-semibold">
                                    Qty: {product.quantity}
                                </p>
                                <button 
                                className="w-4 rounded-lg  border-2 bg-gray-400 border-gray-400 hover:bg-blue-500 transition"
                                onClick={()=> handleAddQuantity(product, section.id)}
                                >
                                    +
                                </button>
                                <button 
                                className="w-4 rounded-lg  border-2 bg-gray-400 border-gray-400 hover:bg-red-500 transition"
                                onClick={()=> handleSubtractQuantity(product, section.id)}
                                >
                                    -
                                </button>
                            </div>
                            <button
                            className="mt-6 w-20 bg-gray-600 text-white py-2 rounded-md hover:bg-gray-700"
                            onClick={() => {
                                setTransferModal(true);
                                setSelectedProduct(product.id)
                            }}
                            >
                                Transfer
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

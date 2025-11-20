import WarehouseCard from "../WarehouseCard";
import "./WarehouseList.css";

export default function WarehouseList({warehouses}) {
    return (
        <div className="p-4">
            <h1 className="text-xl font-semibold mb-3">Warehouses</h1>

            <div className="grid grid-cols-6 gap-4">
                {warehouses.map((warehouse) => (
                <WarehouseCard key={warehouse.id} warehouse={warehouse} />
                ))}
                <button
                    className="flex flex-col items-center justify-center border-2 border-gray-400 rounded-xl p-6 hover:bg-orange-300 transition cursor-pointer"
                >
                    <span className="text-4xl font-bold">+</span>
                    <span className="mt-2">Add Warehouse</span>
                </button>
            </div>
        </div>
  );
}

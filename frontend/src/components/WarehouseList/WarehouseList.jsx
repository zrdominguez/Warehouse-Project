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
            </div>
        </div>
  );
}

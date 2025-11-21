import "./WarehouseCarousel.css";
import WarehouseCard from "../WarehouseCard";
import { useMemo } from "react";

export default function WarehouseCarousel({warehouses, direction}) {
  
  // Duplicate items so scrolling appears infinite
  const looped = useMemo(() => [...warehouses, ...warehouses], [warehouses]);
  return (
      <div className="overflow-hidden py-4 max-w-full">
        <div
          className={`inline-flex gap-4 w-max ${direction == "reverse" ? "animate-warehouse-scroll-rev" : "animate-warehouse-scroll"}`}
        >
          {looped.map((warehouse, index) => (
            <div key={index} className="shrink-0">
              <WarehouseCard warehouse={warehouse} />
            </div>
          ))}
        </div>
      </div>
  );
    
}

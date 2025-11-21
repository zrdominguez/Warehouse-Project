import {Link} from 'react-router-dom';
import "./TaskBar.css";

export default function TaskBar() {
   const items = [
    { label: "Home", to: "/" },
    { label: "Products", to: "/product" },
    { label: "Warehouses", to: "/warehouse" },
  ];

  return (
    <div className="w-full flex flex-col items-center mb-6">
      <h1 className="text-2xl font-bold mb-2">Warehouse Manager</h1>

      {/* Task Bar */}
      <nav className="flex gap-4 text-lg font-medium">
        {items.map((item, index) => (
          <div key={item.to} className="flex items-center">
            <Link className="hover:scale-105" to={item.to}>
              <h2 className='text-amber-900'>{item.label}</h2>
            </Link>

            {index < items.length - 1 && (
              <span className="px-3 text-gray-400">|</span>
            )}
          </div>
        ))}
      </nav>
    </div>
  );
}

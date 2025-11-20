import WarehouseCarousel from "../../components/WarehouseCarousel";
import WarehouseList from "../../components/WarehouseList";
import "./LandingPage.css";

const sample = [
  { id: 1, name: "Dallas Warehouse", location: "Texas", capacity: 5000 },
  { id: 2, name: "Chicago Storage", location: "Illinois", capacity: 8000 },
  { id: 3, name: "Orlando Hub", location: "Florida", capacity: 3000 },
  { id: 4, name: "Dallas Warehouse", location: "Texas", capacity: 5000 },
  { id: 5, name: "Chicago Storage", location: "Illinois", capacity: 8000 },
  { id: 6, name: "Orlando Hub", location: "Florida", capacity: 3000 },
  { id: 7, name: "Dallas Warehouse", location: "Texas", capacity: 5000 },
  { id: 8, name: "Chicago Storage", location: "Illinois", capacity: 8000 },
  { id: 9, name: "Orlando Hub", location: "Florida", capacity: 3000 },
  { id: 10, name: "Dallas Warehouse", location: "Texas", capacity: 5000 },
  { id: 11, name: "Chicago Storage", location: "Illinois", capacity: 8000 },
  { id: 12, name: "Orlando Hub", location: "Florida", capacity: 3000 },
];

export default function LandingPage() {
  return (
    <div className="w-screen flex flex-col justify-center gap-20">
      <WarehouseCarousel warehouses={sample} direction={""}/>
      <WarehouseCarousel warehouses={sample} direction={"reverse"}/>
    </div>

  )
}

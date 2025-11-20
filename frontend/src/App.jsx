import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import LandingPage from "./pages/LandingPage/LandingPage";
import WarehousePage from "./pages/WarehousePage";
import WarehouseDetails from "./pages/WarehouseDetails";


export default function App() {
  return (
     <BrowserRouter>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/" element={<LandingPage />} /> 
          <Route path="/warehouse" element={<WarehousePage />} />
          <Route path="/warehouse/:warehouseId" element={<WarehouseDetails />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

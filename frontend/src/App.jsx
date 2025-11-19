import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import LandingPage from "./components/LandingPage/LandingPage";


export default function App() {
  return (
     <BrowserRouter>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/" element={<LandingPage />} /> 
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

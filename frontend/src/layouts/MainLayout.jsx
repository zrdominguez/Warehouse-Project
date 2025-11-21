import Taskbar from "../components/Taskbar";
import { Outlet } from "react-router-dom";
import Footer from "../components/Footer";

export default function MainLayout() {
  return (
    <div className="flex flex-col min-h-screen">
      <Taskbar />
      <div className="flex-1">
        <Outlet />
      </div>
      <Footer className="justify-self-end w-screen"/>
    </div>
  );
}
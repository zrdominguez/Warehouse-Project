import Taskbar from "../components/Taskbar";
import { Outlet } from "react-router-dom";

export default function MainLayout() {
  return (
    <div>
      <Taskbar />
      <div className="page-content">
        <Outlet />
      </div>
    </div>
  );
}
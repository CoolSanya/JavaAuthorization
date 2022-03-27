import {FC} from "react";
import { Outlet } from "react-router-dom";
import Header from "./Header";

const AdminLayout : FC = () => {
    return (
        <>
            <Header />
            <Outlet/>      
        </>
    );
}
export default AdminLayout;
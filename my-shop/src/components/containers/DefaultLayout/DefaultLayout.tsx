import {FC} from "react";
import { Outlet } from "react-router-dom";
import Header from "./Header";
import "./DefaultLayout.css";

const DefaultLayout : FC = () => {
    return (
        <>
            <Header />
            <Outlet/>      
        </>
    );
}
export default DefaultLayout;
import React from 'react';
import Sidebar from "../Components/Sidebar";
import {Outlet} from "react-router-dom";
import '../css/sidebar.css'
import Header from "../Components/Header";
import Footer from '../../Header/pages/Footer';



function Layout(props) {
    return (
        <div className="layout_box">
            <Header/>
            <Outlet />
            <Footer/>
        </div>
    );
}

export default Layout;
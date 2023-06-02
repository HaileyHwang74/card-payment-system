import React from "react";
import '../css/sidebar.css'

function SidebarItem({ menu }) {
    return (
        // <div className="sidebar-item">
            <div className="sidebar_item">{menu.name}</div>
        // </div>
    );
}

export default SidebarItem;
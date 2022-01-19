import React from "react";
import MyClosetClothes from "../layouts/MyCloset/MyClosetClothes";
import MyClosetSidebar from "../layouts/MyCloset/MyClosetSidebar";

export default function MyCloset() {
  return (
    <>
      <h1>My Closet</h1>
      <div className="container">
        <div className="row">
          <div className="col-2" style={{'border-style': 'dashed'}}>
            <MyClosetSidebar />
          </div>
          <div className="col-10" style={{'border-style': 'dashed'}}>
            <MyClosetClothes />
          </div>
        </div>
      </div>
    </>
  );
}
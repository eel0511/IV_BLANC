import React from "react";
import MyClosetClothes from "../layouts/MyCloset/MyClosetClothes";
import MyClosetSidebar from "../layouts/MyCloset/MyClosetSidebar";

export default function MyCloset() {
  return (
    <>
      <h1>My Closet</h1>
      <div className="container">
        <div className="row">
          <div className="col-2" style={{'borderStyle': 'dashed'}}>
            <MyClosetSidebar />
          </div>
          <div className="col-10">
            <MyClosetClothes />
          </div>
        </div>
      </div>
    </>
  );
}
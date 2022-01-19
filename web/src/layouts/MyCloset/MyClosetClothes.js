import React from "react";
import MyClosetClothesItem from "../../components/MyCloset/MyClosetClothesItem"

export default function MyClosetClothes({ clothesDatas }) {

  return (
    <>
    <h1>옷들 나타내는 곳</h1>
    <div className="container-fluid">
      <div className="row">
        {clothesDatas.map((clothesData) => (
          <div className="col-4 mt-3" key={clothesData.clothesId}>
            <MyClosetClothesItem clothesData={clothesData} />
          </div>
        ))}
      </div>
    </div>
    </>
  );
}
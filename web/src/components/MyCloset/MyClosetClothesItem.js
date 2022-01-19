import React from "react";

export default function MyClosetClothesItem({ clothesData} ) {
  return (
    <div className="card h-100">
      <div className="card-body">
        <img
          src={require( `../../assets/${ clothesData.url }` )}
          alt={clothesData.clothesId}
          style={{'maxWidth': '100%', 'maxHeight': '100%'}}
        />
      </div>
    </div>
  );
}
import React, { useState, useEffect } from "react";
import axios from 'axios';
import MyClosetClothes from "../layouts/MyCloset/MyClosetClothes";
import MyClosetSidebar from "../layouts/MyCloset/MyClosetSidebar";

export default function MyCloset() {
  const [myClothes, setMyClothes] = useState([]);

  const testAxios = () => {
    axios(
      {
        url: 'api/clothes/all',
        method: 'get',
        baseURL: 'http://119.56.162.61:9999',
        data: {
          "page": 1,
          "userId": 1
        }
      }
    ).then((response) => {
      console.log(response.data)
    });
  };

  // useEffect(() => {
  //   testAxios();
  // }, []);

  return (
    <>
      <h1>My Closet</h1>
      <div className="container">
        <div className="row">
          <div className="col-2" style={{'borderStyle': 'dashed'}}>
            <MyClosetSidebar />
          </div>
          <div className="col-10">
            <MyClosetClothes myClothes={myClothes}/>
          </div>
        </div>
      </div>
    </>
  );
}
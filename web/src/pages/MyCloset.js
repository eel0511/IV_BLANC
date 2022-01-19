import React, { useState, useEffect } from "react";
import axios from 'axios';
import MyClosetClothes from "../layouts/MyCloset/MyClosetClothes";
import MyClosetSidebar from "../layouts/MyCloset/MyClosetSidebar";

export default function MyCloset() {
  const [myClothes, setMyClothes] = useState([]);
  const clothesData = [
    {
      "category": 10,
      "clothesId": 1,
      "color": "red",
      "count": 0,
      "createDate": "2022-01-19T08:28:17.455Z",
      "dislikePoint": 0,
      "favorite": 0,
      "material": "string",
      "season": 0,
      "size": 0,
      "updateDate": "2022-01-19T08:28:17.455Z",
      "url": "../assets/상의.jfif",
      "userId": 1
    },
    {
      "category": 20,
      "clothesId": 2,
      "color": "red",
      "count": 0,
      "createDate": "2022-01-20T08:28:17.455Z",
      "dislikePoint": 0,
      "favorite": 0,
      "material": "string",
      "season": 0,
      "size": 0,
      "updateDate": "2022-01-20T08:28:17.455Z",
      "url": "../assets/하의.jfif",
      "userId": 1
    }
  ];

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

  useEffect(() => {
    setMyClothes(clothesData);
  }, []);

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
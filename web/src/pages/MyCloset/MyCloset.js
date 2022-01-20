import React, { useState, useEffect } from "react";
import axios from 'axios';
import MyClosetClothes from "../../layouts/MyCloset/MyClosetClothes";
import MyClosetSidebar from "../../layouts/MyCloset/MyClosetSidebar";
import "./MyCloset.css";

export default function MyCloset() {
  const [myClothes, setMyClothes] = useState([]);
  const clothesDatas = [
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
      "url": "상의.jfif",
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
      "url": "하의.jfif",
      "userId": 1
    },
    {
      "category": 20,
      "clothesId": 3,
      "color": "red",
      "count": 0,
      "createDate": "2022-01-20T08:28:17.455Z",
      "dislikePoint": 0,
      "favorite": 0,
      "material": "string",
      "season": 0,
      "size": 0,
      "updateDate": "2022-01-20T08:28:17.455Z",
      "url": "logo.png",
      "userId": 1
    },
    {
      "category": 20,
      "clothesId": 4,
      "color": "red",
      "count": 0,
      "createDate": "2022-01-20T08:28:17.455Z",
      "dislikePoint": 0,
      "favorite": 0,
      "material": "string",
      "season": 0,
      "size": 0,
      "updateDate": "2022-01-20T08:28:17.455Z",
      "url": "logo2.png",
      "userId": 1
    }
  ];

  const testAxios = () => {
    axios(
      {
        url: 'api/clothes/all',
        method: 'get',
        baseURL: 'http://119.56.162.61:8888',
        params: {
          "page": 1,
          "userId": 1
        }
      }
    ).then((response) => {
      console.log(response)
    });
  };

  // useEffect(() => {
  //   testAxios()
  // }, []);

  return (
    <>
      <h1>My Closet</h1>
      <div className="MyClosetContainer">
        <MyClosetSidebar />
        <MyClosetClothes clothesDatas={clothesDatas}/>
      </div>
    </>
  );
}
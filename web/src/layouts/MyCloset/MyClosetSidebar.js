import React from "react";
import MyClosetSidebarItem from "../../components/MyCloset/MyClosetSidebarItem";

export default function MyClosetSidebar() {
  const menus = [
    { name: "전체" },
    { name: "상의" },
    { name: "하의" },
    { name: "아우터" },
    { name: "신발" },
    { name: "가방" },
    { name: "모자" },
    { name: "기타" }
  ];

  return (
    <>
    <h1>사이드 바</h1>
    <div>
      {menus.map((menu, index) => {
        return (
          <MyClosetSidebarItem menu={menu} key={index}/>
        );
      })}
    </div>
    </>
  );
}
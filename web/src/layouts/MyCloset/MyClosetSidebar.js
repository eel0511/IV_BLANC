import React from 'react';
import MyClosetSidebarItem from '../../components/MyCloset/MyClosetSidebarItem';
import './MyCloset.css';

export default function MyClosetSidebar() {
  const menus = [
    { name: '전체' },
    { name: '상의' },
    { name: '하의' },
    { name: '아우터' },
    { name: '신발' },
    { name: '가방' },
    { name: '모자' },
    { name: '기타' },
  ];

  return (
    <div className='MyClosetSidebar'>
      <div className='MyClosetSidebarWrapper'>
        <ul className='MyClosetSidebarList'>
          {menus.map((menu, index) => {
            return (
              <li className='MyClosetSidebarListItem' key={index}>
                <MyClosetSidebarItem menu={menu} />
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
}

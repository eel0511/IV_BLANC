import React, { useState } from 'react';
import { Nav } from 'react-bootstrap';
import MyClosetSidebarItem from '../../components/MyCloset/MyClosetSidebarItem';

export default function MyStyleTopbar() {
  const menus = [
    { name: '상의' },
    { name: '하의' },
    { name: '아우터' },
    { name: '신발' },
    { name: '가방' },
    { name: '모자' },
    { name: '기타' },
    { name: '룩' },
  ];

  return (
    <div className='MyStyleTopbar'>
      <div className='MyStyleTopbarWrapper'>
        <Nav className='mt-5 mb-3' variant='tabs' defaultActiveKey='link-0'>
          <Nav.Item>
            <Nav.Link eventKey='link-0'>Tab 1</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link eventKey='link-1'>Tab 2</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link eventKey='link-2'>Tab 3</Nav.Link>
          </Nav.Item>
        </Nav>
        {/* <ul className='tabs'>
          {menus.map((menu, index) => {
            return (
              <li className='MyClosetSidebarListItem' key={index}>
                <MyClosetSidebarItem menu={menu} />
              </li>
            );
          })}
        </ul> */}
      </div>
    </div>
  );
}

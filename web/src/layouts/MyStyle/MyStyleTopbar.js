import React, { useState } from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';

import AllClothes from '../../components/MyStyle/AllClothes';
import Top from '../../components/MyStyle/Top';
import Pants from '../../components/MyStyle/Pants';
import Outer from '../../components/MyStyle/Outer';
import Shoes from '../../components/MyStyle/Shoes';
import Bag from '../../components/MyStyle/Bag';
import Hat from '../../components/MyStyle/Hat';
import Other from '../../components/MyStyle/Other';
import StyleLook from '../../components/MyStyle/StyleLook';

export default function MyStyleTopbar() {
  const menus = [
    { name: '전체' },
    { name: '상의' },
    { name: '하의' },
    { name: '아우터' },
    { name: '신발' },
    { name: '가방' },
    { name: '모자' },
    { name: '기타' },
    { name: '룩' },
  ];

  const obj = {
    0: <AllClothes />,
    1: <Top />,
    2: <Pants />,
    3: <Outer />,
    4: <Shoes />,
    5: <Bag />,
    6: <Hat />,
    7: <Other />,
    8: <StyleLook />,
  };

  const [tab, setTab] = useState(0);
  const [title, setTitle] = useState('선택');

  const handleSelect = (e) => {
    console.log(e);
    setTab(e);
  };

  const handleClick = (e) => {
    console.log(e);
    setTitle(e.target.outerText);
  };

  return (
    <div className='wrapper'>
      <Nav
        className='mt-5 mb-3'
        variant='tabs'
        defaultActiveKey='link-0'
        onSelect={handleSelect}
      >
        <NavDropdown title={title} id='nav-dropdown'>
          {menus.map((menu, index) => {
            return (
              <Nav.Item offset='10px'>
                <Nav.Link eventKey={index} key={index} onClick={handleClick}>
                  {menu.name}
                </Nav.Link>
              </Nav.Item>
            );
          })}
        </NavDropdown>
      </Nav>
      {obj[tab]}
    </div>
  );
}

import React, { useState } from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';

import Upper from '../../components/MyStyle/Upper';
import Down from '../../components/MyStyle/Down';
import Outer from '../../components/MyStyle/Outer';

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

  const obj = {
    0: <Upper />,
    1: <Down />,
    2: <Outer />,
  };

  const [tab, setTab] = useState(0);
  const [title, setTitle] = useState('전체');

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

import React, { useState } from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';
import axios from 'axios';

import AllClothes from '../../components/MyStyle/AllClothes';
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

  const [tab, setTab] = useState(0);
  const [title, setTitle] = useState('선택');

  const [isData, setIsData] = useState(false);
  const [inputs, setInputs] = useState([
    {
      category: 0,
      clothesId: 0,
      color: '빨강',
      count: 0,
      createDate: '',
      dislikePoint: 0,
      favorite: 0,
      likePoint: 0,
      material: 0,
      season: 0,
      size: 0,
      updateDate: '',
      url: '',
      userId: 0,
    },
  ]);
  const [clothes, setClothes] = useState([]);

  const handleSelect = async (e) => {
    const category = e;
    console.log(category);
    setTab(e);

    if (Number(category) === 0) {
      try {
        await axios
          .get('http://i6d104.p.ssafy.io:9999/api/clothes/all', {
            headers: {
              'X-AUTH-TOKEN':
                'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
          })
          .then((res) => {
            // console.log(res);
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              alert('전체 옷 조회!');
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err);
      }
    } else {
      try {
        await axios
          .get('http://i6d104.p.ssafy.io:9999/api/clothes/category', {
            headers: {
              'X-AUTH-TOKEN':
                'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              category: category,
              page: 0,
            },
          })
          .then((res) => {
            // console.log(res);
            const resData = res.data.data;
            console.log('response:', resData);

            if (res.status === 200 && res.data.output === 1) {
              alert(`${category} 조회`);
              setIsData(true);
              setClothes([]);
              resData.map(
                // (clothesData) => console.log(clothesData)

                // (clothesData) => setInputs((inputs) => [...inputs, clothesData])
                (clothesData) =>
                  setClothes((clothes) => [...clothes, clothesData])
              );
              // setClothes((clothes)=>[...clothes, res.data.data]);
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err);
      }
    }
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
      {/* <div className='card h-100'>
        <div className='card-body'>
          {clothes.map((clothesData) => (
            <img
              className='MyStyleClothesItemImg'
              key={clothesData.clothesId}
              src={require(`${clothesData.url}`)}
              alt={clothesData.clothesId}
              style={{
                width: '100px',
                height: '100px',
              }}
            />
          ))}
        </div>
      </div> */}
      {isData && <AllClothes clothes={clothes} />}
      {/* {isData && <Top clothes={clothes} />} */}
      {/* {isData && <p>{inputs}</p>} */}
      {/* <div>
        {clothes.map((clothesData) => (
          // <img
          //   className='MyStyleClothesItemImg'
          //   key={clothesData.clothesId}
          //   src={require(`${clothesData.url}`)}
          //   alt={clothesData.clothesId}
          //   style={{
          //     width: '100px',
          //     height: '100px',
          //   }}
          // />
          <p>{clothesData.url}</p>
        ))}
      </div> */}
      {/* {obj[tab]} */}
    </div>
  );
}

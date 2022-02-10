import React, { useState } from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';
import { Container, Row, Col } from 'react-bootstrap';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import axios from 'axios';
import { Modal } from 'react-bootstrap';

import SelectedImage from '../../components/MyStyle/SelectedImage';
import Clothes from '../../components/MyStyle/Clothes';
import StyleLook from '../../components/MyStyle/StyleLook';
import FriendStyleCreateModalBody from '../../components/Friends/FriendStyleCreateModalBody';

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

  const clothesDatas = [
    {
      category: 10,
      clothesId: 1,
      color: 'red',
      count: 0,
      createDate: '2022-01-19T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-19T08:28:17.455Z',
      url: '상의.jfif',
      userId: 1,
    },
    {
      category: 20,
      clothesId: 2,
      color: 'red',
      count: 0,
      createDate: '2022-01-20T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-20T08:28:17.455Z',
      url: '하의.jfif',
      userId: 1,
    },
    {
      category: 20,
      clothesId: 3,
      color: 'red',
      count: 0,
      createDate: '2022-01-20T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-20T08:28:17.455Z',
      url: 'logo.png',
      userId: 1,
    },
    {
      category: 20,
      clothesId: 4,
      color: 'red',
      count: 0,
      createDate: '2022-01-20T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-20T08:28:17.455Z',
      url: 'logo2.png',
      userId: 1,
    },
  ];

  const [tab, setTab] = useState(0);
  const [title, setTitle] = useState('선택');

  const [isData, setIsData] = useState(false);
  const [clothes, setClothes] = useState([]);
  const [selectedClothes, setSelectedClothes] = useState([]);
  const [saveClothesId, setSaveClothesId] = useState([]);
  const [isShowLook, setIsShowLook] = useState(false);

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

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
            const resData = res.data.data;
            console.log('response:', resData);

            if (res.status === 200 && res.data.output === 1) {
              // alert('전체 옷 조회!');
              setIsData(true);
              setClothes([]);
              resData.map((clothesData) =>
                setClothes((clothes) => [...clothes, clothesData])
              );
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
              // alert(`${category} 조회`);
              setIsData(true);
              setClothes([]);
              resData.map((clothesData) =>
                setClothes((clothes) => [...clothes, clothesData])
              );
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

  const saveClothes = (e) => {
    const clothId = Number(e.target.alt);
    const url = e.target.src;
    const category = Number(e.target.title);
    console.log(e.target);
    console.log(e);
    const selectedData = {
      clothesId: clothId,
      url: url,
      category: category,
    };
    const selectedClothesId = {
      clothesId: clothId,
    };
    setSelectedClothes((selectedClothes) => [...selectedClothes, selectedData]);
    setSaveClothesId((saveClothesId) => [...saveClothesId, selectedClothesId]);
  };

  const saveStyle = async (e) => {
    e.preventDefault();

    // selectedClothes에서 clothesId만 뽑아야내야 함

    // 백엔드 통신
    try {
      await axios
        .post('http://i6d104.p.ssafy.io:9999/api/style/add', {
          headers: {
            'X-AUTH-TOKEN':
              'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
          },
          styleDetails: saveClothesId,
        })
        .then((res) => {
          console.log('response:', res.data);
          if (res.status === 200 && res.data.output === 1) {
            alert('스타일 저장 성공!!');
          } else if (res.status === 200 && res.data.output === 0) {
            alert(res.data.msg);
          } else {
            alert(res.data.msg);
          }
        });
    } catch (err) {
      console.error(err);
    }
  };

  const showStyle = (e) => {
    e.preventDefault();

    setIsShowLook(true);
  };

  const handleInitiate = async (e) => {
    e.preventDefault();

    if (window.confirm('진짜 초기화 하시겠습니까?')) {
      setSelectedClothes([]);
      setIsShowLook(false);
      alert('초기화 하였습니다');
    } else {
      alert('취소합니다.');
    }
  };

  return (
    <Container fluid='md'>
      <Row xs={1} md={2}>
        <Col sm={4}>
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
                      <Nav.Link
                        eventKey={index}
                        key={index}
                        onClick={handleClick}
                      >
                        {menu.name}
                      </Nav.Link>
                    </Nav.Item>
                  );
                })}
              </NavDropdown>
            </Nav>

            {/* 서버 연동 */}
            {isData && clothes.length > 0 ? (
              <div className='container-fluid'>
                <div className='row'>
                  {clothes.map((clothesData) => (
                    <div className='col-4 mt-3' key={clothesData.clothesId}>
                      <div className='card h-100'>
                        <div className='card-body'>
                          <img
                            className='MyClosetClothesItemImg'
                            src={clothesData.url}
                            alt={clothesData.clothesId}
                            title={clothesData.category}
                            style={{
                              maxWidth: '100%',
                              maxHeight: '100%',
                            }}
                            onClick={saveClothes}
                          />
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ) : (
              <p>등록된 데이터가 없습니다.</p>
            )}

            <SelectedImage selectedClothes={selectedClothes} />

            {selectedClothes.length > 0 && (
              <div
                style={{
                  display: 'flex',
                  justifyContent: 'center',
                  marginTop: '50px',
                }}
              >
                <Stack direction='row' spacing={2}>
                  <Button
                    variant='contained'
                    color='success'
                    onClick={handleShow}
                  >
                    스타일 추천
                  </Button>

                  <Modal
                    aria-labelledby='contained-modal-title-vcenter'
                    centered
                    show={show}
                    onHide={handleClose}
                  >
                    <Modal.Header closeButton>
                      <Modal.Title>스타일 추천</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                      <FriendStyleCreateModalBody saveClothesId={saveClothesId} />
                    </Modal.Body>
                  </Modal>

                  <Button variant='contained' color='info' onClick={showStyle}>
                    스타일 보기
                  </Button>
                  <Button
                    variant='contained'
                    color='error'
                    onClick={handleInitiate}
                  >
                    초기화
                  </Button>
                </Stack>
              </div>
            )}
          </div>
        </Col>
        <Col sm={8}>
          {isShowLook && <StyleLook selectedClothes={selectedClothes} />}
        </Col>
      </Row>
    </Container>
  );
}

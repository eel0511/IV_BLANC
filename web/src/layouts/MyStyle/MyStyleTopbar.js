import React, { useState, useEffect } from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';
import { Container, Row, Col } from 'react-bootstrap';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import axios from 'axios';
import { Modal } from 'react-bootstrap';

import SelectedImage from '../../components/MyStyle/SelectedImage';
import StyleLook from '../../components/MyStyle/StyleLook';
import ShowLookImg from '../../components/MyStyle/ShowLookImg';
import MyStyleCreateModalBody from '../../components/MyStyle/MyStyleCreateModalBody';
import MyStyleAIModalBody from '../../components/MyStyle/MyStyleAIModalBody';

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
  const [clothes, setClothes] = useState([
    {
      createDate: '2022-02-08T15:51:09',
      updateDate: '2022-02-08T15:51:09',
      clothesId: 62,
      category: 21,
      color: '#000000',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/b8c70839-8ded-4dd8-b198-e5044ebbfe32.jfif',
      favorite: 0,
      size: 100,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:53:33',
      updateDate: '2022-02-08T17:37:18',
      clothesId: 63,
      category: 40,
      color: '#FFFFFF',
      material: 16,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/55f53847-bed5-4a8b-a474-72367449d31d.jfif',
      favorite: 1,
      size: 260,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:54:41',
      updateDate: '2022-02-08T15:54:41',
      clothesId: 64,
      category: 30,
      color: '#F6EFDF',
      material: 4,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/65789b82-25d5-4c4b-8998-38fd2a5d6553.jfif',
      favorite: 0,
      size: 100,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:03',
      updateDate: '2022-02-08T15:55:03',
      clothesId: 65,
      category: 60,
      color: '#C14D49',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/49f9a751-f2b3-4163-973e-3dbe0e756ce3.jfif',
      favorite: 0,
      size: 95,
      season: 1,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:31',
      updateDate: '2022-02-08T15:55:31',
      clothesId: 66,
      category: 50,
      color: '#27476F',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/11392b62-bff2-43b0-95d1-d810f18d9b87.jfif',
      favorite: 0,
      size: 95,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T18:59:42',
      updateDate: '2022-02-08T18:59:42',
      clothesId: 69,
      category: 11,
      color: '#27476F',
      material: 8,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/ab47070f-e237-4e5d-8044-b074d53aa4fe.png',
      favorite: 0,
      size: 1,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T21:49:24',
      updateDate: '2022-02-08T21:49:24',
      clothesId: 73,
      category: 10,
      color: '#ABAEB6',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/45e3779f-5878-4106-a422-ab4719acf55d.jfif',
      favorite: 0,
      size: 95,
      season: 2,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
  ]);
  const [filterMyClothes, setFilterMyClothes] = useState([
    {
      createDate: '2022-02-08T15:51:09',
      updateDate: '2022-02-08T15:51:09',
      clothesId: 62,
      category: 21,
      color: '#000000',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/b8c70839-8ded-4dd8-b198-e5044ebbfe32.jfif',
      favorite: 0,
      size: 100,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:53:33',
      updateDate: '2022-02-08T17:37:18',
      clothesId: 63,
      category: 40,
      color: '#FFFFFF',
      material: 16,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/55f53847-bed5-4a8b-a474-72367449d31d.jfif',
      favorite: 1,
      size: 260,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:54:41',
      updateDate: '2022-02-08T15:54:41',
      clothesId: 64,
      category: 30,
      color: '#F6EFDF',
      material: 4,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/65789b82-25d5-4c4b-8998-38fd2a5d6553.jfif',
      favorite: 0,
      size: 100,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:03',
      updateDate: '2022-02-08T15:55:03',
      clothesId: 65,
      category: 60,
      color: '#C14D49',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/49f9a751-f2b3-4163-973e-3dbe0e756ce3.jfif',
      favorite: 0,
      size: 95,
      season: 1,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:31',
      updateDate: '2022-02-08T15:55:31',
      clothesId: 66,
      category: 50,
      color: '#27476F',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/11392b62-bff2-43b0-95d1-d810f18d9b87.jfif',
      favorite: 0,
      size: 95,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T18:59:42',
      updateDate: '2022-02-08T18:59:42',
      clothesId: 69,
      category: 11,
      color: '#27476F',
      material: 8,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/ab47070f-e237-4e5d-8044-b074d53aa4fe.png',
      favorite: 0,
      size: 1,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T21:49:24',
      updateDate: '2022-02-08T21:49:24',
      clothesId: 73,
      category: 10,
      color: '#ABAEB6',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/45e3779f-5878-4106-a422-ab4719acf55d.jfif',
      favorite: 0,
      size: 95,
      season: 2,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
  ]);
  const [selectedClothes, setSelectedClothes] = useState([]);
  const [saveClothesId, setSaveClothesId] = useState([]);
  const [isShowLook, setIsShowLook] = useState(false);
  const [isClickLook, setIsClickLook] = useState(false);
  const [styleClothes, setStyleClothes] = useState([]);
  const [styleLook, setStyleLook] = useState({});

  const [show, setShow] = useState(false);
  const [AIshow, setAIShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleAIClose = () => setAIShow(false);
  const handleAIShow = () => setAIShow(true);

  const token = localStorage.getItem('JWT');
  // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const getMyClothesData = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/clothes/all', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        setClothes(response.data.data);
        setFilterMyClothes(response.data.data);
      });
  };

  const getStyleLook = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/style/finduserstyle', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        setStyleClothes(response.data.data);
      });
  };

  useEffect(() => {
    getMyClothesData();
    getStyleLook();
  }, []);

  const handleSelect = async (e) => {
    const category = e;
    console.log(category);
    setTab(Number(category));
    setIsData(true);

    if (Number(category) === 0) {
      setFilterMyClothes([]);
      setFilterMyClothes(clothes);
    } else {
      const filterClothesDatas = clothes.filter(
        (clothesData) =>
          parseInt(clothesData.category / 10) === Number(category)
      );
      setFilterMyClothes([]);
      setFilterMyClothes(filterClothesDatas);
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

  const showStyle = (e) => {
    // e.preventDefault();
    setIsClickLook(false);
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

  const showLook = (e) => {
    // e.preventDefault();
    console.log(e);
    setIsClickLook(true);
    setIsShowLook(false);
    const data = {
      styleId: Number(e.target.alt),
      url: e.target.src,
    };
    console.log(data);
    setStyleLook(data);
  };

  let showImg = (
    <div className='row'>
      {filterMyClothes.map((clothesData) => (
        <div className='col-3 mt-4' key={clothesData.clothesId}>
          <div className='card h-100'>
            <div className='card-body'>
              <img
                className='MyClosetClothesItemImg'
                src={clothesData.url}
                alt={clothesData.clothesId}
                title={clothesData.category}
                style={{
                  width: '110px',
                  height: '110px',
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
  );

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
            {tab < 8 ? (
              filterMyClothes.length > 0 ? (
                showImg
              ) : (
                <NowImg />
              )
            ) : styleClothes.length > 0 ? (
              <div className='row'>
                {styleClothes.map((clothesData) => (
                  <div className='col-4 mt-4' key={clothesData.styleId}>
                    <div className='card h-100'>
                      <div className='card-body'>
                        <img
                          className='MyClosetClothesItemImg'
                          src={clothesData.url}
                          alt={clothesData.styleId}
                          style={{
                            maxWidth: '100%',
                            maxHeight: '100%',
                          }}
                          onClick={showLook}
                        />
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <NowImg />
            )}

            <SelectedImage selectedClothes={selectedClothes} />

            {selectedClothes.length > 0 && (
              <div
                style={{
                  display: 'flex',
                  justifyContent: 'center',
                  marginTop: '50px',
                  marginBottom: '50px',
                }}
              >
                <Stack direction='row' spacing={2}>
                  {tab === 1 && (
                    <Button
                      variant='contained'
                      color='secondary'
                      onClick={handleAIShow}
                    >
                      AI 스타일링
                    </Button>
                  )}
                  <Modal
                    aria-labelledby='contained-modal-title-vcenter'
                    centered
                    size='lg'
                    show={AIshow}
                    onHide={handleAIClose}
                  >
                    <Modal.Header closeButton>
                      <Modal.Title>AI 스타일링</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                      <MyStyleAIModalBody />
                    </Modal.Body>
                  </Modal>

                  <Button
                    variant='contained'
                    color='success'
                    onClick={handleShow}
                  >
                    스타일 저장
                  </Button>
                  <Modal
                    aria-labelledby='contained-modal-title-vcenter'
                    centered
                    size='lg'
                    show={show}
                    onHide={handleClose}
                  >
                    <Modal.Header closeButton>
                      <Modal.Title>스타일 저장</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                      <MyStyleCreateModalBody
                        saveClothesId={saveClothesId}
                        handleClose={handleClose}
                        getStyleLook={getStyleLook}
                      />
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
          {isClickLook && <ShowLookImg styleLook={styleLook} />}
        </Col>
      </Row>
    </Container>
  );
}

function NowImg() {
  return <p>등록된 데이터가 없습니다.</p>;
}

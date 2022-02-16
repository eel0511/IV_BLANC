import React, { useState, useEffect } from 'react';
import { Modal, Button, ListGroup, Container, Row, Col } from 'react-bootstrap';
import moment from 'moment';
import Checkbox from '@mui/material/Checkbox';
import FavoriteBorder from '@mui/icons-material/FavoriteBorder';
import Favorite from '@mui/icons-material/Favorite';
import axios from 'axios';
import codeData from '../../codeData.json';
import './MyCloset.css';
import DetailClothes from './MyClosetDetailItem';

const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

export default function MyClosetClothesItem({ clothesData, getMyClothesData }) {
  const [show, setShow] = useState(false);
  const [date, setDate] = useState(clothesData.createDate);
  const [favoriteChecked, setFavoriteChecked] = useState(false);

  // 종류, 색깔, 소재, 계절
  const [category, setCategory] = useState('');
  const [color, setColor] = useState('');
  const [material, setMaterial] = useState('');
  const [season, setSeason] = useState('');

  useEffect(() => {
    setDate(moment(clothesData.createDate).format('YYYY-MM-DD HH:mm:ss'));

    let mainCategory = '';
    switch (parseInt(clothesData.category / 10)) {
      case 1:
        mainCategory = codeData['category'].상의;
        break;
      case 2:
        mainCategory = codeData['category'].하의;
        break;
      case 3:
        mainCategory = codeData['category'].아우터;
        break;
      case 4:
        mainCategory = codeData['category'].신발;
        break;
      case 5:
        mainCategory = codeData['category'].가방;
        break;
      case 6:
        mainCategory = codeData['category'].모자;
        break;
      default:
        mainCategory = codeData['category'].기타;
        break;
    }

    // console.log(mainCategory);
    // console.log(
    //   Object.keys(mainCategory).find(
    //     (key) => mainCategory[key] === clothesData.category
    //   )
    // );
    setCategory(
      Object.keys(mainCategory).find(
        (key) => mainCategory[key] === clothesData.category
      )
    );
    setColor(
      Object.keys(codeData['colors']).find(
        (key) => codeData['colors'][key] === clothesData.color
      )
    );
    setMaterial(
      Object.keys(codeData['material2']).find(
        (key) => codeData['material2'][key] === clothesData.material
      )
    );
    setSeason(
      Object.keys(codeData['season']).find(
        (key) => codeData['season'][key] === clothesData.season
      )
    );

    // 즐겨찾기 데이터를 받아와서 favorite === 1이면 setFavoriteChecked 수정
    if (clothesData.favorite === 1) {
      setFavoriteChecked(true);
    }
  }, []);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleChange = async (e) => {
    // console.log(e.target.checked);
    const favoriteCurrent = e.target.checked;
    setFavoriteChecked(favoriteCurrent);
    // console.log(favoriteCurrent);

    // 체크했을 때 서버에 즐겨찾기 등록 정보 전달
    // favoriteChecked===true이면 추가, 아니면 삭제
    // 추후에 토큰 사용한 백엔드 연동 구현
    if (favoriteCurrent) {
      try {
        await axios
          .put('http://i6d104.p.ssafy.io:9999/api/clothes/addfavorite',
          {
            clothes_id: Number("") 
          }, {
            headers: {
              'X-AUTH-TOKEN': localStorage.getItem('JWT'),
              // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              clothesId: `${clothesData.clothesId}`,
            },
          })
          .then((res) => {
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              alert(res.data.msg);
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err.response.data);
      }
    } else {
      try {
        await axios
          .put('http://i6d104.p.ssafy.io:9999/api/clothes/deletefavorite',{
            clothes_id: Number("") 
          }, {
            headers: {
              'X-AUTH-TOKEN': localStorage.getItem('JWT'),
              // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              clothesId: `${clothesData.clothesId}`,
            },
          })
          .then((res) => {
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              alert(res.data.msg);
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err.response.data);
      }
    }
  };

  const handleDelete = async (e) => {
    e.preventDefault();

    if (window.confirm('진짜 삭제하시겠습니까?')) {
      // 삭제 기능 구현
      // 토큰 포함 버전으로 바꿔야 함
      try {
        await axios
          .delete('http://i6d104.p.ssafy.io:9999/api/clothes/deleteById', {
            headers: {
              'X-AUTH-TOKEN': localStorage.getItem('JWT'),
              // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              clothesId: `${clothesData.clothesId}`,
            },
          })
          .then((res) => {
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              console.log(res.data.msg);
              alert('삭제되었습니다.');
              setShow(false);
              getMyClothesData();
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err.response.data);
      }
    } else {
      alert('취소합니다.');
    }
  };

  return (
    <div className='card myClothes__card'>
      <div className='card-body'>
        <img
          className='MyClosetClothesItemImg'
          src={`${clothesData.url}`}
          alt={clothesData.clothesId}
          style={{ maxWidth: '100%', maxHeight: '100%' }}
          onClick={handleShow}
        />

        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Detail</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Container>
              <Row>
                <Col md={{ span: 7, offset: 2 }}>
                  <img
                    src={`${clothesData.url}`}
                    alt={clothesData.clothesId}
                    style={{ maxWidth: '100%', maxHeight: '100%' }}
                  />
                </Col>
                <Col md={{ span: 1, offset: 1 }}>
                  <Checkbox
                    {...label}
                    icon={<FavoriteBorder />}
                    checkedIcon={<Favorite />}
                    checked={favoriteChecked}
                    onChange={handleChange}
                    color='error'
                  />
                </Col>
              </Row>
            </Container>

            <ListGroup variant='flush'>
              <DetailClothes item='종류' value={category} />
              <DetailClothes item='색깔' value={color} />
              <DetailClothes item='소재' value={material} />
              <DetailClothes item='계절' value={season} />
              <DetailClothes item='입은 횟수' value={clothesData.count} />
              <DetailClothes item='좋아요' value={clothesData.likePoint} />
              <DetailClothes item='싫어요' value={clothesData.dislikePoint} />
              <DetailClothes item='등록날짜' value={date} />
            </ListGroup>
          </Modal.Body>
          <Modal.Footer>
            <Container>
              <Row>
                <Col>
                  <Button
                    variant='danger'
                    style={{ float: 'left' }}
                    onClick={handleDelete}
                  >
                    삭제
                  </Button>
                </Col>
                <Col xs={7}></Col>
                <Col>
                  <Button variant='secondary' onClick={handleClose}>
                    Close
                  </Button>
                </Col>
              </Row>
            </Container>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
}

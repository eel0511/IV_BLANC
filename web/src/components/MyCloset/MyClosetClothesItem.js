import React, { useState, useEffect } from 'react';
import { Modal, Button, ListGroup, Container, Row, Col } from 'react-bootstrap';
import moment from 'moment';
import Checkbox from '@mui/material/Checkbox';
import FavoriteBorder from '@mui/icons-material/FavoriteBorder';
import Favorite from '@mui/icons-material/Favorite';
import axios from 'axios';
import './MyCloset.css';

const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

export default function MyClosetClothesItem({ clothesData }) {
  const [show, setShow] = useState(false);
  const [date, setDate] = useState(clothesData.createDate);
  const [favoriteChecked, setFavoriteChecked] = useState(false);

  useEffect(() => {
    setDate(moment(clothesData.createDate).format('YYYY-MM-DD HH:mm:ss'));

    // 즐겨찾기 데이터를 받아와서 favorite === 1이면 setFavoriteChecked 수정
  }, []);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleChange = async (e) => {
    console.log(e.target.checked);
    const favoriteCurrent = e.target.checked;
    setFavoriteChecked(favoriteCurrent);
    console.log(favoriteCurrent);

    // 체크했을 때 서버에 즐겨찾기 등록 정보 전달
    // favoriteChecked===true이면 추가, 아니면 삭제
    // 추후에 토큰 사용한 백엔드 연동 구현
    // if (favoriteCurrent) {
    //   try {
    //     await axios.put('http://119.56.162.61:8888/api/clothes/addfavorite?clothesId=1').then((res) => {
    //       console.log('response:', res.data);
    //       if (res.status === 200 && res.data.output === 1) {
    //         alert(res.data.msg);
    //       } else if (res.status === 200 && res.data.output === 0) {
    //         alert(res.data.msg);
    //       } else {
    //         alert(res.data.msg);
    //       }
    //     });
    //   } catch (err) {
    //     console.error(err.response.data);
    //   }
    // } else {
    //   try {
    //     await axios
    //       .put('http://119.56.162.61:8888/api/clothes/deletefavorite', {
    //         clothesId: 1,
    //       })
    //       .then((res) => {
    //         console.log('response:', res.data);
    //         if (res.status === 200 && res.data.output === 1) {
    //           alert(res.data.msg);
    //         } else if (res.status === 200 && res.data.output === 0) {
    //           alert(res.data.msg);
    //         } else {
    //           alert(res.data.msg);
    //         }
    //       });
    //   } catch (err) {
    //     console.error(err.response.data);
    //   }
    // }
  };

  const handleDelete = async (e) => {
    e.preventDefault();

    if (window.confirm('진짜 삭제하시겠습니까?')) {
      // 삭제 기능 구현

      alert('삭제되었습니다.');
      setShow(false);
    } else {
      alert('취소합니다.');
    }
  };

  return (
    <div className='card h-100'>
      <div className='card-body'>
        <img className='MyClosetClothesItemImg' src={require(`../../assets/${clothesData.url}`)} alt={clothesData.clothesId} style={{ maxWidth: '100%', maxHeight: '100%' }} onClick={handleShow} />

        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>옷 상세정보</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Container>
              <Row>
                <Col xs={12} md={8}>
                  <img src={require(`../../assets/${clothesData.url}`)} alt={clothesData.clothesId} style={{ maxWidth: '100%', maxHeight: '100%' }} />
                </Col>
                <Col xs={6} md={4}>
                  <Checkbox {...label} icon={<FavoriteBorder />} checkedIcon={<Favorite />} checked={favoriteChecked} onChange={handleChange} color='error' />
                </Col>
              </Row>
            </Container>

            <ListGroup variant='flush'>
              <ListGroup.Item>종류 : {clothesData.category}</ListGroup.Item>
              <ListGroup.Item>색깔 : {clothesData.color}</ListGroup.Item>
              <ListGroup.Item>사이즈 : {clothesData.size}</ListGroup.Item>
              <ListGroup.Item>소재 : {clothesData.material}</ListGroup.Item>
              <ListGroup.Item>계절 : {clothesData.season}</ListGroup.Item>
              <ListGroup.Item>입은 횟수 : {clothesData.count}</ListGroup.Item>
              <ListGroup.Item>좋아요 : {clothesData.likePoint}</ListGroup.Item>
              <ListGroup.Item>싫어요 : {clothesData.dislikePoint}</ListGroup.Item>
              <ListGroup.Item>등록날짜 : {date}</ListGroup.Item>
            </ListGroup>
          </Modal.Body>
          <Modal.Footer>
            <Container>
              <Row>
                <Col>
                  <Button variant='danger' style={{ float: 'left' }} onClick={handleDelete}>
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

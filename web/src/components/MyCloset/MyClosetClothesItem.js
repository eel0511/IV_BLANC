import React, { useState, useEffect } from 'react';
import { Modal, Button, ListGroup } from 'react-bootstrap';
import moment from 'moment';
import './MyCloset.css';

export default function MyClosetClothesItem({ clothesData }) {
  const [show, setShow] = useState(false);
  const [date, setDate] = useState(clothesData.createDate);

  useEffect(() => {
    setDate(moment(clothesData.createDate).format('YYYY-MM-DD HH:mm:ss'));
  }, []);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <div className='card h-100'>
      <div className='card-body'>
        <img src={require(`../../assets/${clothesData.url}`)} alt={clothesData.clothesId} style={{ maxWidth: '100%', maxHeight: '100%' }} onClick={handleShow} />

        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>옷 상세정보</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <img src={require(`../../assets/${clothesData.url}`)} alt={clothesData.clothesId} style={{ maxWidth: '100%', maxHeight: '100%' }} />
            <ListGroup variant='flush'>
              <ListGroup.Item>종류 : {clothesData.category}</ListGroup.Item>
              <ListGroup.Item>색깔 : {clothesData.color}</ListGroup.Item>
              <ListGroup.Item>사이즈 : {clothesData.size}</ListGroup.Item>
              <ListGroup.Item>소재 : {clothesData.material}</ListGroup.Item>
              <ListGroup.Item>계절 : {clothesData.season}</ListGroup.Item>
              <ListGroup.Item>입은 횟수 : {clothesData.count}</ListGroup.Item>
              <ListGroup.Item>싫어요 : {clothesData.dislikePoint}</ListGroup.Item>
              <ListGroup.Item>등록날짜 : {date}</ListGroup.Item>
            </ListGroup>
            {/* <p>{clothesData.clothesId}</p>
            <p>{clothesData.category}</p>
            <p>{clothesData.color}</p>
            <p>{clothesData.createDate}</p>
            <p>{clothesData.material}</p> */}
          </Modal.Body>
          <Modal.Footer>
            <Button variant='secondary' onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
}

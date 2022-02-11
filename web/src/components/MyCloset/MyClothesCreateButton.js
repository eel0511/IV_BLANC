import React, { useState } from 'react';
import { Modal } from 'react-bootstrap';
import MyClothesCreateModalBody from './MyClothesCreateModalBody';

export default function MyClothesCreateButton() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <button
        type='button'
        className='btn btn-danger'
        style={{
          'backgroundColor': '#ed6991',
          color: 'white',
          border: 'none',
        }}
        onClick={handleShow}
      >
        등록 하기
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>등록 하기</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <MyClothesCreateModalBody handleClose={handleClose}/>
        </Modal.Body>
      </Modal>
    </>
  );
}

import React, { useState } from 'react';
import { Modal } from 'react-bootstrap';
import MyClothesCreateModalBody from './MyClothesCreateModalBody';

export default function MyClothesCreateButton({ getMyClothesData }) {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <button
        type='button'
        className='btn btn-danger'
        style={{
          backgroundColor: '#ed6991',
          color: 'white',
          border: 'none',
        }}
        onClick={handleShow}
      >
        등록 하기
      </button>

      <Modal show={show} size='xl' onHide={handleClose}>
        <Modal.Header
          closeButton
          style={{
            marginLeft: '0%',
            marginBottom: '0%',
            backgroundColor: '#EA8FAA',
            color: 'white',
          }}
        >
          <div
            className='d-flex align-content-center justify-content-center'
            style={{
              marginLeft: '45%',
            }}
          >
            <Modal.Title
              style={{
                fontSize: '2rem',
              }}
            >
              등록 하기
            </Modal.Title>
          </div>
        </Modal.Header>
        <Modal.Body>
          <MyClothesCreateModalBody
            handleClose={handleClose}
            getMyClothesData={getMyClothesData}
          />
        </Modal.Body>
      </Modal>
    </>
  );
}

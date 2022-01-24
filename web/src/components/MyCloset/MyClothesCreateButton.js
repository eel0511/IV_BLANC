import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

export default function MyClothesCreateButton() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <button type='button' className='btn btn-secondary' onClick={handleShow}>
        등록 하기
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>등록 하기</Modal.Title>
        </Modal.Header>
        <Modal.Body>등록 정보</Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

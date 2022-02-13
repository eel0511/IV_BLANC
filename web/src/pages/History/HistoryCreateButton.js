import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import HistoryCreateModalBody from './HistoryCreateModalBody';

export default function HistoryCreateButton() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <button
        type='button'
        className='btn btn-danger'
        style={{
          'background-color': '#ed6991',
          color: 'white',
          border: 'none',
        }}
        onClick={handleShow}
      >
        추억하기
      </button>

      <Modal
        size="xl"
        show={show}
        onHide={handleClose}
        aria-labelledby='contained-modal-title-vcenter'
        centered
      >
        <Modal.Header 
          closeButton
          style={{ backgroundColor: '#ed6991' , color: 'white'}}
          >
          <Modal.Title
            style={{ marginLeft: "42%", fontSize: '2.1rem' }}
          >추억 하기</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <HistoryCreateModalBody />
        </Modal.Body>
        
      </Modal>
    </>
  );
}

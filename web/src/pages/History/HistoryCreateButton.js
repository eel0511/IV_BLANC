import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import HistoryCreateModalBody from './HistoryCreateModalBody';

export default function HistoryCreateButton() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <button type='button' className='btn btn-primary' onClick={handleShow}>
        추억 하기
      </button>

      <Modal show={show} onHide={handleClose} aria-labelledby="contained-modal-title-vcenter"
      centered>
        <Modal.Header closeButton>
          <Modal.Title>추억 하기</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <HistoryCreateModalBody />
        </Modal.Body>
        <Modal.Footer>
          {/* <Button variant='danger' onClick={handleClose}>
            Close
          </Button> */}
        </Modal.Footer>
      </Modal>
    </>
  );
}

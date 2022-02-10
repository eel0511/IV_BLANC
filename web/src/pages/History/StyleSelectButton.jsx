import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import StyleSelectModalBody from './StyleSelectModalBody';

export default function StyleSelectButton() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <button
        type='button'
        className='btn'
        style={{
          'background-color': '#ed6991',
          'color': 'white'
        }}
        onClick={handleShow}
      >
        스타일 선택
      </button>

      <Modal show={show} onHide={handleClose} aria-labelledby="contained-modal-title-vcenter"
      centered>
        <Modal.Header closeButton>
          <Modal.Title>저장된 스타일룩</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <StyleSelectModalBody />
        </Modal.Body>
        <Modal.Footer>
          <Button variant='danger' onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

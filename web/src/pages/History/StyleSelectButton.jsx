import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import StyleSelectModalBody from './StyleSelectModalBody';

export default function StyleSelectButton({ getImg }) {
  const [show, setShow] = useState(false);
  const [styleLook, setStyleLook] = useState([]);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const getStyleLook = (styles) => {
    console.log(styles);
    setStyleLook(styles);
    getImg(styles);
    setShow(false);
  };
  return (
    <>
      <button
        type='button'
        className='btn'
        style={{
          'background-color': '#ed6991',
          color: 'white',
          marginTop: '30px',
        }}
        onClick={handleShow}
      >
        스타일 선택
      </button>

      <Modal
        show={show}
        onHide={handleClose}
        aria-labelledby='contained-modal-title-vcenter'
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>저장된 스타일룩</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <StyleSelectModalBody getStyleLook={getStyleLook} />
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
import React, { useState } from "react";
import "./MyCloset.css";
import { Modal, Button } from "react-bootstrap";

export default function MyClosetClothesItem({ clothesData } ) {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <div className="card h-100">
      <div className="card-body">
        <img
          src={require( `../../assets/${ clothesData.url }` )}
          alt={clothesData.clothesId}
          style={{'maxWidth': '100%', 'maxHeight': '100%'}}
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
          onClick={handleShow}
        />

        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>옷 상세정보 제목</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            옷 상세정보 보여주기
            <p>{clothesData.clothesId}</p>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
}
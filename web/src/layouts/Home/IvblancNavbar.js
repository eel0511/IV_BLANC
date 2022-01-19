import React from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';

export default function IvblancNavbar() {
  return (
    <>
      <Navbar bg="light" variant="light">
        <Container>
          <Navbar.Brand href="#1">
            <img 
              src="../../assets/logo2.png"
              width="30"
              height="30"
              className="d-inline-block align-top"
              alt="logo"
            />
          </Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link href="#1">내 옷장</Nav.Link>
            <Nav.Link href="#2">스타일링</Nav.Link>
            <Nav.Link href="#3">히스토리</Nav.Link>
            <Nav.Link href="#4">친구 옷장</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
    </>
  );
}
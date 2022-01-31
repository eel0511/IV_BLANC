import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import MyClosetSidebar from '../../layouts/MyCloset/MyClosetSidebar';

export default function MyStyle() {
  return (
    <>
      <h1>Mystyle</h1>
      <div className='MyStyleContanier'>
        <Container fluid='md'>
          <Row>
            <Col sm={4}>1of2</Col>
            <Col sm={8}>2of2</Col>
          </Row>
        </Container>
      </div>
    </>
  );
}

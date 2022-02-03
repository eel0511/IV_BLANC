import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import MyStyleTopbar from '../../layouts/MyStyle/MyStyleTopbar';

export default function MyStyle() {
  return (
    <>
      <h1>Mystyle</h1>
      <div className='MyStyleContanier'>
        <Container fluid='md'>
          <Row xs={1} md={2}>
            <Col sm={4}>
              <MyStyleTopbar />
            </Col>
            <Col sm={8}>아바타</Col>
            <Col sm={4}>선택한 이미지</Col>
          </Row>
        </Container>
      </div>
    </>
  );
}

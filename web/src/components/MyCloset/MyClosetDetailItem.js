import React from 'react';
import { ListGroup, Container, Row, Col } from 'react-bootstrap';

export default function DetailClothes({ item, value }) {
  return (
    <ListGroup.Item>
      <Container>
        <Row>
          <Col>{item} </Col>
          <Col xs={1}></Col>
          <Col>{value}</Col>
        </Row>
      </Container>
    </ListGroup.Item>
  );
}

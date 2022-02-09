import React from 'react';
import styled from 'styled-components';
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';
import { Container, Row, Col } from 'react-bootstrap';
import MyStyleTopbar from '../../layouts/MyStyle/MyStyleTopbar';
import home from '../../assets/home.png';

export default function MyStyle() {
  return (
    <Section>
      <ScrollToTop />
      <div className='MyCloset__Nav'>
        <Navbar />
      </div>
      <h1>Mystyle</h1>
      <div className='MyStyleContanier'>
        <MyStyleTopbar />
        {/* <Container fluid='md'>
          <Row xs={1} md={2}>
            <Col sm={4}>
              <MyStyleTopbar />
            </Col>
            <Col sm={8}>아바타</Col>
          </Row>
        </Container> */}
      </div>
    </Section>
  );
}

const Section = styled.section`
  // background-image: url(${home});
  background-size: cover;
  min-height: 100vh;
  background-repeat: no-repeat;
  background-position: center;
  position: relative;
`;

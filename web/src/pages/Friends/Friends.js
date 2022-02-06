import React from 'react';
import styled from "styled-components";
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';
import home from "../../assets/home.png";

export default function Friends() {
  return (
    <Section>
      <ScrollToTop />
      <Navbar />
        <h1>Friends</h1>
    </Section>
  );
}

const Section = styled.section`
  background-image: url(${home});
  background-size: cover;
  min-height: 100vh;
  background-repeat: no-repeat;
  background-position: center;
  position: relative;
`;
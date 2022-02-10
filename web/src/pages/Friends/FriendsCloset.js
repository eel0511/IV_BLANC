import React from 'react';
import home from "../../assets/home.png";
import styled from "styled-components";
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';

export default function FriendsCloset({ location }) {
  console.log(location)

  return(
    <Section>
      <ScrollToTop />
      <div className='MyCloset__Nav'>
        <Navbar />
      </div>
      <h1>FriendsCloset</h1>
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
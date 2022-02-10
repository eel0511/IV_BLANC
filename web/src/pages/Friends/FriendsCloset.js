import React from 'react';
import home from '../../assets/home.png';
import styled from 'styled-components';
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';
import Title from '../../components/Home/Title';
import FriendsClosetList from '../../layouts/Friends/FriendsClosetList';
import { useLocation } from 'react-router-dom';

export default function FriendsCloset() {
  let location = useLocation();
  console.log(location);

  return (
    <Section>
      <ScrollToTop />
      <div className='MyCloset__Nav'>
        <Navbar />
      </div>
      <Title value='FRIEND' />
      <FriendsClosetList />
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

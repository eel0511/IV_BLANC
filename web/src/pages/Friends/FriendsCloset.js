import React from 'react';
import home from '../../assets/home.png';
import styled from 'styled-components';
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';
import Title from '../../components/Home/Title';
import FriendStyleTopbar from '../../layouts/Friends/FriendClothesList';
import { useLocation } from 'react-router-dom';

export default function FriendsCloset() {
  let location = useLocation();
  const friendName = location.state.friendName;
  const friendEmail = location.state.friendEmail;

  return (
    <Section>
      <ScrollToTop />
      <div className='MyCloset__Nav'>
        <Navbar />
      </div>
      <Title value='FRIEND' />
      <FriendStyleTopbar friendName={friendName} friendEmail={friendEmail}/>
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

import React from 'react';
import FriendsList from '../../layouts/Friends/FriendsList';
import FriendsCreateButton from '../../components/Friends/FriendsCreateButton';
import Notice from '../../components/Friends/Notice';
import styled from "styled-components";
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';
import home from "../../assets/home.png";

export default function Friends() {
  return (
    <Section>
      <ScrollToTop />
      <Navbar />
        <div><FriendsList /></div>
        <footer>
          <Notice />
          <FriendsCreateButton />
        </footer>
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
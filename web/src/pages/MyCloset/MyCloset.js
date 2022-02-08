import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styled from "styled-components";
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';
import MyClosetClothes from '../../layouts/MyCloset/MyClosetClothes';
import MyClosetSidebar from '../../layouts/MyCloset/MyClosetSidebar';
import './MyCloset.css';
import home from "../../assets/home.png";

export default function MyCloset() {
  const [myClothes, setMyClothes] = useState([]);
  
  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const testAxios = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/clothes/all', 
      {
        headers: {
          'X-AUTH-TOKEN': `${token}`
        }
      })
      .then((response) => {
        console.log(response.data.data);
      });
  };

  useEffect(() => {
    testAxios();
  }, []);

  return (
    <Section>
      <ScrollToTop />
      <Navbar />
      <div className='MyClosetContainer'>
        <MyClosetSidebar />
        <MyClosetClothes clothesDatas={myClothes} />
      </div>
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
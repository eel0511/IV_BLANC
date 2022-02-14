import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styled from 'styled-components';
import Navbar from '../../components/Navbar';
import ScrollToTop from '../../components/ScrollToTop';
import MyClosetClothes from '../../layouts/MyCloset/MyClosetClothes';
import MyClosetSidebar from '../../layouts/MyCloset/MyClosetSidebar';
import './MyCloset.css';
import home from '../../assets/home.png';

export default function MyCloset() {
  const [myClothes, setMyClothes] = useState([
    {
      createDate: '2022-02-08T15:51:09',
      updateDate: '2022-02-08T15:51:09',
      clothesId: 62,
      category: 21,
      color: '#000000',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/b8c70839-8ded-4dd8-b198-e5044ebbfe32.jfif',
      favorite: 0,
      size: 100,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:53:33',
      updateDate: '2022-02-08T17:37:18',
      clothesId: 63,
      category: 40,
      color: '#FFFFFF',
      material: 16,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/55f53847-bed5-4a8b-a474-72367449d31d.jfif',
      favorite: 1,
      size: 260,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:54:41',
      updateDate: '2022-02-08T15:54:41',
      clothesId: 64,
      category: 30,
      color: '#F6EFDF',
      material: 4,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/65789b82-25d5-4c4b-8998-38fd2a5d6553.jfif',
      favorite: 0,
      size: 100,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:03',
      updateDate: '2022-02-08T15:55:03',
      clothesId: 65,
      category: 60,
      color: '#C14D49',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/49f9a751-f2b3-4163-973e-3dbe0e756ce3.jfif',
      favorite: 0,
      size: 95,
      season: 1,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:31',
      updateDate: '2022-02-08T15:55:31',
      clothesId: 66,
      category: 50,
      color: '#27476F',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/11392b62-bff2-43b0-95d1-d810f18d9b87.jfif',
      favorite: 0,
      size: 95,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T18:59:42',
      updateDate: '2022-02-08T18:59:42',
      clothesId: 69,
      category: 11,
      color: '#27476F',
      material: 8,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/ab47070f-e237-4e5d-8044-b074d53aa4fe.png',
      favorite: 0,
      size: 1,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T21:49:24',
      updateDate: '2022-02-08T21:49:24',
      clothesId: 73,
      category: 10,
      color: '#ABAEB6',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/45e3779f-5878-4106-a422-ab4719acf55d.jfif',
      favorite: 0,
      size: 95,
      season: 2,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
  ]);
  const [filterMyClothes, setFilterMyClothes] = useState([
    {
      createDate: '2022-02-08T15:51:09',
      updateDate: '2022-02-08T15:51:09',
      clothesId: 62,
      category: 21,
      color: '#000000',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/b8c70839-8ded-4dd8-b198-e5044ebbfe32.jfif',
      favorite: 0,
      size: 100,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:53:33',
      updateDate: '2022-02-08T17:37:18',
      clothesId: 63,
      category: 40,
      color: '#FFFFFF',
      material: 16,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/55f53847-bed5-4a8b-a474-72367449d31d.jfif',
      favorite: 1,
      size: 260,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:54:41',
      updateDate: '2022-02-08T15:54:41',
      clothesId: 64,
      category: 30,
      color: '#F6EFDF',
      material: 4,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/65789b82-25d5-4c4b-8998-38fd2a5d6553.jfif',
      favorite: 0,
      size: 100,
      season: 4,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:03',
      updateDate: '2022-02-08T15:55:03',
      clothesId: 65,
      category: 60,
      color: '#C14D49',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/49f9a751-f2b3-4163-973e-3dbe0e756ce3.jfif',
      favorite: 0,
      size: 95,
      season: 1,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T15:55:31',
      updateDate: '2022-02-08T15:55:31',
      clothesId: 66,
      category: 50,
      color: '#27476F',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/11392b62-bff2-43b0-95d1-d810f18d9b87.jfif',
      favorite: 0,
      size: 95,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T18:59:42',
      updateDate: '2022-02-08T18:59:42',
      clothesId: 69,
      category: 11,
      color: '#27476F',
      material: 8,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/ab47070f-e237-4e5d-8044-b074d53aa4fe.png',
      favorite: 0,
      size: 1,
      season: 3,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
    {
      createDate: '2022-02-08T21:49:24',
      updateDate: '2022-02-08T21:49:24',
      clothesId: 73,
      category: 10,
      color: '#ABAEB6',
      material: 1,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/45e3779f-5878-4106-a422-ab4719acf55d.jfif',
      favorite: 0,
      size: 95,
      season: 2,
      count: 0,
      likePoint: 0,
      dislikePoint: 0,
      userId: 1,
    },
  ]);

  const getFilterMyclothes = (myclothes) => {
    setFilterMyClothes(myclothes);
  };

  const token = localStorage.getItem('JWT');
  // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const getMyClothesData = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/clothes/all', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        setMyClothes(response.data.data);
        setFilterMyClothes(response.data.data);
      });
  };

  useEffect(() => {
    getMyClothesData();
  }, []);

  return (
    <Section>
      <ScrollToTop />
      <div className='MyCloset__Nav'>
        <Navbar />
      </div>
      <div className='MyClosetContainer'>
        <MyClosetSidebar
          clothesDatas={myClothes}
          getFilterMyclothes={getFilterMyclothes}
        />
        <MyClosetClothes
          clothesDatas={filterMyClothes}
          getMyClothesData={getMyClothesData}
        />
      </div>
    </Section>
  );
}

const Section = styled.section`
  // background-image: url(${home});
  background-size: cover;
  min-height: 80vh;
  background-repeat: no-repeat;
  background-position: center;
  position: relative;
`;

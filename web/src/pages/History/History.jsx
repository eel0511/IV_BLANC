import React , { useState, useEffect } from 'react';
import styled from "styled-components";
import ScrollToTop from '../../components/ScrollToTop';
import Navbar from '../../components/Navbar';
import home from "../../assets/home.png";
import axios from 'axios';
import pricing1 from "../../assets/pricing1.png";
import pricing2 from "../../assets/pricing2.png";
import FlipCard from "./FlipCard";
import HistoryCreateButton from './HistoryCreateButton';
import Title from '../../components/Home/Title';

export default function History() {
  const [myHistories, setmyHistories] = useState([]);

  const token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const getmyHistoriesData = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/history/find/all', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        setmyHistories(response.data.data);
      });
  };

  // useEffect(() => {
  //   getmyHistoriesData();
  // }, []);
  return (
      <Section>
      <ScrollToTop />
      <div className='MyCloset__Nav'>
        <Navbar />
      </div>
      <Title value="HISTORY" />
      <div className="background">
        <img src={pricing1} alt="background" className="bg1" />
        <img src={pricing2} alt="background" className="bg2" />
      </div>
      <div style={{ 'margin-top': '1rem' }}>
      <HistoryCreateButton/>
      </div>
      <StyledHistory>
      <div className="container">
      <div className="overlay-background" />
      <div className="overlay-border" />
      <div className="row h-100">
        <div className="col d-flex flex-wrap justify-content-around align-items-center">
          {myHistories && myHistories.map((myHistory) => (
            <FlipCard key={myHistory.historyId} myHistory={myHistory} />
          ))}
        </div>
      </div>

    </div>
    </StyledHistory>
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

const StyledHistory = styled.div`
  display: grid;

  *,
  *::after,
  *::before {
    display: flex;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
  }
  body {
    position: relative;
    font-family: 'Ubuntu', sans-serif;
    background-color: #000;

    .container {
      height: 100%;
    }

    .overlay-background {
      position: fixed;
      top: 0;
      right:0;
      bottom: 0;
      left: 0;
      background-size: cover;
      filter: blur(2px) contrast(150%) opacity(25%);
  }

  .overlay-border {
    position: fixed;
    top: 50px;
    right: 50px;
    bottom: 50px;
    left: 50px;
    
  }

}
.flip-card-outer {
  width: 300px;
  height: 400px;
  perspective: 1000px;
  margin: 25px 0;
  

  /* &.hover .flip-card-inner {
    transform: rotateY(180deg);
  } */

  .flip-card-inner {
    z-index: 1;
    backface-visibility: visible;
    transform-style: preserve-3d;
    transition: .5s linear .1s;
    position: relative;
    width: inherit;
    height: inherit;
    

    &.showBack {
      transform: rotateY(180deg);
    }
  
    .card {
      position: absolute;
      top: 0;
      left: 0;
      overflow: hidden;
      width: 100%;
      height: 100%;
      border: 0;
      
  
      &.front {
        z-index: 10;
        transform: rotateY(0);
        background-repeat: no-repeat;
        background-size: cover;
        color: #fff;


        p {
          display: flex;
          align-items: flex-end;
          justify-content: center;
          background: #ed6991;
          color : rgba(255, 255, 255, 0.7);
        }

        .icon {
          position: absolute;
          bottom: 5px;
          right: 5px;
          transform: rotateY(180deg);
        }
      }
  
      &.back {
        z-index: 9;
        transform: rotateY(180deg);
        background-color: #fff;

        p {
          margin-bottom: 0;
        }

        .brand {
          font-size: 2rem;
          font-weight: bold;
        }

        .name {
          font-size: 1.4rem;
        }

        .price {
          font-size: 1.6rem;
          font-weight: bold;
        }

        .icon {
          position: absolute;
          bottom: 5px;
          right: 5px;
          transform: rotateY(180deg);
        }
      }
    }
  }
}
`;


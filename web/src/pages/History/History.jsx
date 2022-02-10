import React , { useState, useEffect } from 'react';
import styled from "styled-components";
import axios from 'axios';
import ScrollToTop from '../../components/ScrollToTop';
import Navbar from '../../components/Navbar';
import home from "../../assets/home.png";
import "bootstrap/dist/css/bootstrap.min.css";
import fimg1 from "../../assets/images/2.jfif";
import bimg1 from "../../assets/outfit/1.jfif";
import fimg2 from "../../assets/images/3.jfif";
import bimg2 from "../../assets/outfit/2.jfif";
import fimg3 from "../../assets/images/4.jfif";
import bimg3 from "../../assets/outfit/3.jfif";
import fimg4 from "../../assets/images/6.jfif";
import bimg4 from "../../assets/outfit/4.jfif";
import ReactCardFlip from "react-card-flip";
import FlipCard from "./FlipCard";
import FlipCard1 from './FlipCard1';
import HistoryCreateButton from './HistoryCreateButton';

const cards = [
  {
    date: "2022-02-10",
    variant: "focus",
    historyId: 1,
    styleUrl: bimg1,
    subject: "제목 1",
    text: "내용 1",
    photos: {url : fimg1},
  },
  {
    date: "2022-02-10",
    variant: "focus",
    historyId: 2,
    styleUrl: bimg2,
    subject: "제목 1",
    text: "내용 1",
    photos: {url : fimg2},
  },
  {
    date: "2022-02-10",
    variant: "focus",
    historyId: 3,
    styleUrl: bimg3,
    subject: "제목 1",
    text: "내용 1",
    photos: {url : fimg3},
  },
  {
    date: "2022-02-10",
    variant: "focus",
    historyId: 4,
    styleUrl: bimg4,
    subject: "제목 1",
    text: "내용 1",
    photos: {url : fimg4},
  },
];

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

  useEffect(() => {
    getmyHistoriesData();
  }, []);
  return (
      <Section>
      <ScrollToTop />
      <Navbar />
      <>
      <HistoryCreateButton />
      </>
      <StyledHistory>
      <div className="container">
      <div className="overlay-background" />
      <div className="overlay-border" />
      <div className="row h-100">
        <div className="col d-flex flex-wrap justify-content-around align-items-center">
          {cards.map((myHistory) => (
            // <FlipCard key={myHistory.historyId} myHistory={myHistory} />
            <FlipCard1 key={myHistory.historyId} myHistory={myHistory} />
          ))}
        </div>
      </div>

    </div>
    </StyledHistory>
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

    .card {
    position: absolute
    top: 50%
    left: 50%
    height: 400px
    width: 300px
    transform: translate(-50%, -50%)
    transform-style: preserve-3d
    perspective: 600px
    transition: .5s
    }
    &:hover .card-front{
        transform: rotateX(-180deg)
    }

    &:hover .card-back{
        transform: rotateX(0deg)
    }
.card-front{
    height: 100%
    width: 100%
    background-position: 50% 50%
    background-size: cover
    position: absolute
    top: 0
    left: 0
    background-color: #000000
    backface-visibility: hidden
    transform: rotateX(0deg)
    transition: .5s
    }
.card-back{
    height: 100%
    width: 100%
    position: absolute
    top: 0
    left: 0
    background-color: #000000
    backface-visibility: hidden
    transform: rotateX(180deg)
    transition: .5s
}

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
  

  &.flip-card-inner .showBack {
    transform: rotateY(180deg);
  }

  .flip-card-inner {
    z-index: 10;
    backface-visibility: visible;
    /* -webkit-backface-visibility: hidden; */
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
        z-index: 9;
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
        z-index: 8;
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


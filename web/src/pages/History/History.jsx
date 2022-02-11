import React , { useState, useEffect } from 'react';
import styled from "styled-components";
import ScrollToTop from '../../components/ScrollToTop';
import Navbar from '../../components/Navbar';
import home from "../../assets/home.png";
import axios from 'axios';
import "bootstrap/dist/css/bootstrap.min.css";
import fimg1 from "../../assets/images/2.jfif";
import bimg1 from "../../assets/outfit/1.jfif";
import fimg2 from "../../assets/images/3.jfif";
import bimg2 from "../../assets/outfit/2.jfif";
import fimg3 from "../../assets/images/4.jfif";
import bimg3 from "../../assets/outfit/3.jfif";
import fimg4 from "../../assets/images/6.jfif";
import bimg4 from "../../assets/outfit/4.jfif";
import fimg5 from "../../assets/images/11.jfif";
import bimg5 from "../../assets/outfit/5.jfif";
import fimg6 from "../../assets/images/12.png";
import bimg6 from "../../assets/outfit/6.jfif";
import fimg7 from "../../assets/images/13.jfif";
import bimg7 from "../../assets/outfit/7.jfif";
import fimg8 from "../../assets/images/21.png";
import bimg8 from "../../assets/outfit/8.jfif";
import fimg9 from "../../assets/images/22.jfif";
import bimg9 from "../../assets/outfit/9.jfif";
import fimg10 from "../../assets/images/25.jfif";
import bimg10 from "../../assets/outfit/16.jfif";
import fimg11 from "../../assets/images/26.jfif";
import bimg11 from "../../assets/outfit/18.jfif";
import fimg12 from "../../assets/images/27.png";
import bimg12 from "../../assets/outfit/24.jfif";
import fimg13 from "../../assets/images/29.jfif";
import bimg13 from "../../assets/outfit/30.jfif";
import fimg14 from "../../assets/images/37.jfif";
import bimg14 from "../../assets/outfit/36.jfif";
import fimg15 from "../../assets/images/40.png";
import bimg15 from "../../assets/outfit/38.jfif";
import fimg16 from "../../assets/images/41.jfif";
import bimg16 from "../../assets/outfit/42.jfif";

import FlipCard from "./FlipCard";
import HistoryCreateButton from './HistoryCreateButton';
import Title from '../../components/Home/Title';

const cards = [
  {
    id: "1",
    variant: "focus",
    front: { title: "Focus", background: fimg1 },
    back: {
      image: bimg1,
      title: "title 1",
      comment: "comment 1",
      date: "2022-02-07"
    }
  },
  {
    id: "2",
    variant: "focus",
    front: { title: "Focus", background: fimg2 },
    back: {
      image: bimg2,
      title: "title 2",
      comment: "comment 2",
      date: "2022-02-07"
    }
  },
  {
    id: "3",
    variant: "focus",
    front: { title: "Focus", background: fimg3 },
    back: {
      image: bimg3,
      title: "title 3",
      comment: "comment 3",
      date: "2022-02-07"
    }
  },
  {
    id: "4",
    variant: "focus",
    front: { title: "Focus", background: fimg4 },
    back: {
      image: bimg4,
      title: "title 4",
      comment: "comment 4",
      date: "2022-02-07"
    }
  },
  {
    id: "5",
    variant: "focus",
    front: { title: "Focus", background: fimg5 },
    back: {
      image: bimg5,
      title: "title 5",
      comment: "comment 5",
      date: "2022-02-07"
    }
  },
  {
    id: "6",
    variant: "focus",
    front: { title: "Focus", background: fimg6 },
    back: {
      image: bimg6,
      title: "title 6",
      comment: "comment 6",
      date: "2022-02-07"
    }
  },
  {
    id: "7",
    variant: "focus",
    front: { title: "Focus", background: fimg7 },
    back: {
      image: bimg7,
      title: "title 7",
      comment: "comment 7",
      date: "2022-02-07"
    }
  },
  {
    id: "8",
    variant: "focus",
    front: { title: "Focus", background: fimg8 },
    back: {
      image: bimg8,
      title: "title 8",
      comment: "comment 8",
      date: "2022-02-07"
    }
  },
  {
    id: "9",
    variant: "focus",
    front: { title: "Focus", background: fimg9 },
    back: {
      image: bimg9,
      title: "title 9",
      comment: "comment 9",
      date: "2022-02-07"
    }
  },
  {
    id: "10",
    variant: "focus",
    front: { title: "Focus", background: fimg10 },
    back: {
      image: bimg10,
      title: "title 10",
      comment: "comment 10",
      date: "2022-02-07"
    }
  },
  {
    id: "11",
    variant: "focus",
    front: { title: "Focus", background: fimg11 },
    back: {
      image: bimg11,
      title: "title 11",
      comment: "comment 11",
      date: "2022-02-07"
    }
  },
  {
    id: "12",
    variant: "focus",
    front: { title: "Focus", background: fimg12 },
    back: {
      image: bimg12,
      title: "title 12",
      comment: "comment 12",
      date: "2022-02-07"
    }
  },
  {
    id: "13",
    variant: "focus",
    front: { title: "Focus", background: fimg13 },
    back: {
      image: bimg13,
      title: "title 13",
      comment: "comment 13",
      date: "2022-02-07"
    }
  },
  {
    id: "14",
    variant: "focus",
    front: { title: "Focus", background: fimg14 },
    back: {
      image: bimg14,
      title: "title 14",
      comment: "comment 14",
      date: "2022-02-07"
    }
  },
  {
    id: "15",
    variant: "focus",
    front: { title: "Focus", background: fimg15 },
    back: {
      image: bimg15,
      title: "title 15",
      comment: "comment 15",
      date: "2022-02-07"
    }
  },
  {
    id: "16",
    variant: "focus",
    front: { title: "Focus", background: fimg16 },
    back: {
      image: bimg16,
      title: "title 16",
      comment: "comment 16",
      date: "2022-02-07"
    }
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
      <div className='MyCloset__Nav'>
        <Navbar />
      </div>
      <Title value="HISTORY" />
      <div style={{ 'margin-top': '1rem' }}>
      <HistoryCreateButton/>
      </div>
      <StyledHistory>
      <div className="container">
      <div className="overlay-background" />
      <div className="overlay-border" />
      <div className="row h-100">
        <div className="col d-flex flex-wrap justify-content-around align-items-center">
          {cards.map((card) => (
            <FlipCard key={card.id} card={card} />
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
  

  &.hover:hover .flip-card-inner {
    transform: rotateY(180deg);
  }

  .flip-card-inner {
    z-index: 10;
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


import React from 'react';
import Navbar from '../Navbar';
import styled from 'styled-components';
import home from '../../assets/home.png';
import play from '../../assets/play.png';
import { motion } from 'framer-motion';
import { homeAnimation, homeInfoAnimation } from '../../animations';

export default function HomeMain() {
  return (
    <Section id='home'>
      <Navbar />
      <motion.div
        variants={homeAnimation}
        transition={{ delay: 0.3, duration: 0.6, type: 'tween' }}
        className='home'
      >
        <div className='content'>
          <div className='title'>
            <h1>Discover your Own Style</h1>
            <p>
              공유 옷장과 스타일링을 통해 차별화된 자신의 스타일을 만들어보세요.
            </p>
          </div>
          <div className='subTitle'></div>

          <div className='subTitle'>
            <a href='https://storage.googleapis.com/iv-blanc.appspot.com/app-debug.apk'>
              <button className='download' type='button'>
                App Download
              </button>
            </a>
            {/* <img src={play} alt="Play Button" /> */}
          </div>
        </div>
      </motion.div>
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
  .home {
    height: 80%;
    .content {
      height: 100%;
      color: white;
      display: flex;
      flex-direction: column;
      align-items: flex-center;
      gap: 1.2rem;
      margin-top: 6rem;
      width: 100%;
      .title {
        h1 {
          color: #ed6991;
          font-size: 5rem;
          line-height: 5.3rem;
          margin-bottom: 2rem;
        }
        p {
          font-size: 2.2rem;
          margin-bottom: 2rem;
        }
      }
      .subTitle {
        img {
        }
        p {
          width: 100%;
          font-size: 1.2rem;
          margin-bottom: 2rem;
        }
      }
    }
  }

  @keyframes ring {
    0% {
      width: 30px;
      height: 30px;
      opacity: 1;
    }
    100% {
      width: 300px;
      height: 300px;
      opacity: 0;
    }
  }

  .download {
    position: relative;
    border: none;
    min-width: 200px;
    min-height: 50px;
    /* background: linear-gradient(
      90deg,
      rgba(129, 230, 217, 1) 0%,
      rgba(79, 209, 197, 1) 100%
    ); */
    background: #ed6991;
    border-radius: 1000px;
    color: white;
    cursor: pointer;
    /* box-shadow: 12px 12px 24px rgba(79, 209, 197, 0.64); */
    font-weight: 700;
    transition: 0.3s;
  }
  .download:hover {
    transform: scale(1.2);
  }

  .download:hover::after {
    content: '';
    width: 30px;
    height: 30px;
    border-radius: 100%;
    border: 6px solid #ed6991;
    position: absolute;
    z-index: -1;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    animation: ring 1.5s infinite;
  }

  @media screen and (min-width: 280px) and (max-width: 1080px) {
    .home {
      .content {
        padding-left: 2rem;
        width: 100%;
        margin-bottom: 2rem;
        .title {
          h1 {
            font-size: 4rem;
            line-height: 4rem;
          }
        }
      }
    }
  }
`;

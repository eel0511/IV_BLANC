import React from "react";
import Navbar from "../Navbar";
import styled from "styled-components";
import home from "../../assets/home.png";
import play from "../../assets/play.png";
import { motion } from "framer-motion";
import { homeAnimation, homeInfoAnimation } from "../../animations";

export default function HomeMain() {
  return (
    <Section id="home">
      <Navbar />
      <motion.div
        variants={homeAnimation}
        transition={{ delay: 0.3, duration: 0.6, type: "tween" }}
        className="home"
      >
        <div className="content">
          <div className="title">
            <h1>Discover your Own Style</h1>
            <p>
            공유 옷장과 스타일링을 통해 
            차별화된 자신의 스타일을 만들어보세요.
            </p>
          </div>
          <div className="subTitle">
            
          </div>
          
          <div className="subTitle">
          <img src={play} alt="Play Button" />
            <p>
            Get started
            </p>
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
      align-items: flex-start;
      gap: 1.2rem;
      padding-left: 14rem;
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
        padding-left: 28rem;
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
 
  }

  @media screen and (min-width: 280px) and (max-width: 1080px) {
    .home {
      .content {
        width: 100%;
        height: 100vh;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
            -ms-flex-align: center;
                align-items: center;
        -webkit-box-pack: center;
            -ms-flex-pack: center;
          justify-content: center;
        .title {
          h1 {
            font-size: 4rem;
            line-height: 4rem;
          }
        }
      }
    }
    }
  }
`;

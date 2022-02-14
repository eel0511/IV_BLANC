import React from "react";
import styled from "styled-components";
import Title from "./Title";

import img20 from "../../assets/images/20.jfif";
import img12 from "../../assets/images/12.png";
import img23 from "../../assets/images/23.jfif";
import img4 from "../../assets/images/4.jfif";
import img21 from "../../assets/images/21.png";
import img6 from "../../assets/images/6.jfif";
import img28 from "../../assets/images/28.png";
import img40 from "../../assets/images/40.png";

import loadmore from "../../assets/loadmore.png";
import portfolio1 from "../../assets/portfolio1.png";
import portfolio2 from "../../assets/portfolio2.png";
import { motion } from "framer-motion";
import { useScroll } from "../useScroll";
import { portfolioAnimation } from "../../animations";
function Portfolio() {
  const [element, controls] = useScroll();
  return (
    <Section id="portfolio" ref={element}>
      <Title value="own style" />
      <div className="background">
        <img src={portfolio1} alt="Design" className="design1" />
        <img src={portfolio2} alt="Design" className="design2" />
      </div>
      <div className="portfolio__title">
        <p>Pick & Share</p>
        <h2>Pick your own Style by Shared Closet</h2>
      </div>
      <div className="grid">
        <motion.div
          className="child-one grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          whileInView={{ opacity: 1 }}
        >
          <img src={img12} alt="placeholder"/>
        </motion.div>
        <motion.div
          className="child-two grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          viewport={{ once: true }}
        >
          <img src={img20} alt="placeholder" />
        </motion.div>
        <motion.div
          className="child-three grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          viewport={{ once: true }}
        >
          <img src={img23} alt="placeholder" />
        </motion.div>
        <motion.div
          className="child-four grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          viewport={{ once: true }}
        >
          <img src={img4} alt="placeholder" />
        </motion.div>
        <motion.div
          className="child-five grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          viewport={{ once: true }}
        >
          <img src={img21} alt="placeholder" />
        </motion.div>
        <motion.div
          className="child-six grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          viewport={{ once: true }}
        >
          <img src={img6} alt="placeholder" />
        </motion.div>
        <motion.div
          className="child-seven grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          viewport={{ once: true }}
        >
          <img src={img28} alt="placeholder" />
        </motion.div>
        <motion.div
          className="child-eight grid-box"
          variants={portfolioAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          viewport={{ once: true }}
        >
          <img src={img40} alt="placeholder" />
        </motion.div>
      </div>
      <div className="portfolio-more">
        <span>Load More</span>
        <img src={loadmore} alt="Load More" />
      </div>
    </Section>
  );
}

const Section = styled.section`
  min-height: 100vh;
  padding: 2rem 0;
  background-color: var(--secondary-color);
  .background {
    position: relative;
    .design1 {
      position: absolute;
      z-index: 1;
      right: 8%;
      top: 0;
    }
    .design2 {
      position: absolute;
      z-index: 1;
      left: 0;
      top: 30rem;
      height: 40rem;
    }
  }
  .sideTitle {
    z-index: 2;
    h1 {
      color: white;
    }
  }
  .portfolio__title {
    margin: 6rem 15rem;
    p {
      color: var(--primary-color);
      text-transform: uppercase;
      letter-spacing: 0.2rem;
      font-size: 2rem;
    }
    h2 {
      color: white;
      font-size: 2rem;
    }
  }
  .grid {
    padding: 0 15rem;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-template-areas:
      "one one two two"
      "one one three four"
      "five six seven seven"
      "eight six seven seven";
    .grid-box {
      height: 10rem;
      width: 100%;
      background-color: var(--primary-color);
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      img {
        transition: 0.4s ease-in-out;
      }
      &:hover {
        img {
          transform: scale(1.2);
        }
      }
      &:nth-of-type(1) {
        grid-area: one;
        height: 100%;
        background-color: #8860e66a;
        z-index: 10;
        img {
          width: fit-content;
          height: 500px;
        }
      }
      &:nth-of-type(2) {
        z-index: 10;
        grid-area: two;
        height: 100%;
        background-color: #662d91ca;
        img {
          width: fit-content;
          height: 250px;
        }
      }
      &:nth-of-type(3) {
        grid-area: three;
        height: 100%;
        background-color: #8860e6b0;
        img {
          width: fit-content;
          height: 200px;
        }
      }
      &:nth-of-type(4) {
        grid-area: four;
        height: 100%;
        img {
          width: fit-content;
          height: 200px;
        }
      }
      &:nth-of-type(5) {
        z-index: 10;
        grid-area: five;
        height: 100%;
        background-color: #8860e6b0;
        img {
          width: fit-content;
          height: 200px;
        }
      }
      &:nth-of-type(6) {
        grid-area: six;
        height: 100%;
        background-color: #662d91ca;
        img {
          width: fit-content;
          height: 400px;
        }
      }
      &:nth-of-type(7) {
        grid-area: seven;
        background-color: #8860e66a;
        height: 100%;
        img {
          width: fit-content;
          height: 500px;
        }
      }
      &:nth-of-type(8) {
        z-index: 10;
        grid-area: eight;
        height: 100%;
        img {
          width: fit-content;
          height: 200px;
        }
      }
    }
  }
  .portfolio-more {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 1rem;
    margin: 4rem 0;
    span {
      color: white;
      letter-spacing: 0.1rem;
      text-transform: uppercase;
    }
  }
  @media screen and (min-width: 280px) and (max-width: 1080px) {
    .background {
      display: none;
    }
    .portfolio__title {
      margin: 0;
      padding: 0 2rem;
      text-align: center;
      h2 {
        font-size: 1.5rem;
      }
    }
    .grid {
      padding: 2rem 4rem;
      grid-template-columns: 1fr;
      grid-template-areas:
        "one"
        "two"
        "three"
        "four"
        "five"
        "six"
        "seven"
        "eight";
      .grid-box {
        height: 100rem !important;
      }
    }
  }
`;

export default Portfolio;

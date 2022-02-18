import React from "react";
import styled from "styled-components";
import Title from "./Title";
import play from "../../assets/play.png";
import { motion } from "framer-motion";
import { useScroll } from "../useScroll";
import { blogsAnimations } from "../../animations";

export default function Blog() {
  const [element, controls] = useScroll();
  const blogsData = [
    {
      title: "2022-02-17",
      type: "IV BLANC와 함께",
      img: "1.jfif",
      description:
        "추억하다!",
    },
    {
      title: "2022-02-18",
      type: "소중한 기억일 수록",
      img: "2.jfif",
      description:
        "잊혀지기 쉽습니다!",
    },
    {
      title: "2022-02-19",
      type: "히스토리를 통해",
      img: "3.jfif",
      description:
        "나의 하루를 기록하세요!",
    },
  ];
  return (
    <Section id="blog" ref={element}>
      <Title value="History" />
      <div className="decoration"></div>
      <div className="blogs">
        {blogsData.map(({ title, type, description, img }) => {
          return (
            <motion.div
              className="blog"
              key={title}
              variants={blogsAnimations}
              animate={controls}
              transition={{
                delay: 0.03,
                type: "tween",
                duration: 0.8,
              }}
              whileInView={{ opacity: 1, y: 0 }}
            >
              <div className="image">
                <img src={require(`../../assets/outfit/${img}`)} alt="Placeholder" />
              </div>
              <div className="title">
                <h3>{title}</h3>
              </div>
              <span className="type">{type}</span>
              <div className="description">
                <p>{description}</p>
              </div>
              <div className="more">
                <img src={play} alt="play" />
                <span>Read more</span>
              </div>
            </motion.div>
          );
        })}
      </div>
    </Section>
  );
}

const Section = styled.section`
  min-height: 100vh;
  position: relative;
  .decoration {
    position: absolute;
    height: 20rem;
    width: 70vw;
    border: 0.5rem solid var(--secondary-color);
    left: 15%;
    top: -2rem;
  }
  .blogs {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 3rem;
    margin: 10rem 20rem;
    .blog {
      display: flex;
      flex-direction: column;
      gap: 1rem;
      .image {
        height: 18rem;
        margin-bottom: 10px;
        background-color: #a686f0af;
        display: flex;
        justify-content: center;
        align-items: center;
        img {
          width: fit-content;
          height: 250px;
        }
      }
      .title {
        h3 {
          color: var(--secondary-color);
          font-size: 2rem;
        }
      }
      .type {
        color: var(--primary-color);
        font-weight: bolder;
        text-transform: uppercase;
      }
      .description {
        height: 2rem;
        color: var(--primary-color);
      }
      .more {
        margin-top: 50px;
        margin-left: 40px;
        display: flex;
        align-items: center;
        gap: 1rem;
        cursor: pointer;
        span {
          letter-spacing: 0.1rem;
          text-transform: uppercase;
          color: var(--primary-color);
        }
      }
    }
  }
  @media screen and (min-width: 280px) and (max-width: 1080px) {
    margin: 2rem 0;
    .decoration {
      display: none;
    }
    .blogs {
      padding: 0 2rem;
      grid-template-columns: 1fr;
      margin: 0 1rem;
    }
  }
`;

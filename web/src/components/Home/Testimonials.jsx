import React, { useState } from "react";
import testimonial1 from "../../assets/testimonial1.png";
import testimonial2 from "../../assets/testimonial2.png";
import { motion } from "framer-motion";
import { useScroll } from "../useScroll";
import { testimonialsAnimation } from "../../animations";
import styled from "styled-components";
import Title from "./Title";


export default function Testimonials() {
  const [element, controls] = useScroll();

  const [selected, setSelected] = useState(0);
  const testimonials = [
    {
      designation: "Leader / Mobile",
      name: "김민수",
      img: "김민수.jpg",
      review:
        "“나는 코딩한다. 고로 존재한다.”",
    },
    {
      designation: "Backend / Mobile / AI",
      name: "이수형",
      img: "이수형.jpg",
      review:
        "“True에 느낌표 하나 찍으면 False”",
    },
    {
      designation: "Backend / Mobile",
      name: "김현수",
      img: "김현수.jpg",
      review:
        "“아는 코드도 다시 보자.” ",
    },
    {
      designation: "Frontend",
      name: "이인섭",
      img: "이인섭.jpg",
      review:
        "“높이 나는 새가 가장 늦게 퇴근한다..”",
    },
    {
      designation: "Frontend / UCC",
      name: "방기진",
      img: "방기진.jpg",
      review:
        "“아는 것이 힘들다.”",
    },
    {
      designation: "Frontend",
      name: "장영윤",
      img: "장영윤.jpg",
      review:
        "“개발 업무는 1%의 영감과 99%의 노가다로 이루어진다”",
    },
  ];
  return (
    <Section ref={element}>
      <Title value="" />
      <div className="background">
        <img src={testimonial1} alt="background design" className="design1" />
        <img src={testimonial2} alt="background design" className="design2" />
      </div>
      <div className="container" >
        <motion.div
          className="testimonials"
          variants={testimonialsAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
          whileInView={{ opacity: 1 }}
        >
          {testimonials.map((testimonial, index) => {
            return (
              <div
                className={`testimonial ${
                  selected === index ? "active" : "hidden"
                }`}
                key={index}
              >
                <div className="image">
                  <div className="circle1"></div>
                  <div className="circle2">
                    <img src={require(`../../assets/member/${testimonial.img}`)} alt="placeholder" />
                  </div>
                </div>
                <div className="title-container">
                  <span className="designation">{testimonial.designation}</span>
                  <h3 className="title">{testimonial.name}</h3>
                </div>
                <p className="description">{testimonial.review}</p>
              </div>
            );
          })}
        </motion.div>
        <motion.div
          className="controls"
          variants={testimonialsAnimation}
          animate={controls}
          transition={{
            delay: 0.03,
            type: "tween",
            duration: 0.8,
          }}
        >
          <button
            className={selected === 0 ? "active" : ""}
            onClick={() => {
              setSelected(0);
            }}
          ></button>
          <button
            className={selected === 1 ? "active" : ""}
            onClick={() => setSelected(1)}
          ></button>
          <button
            className={selected === 2 ? "active" : ""}
            onClick={() => setSelected(2)}
          ></button>
          <button
            className={selected === 3 ? "active" : ""}
            onClick={() => setSelected(3)}
          ></button>
          <button
            className={selected === 4 ? "active" : ""}
            onClick={() => setSelected(4)}
          ></button>
          <button
            className={selected === 5 ? "active" : ""}
            onClick={() => setSelected(5)}
          ></button>
        </motion.div>
      </div>
    </Section>
  );
}

const Section = styled.section`
  overflow: hidden;
  .title {
    z-index:-2;
  }
  .background {
    position: relative;
    .design1 {
      position: absolute;
      left: 0rem;
      height: 35rem;
      top: 10rem;
      z-index: 1;
    }
    .design2 {
      position: absolute;
      right: 0;
      z-index: 2;

    }
  }
  .container {
    min-height: 100vh;
    min-width: 100vw;
    background-color: var(--primary-color);
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    gap: 2rem;

    .testimonials {
      display: flex;
      gap: 1rem;
      text-align: center;
      justify-content: center;
      width: 30%;

      .testimonial {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
        .image {
          position: relative;
          .circle1 {
            position: absolute;
            height: 15rem;
            width: 15rem;
            border: 0.2rem solid white;
            border-radius: 10rem;
          }
          .circle2 {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 15rem;
            width: 15rem;
            border-radius: 10rem;
            background-color: #8860e6b0;
            img {
              height: 15rem;
              width: 15rem;
              border-radius: 10rem;
            }
          }
        }
      }
      .hidden {
        display: none;
      }
      color: white;
      .designation {
        color: var(--secondary-color);
      }
      .description {
        height: 8rem;
      }
    }
    .controls {
      display: flex;
      gap: 1rem;
      margin-top: -100px;
      button {
        padding: 0.5rem;
        border-radius: 1rem;
        background-color: var(--secondary-color);
        border: 0.1rem solid transparent;
        cursor: pointer;
      }
      .active {
        background-color: transparent;
        border: 0.1rem solid var(--secondary-color);
      }
    }
  }
  @media screen and (min-width: 280px) and (max-width: 1080px) {
    .background {
      display: none;
    }
    .container {
      padding: 4rem 0;
      .testimonials {
        width: 80%;
        .testimonial {
          .description {
            height: 18rem;
          }
        }
      }
    }
  }
`;

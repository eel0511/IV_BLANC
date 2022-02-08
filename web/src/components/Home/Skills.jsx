import React from "react";
import styled from "styled-components";
import Title from "./Title";
import skills1 from "../../assets/skills1.png";
import skills2 from "../../assets/skills2.png";
import logotext from '../../assets/logo2.png'
import logo from '../../assets/logo.png'
import { skillsBarAnimations } from "../../animations";
import { motion } from "framer-motion";
import { useScroll } from "../useScroll";
function Skills() {
  const [element, controls] = useScroll();
  const skillsData = [
    {
      name: "creativity",
      amount: 75,
    },
    {
      name: "coding",
      amount: 50,
    },
    {
      name: "react",
      amount: 90,
    },
    {
      name: "marketing",
      amount: 70,
    },
    {
      name: "design",
      amount: 30,
    },
  ];
  return (
    <Section id="skills" ref={element}>
      <Title value="brand" />
      <div className="background">
        <img src={skills1} alt="skills design" className="design1" />
        <img src={skills2} alt="skills design" className="design2" />
      </div>
      <div className="skills__title">
        <p>IV BLANC</p>
        <h2>Check our super awesome visions</h2>
      </div>
      <div className="skills">
      {/* <img src={logotext} alt="logotext" className="logotext" />
      <img src={logo} alt="logo" className="logo"/> */}
        {/* <div className="skills__bars">
          {skillsData.map(({ name, amount }) => {
            return (
              <motion.div
                className="skills__bars__bar"
                key={name}
                variants={skillsBarAnimations}
                animate={controls}
                transition={{
                  delay: 0.03,
                  type: "tween",
                  duration: 0.8,
                }}
                whileInView={{ opacity: 1, y: 0 }}
              >
                <div className="container">
                  <progress value={amount} max="100" />
                  <span>{name}</span>
                </div>
                <h3>{amount}%</h3>
              </motion.div>
            );
          })}
        </div> */}
        <div className="skills__content">
          <p className="title">
            Why IV BLANC?
          </p>
          <p className="description">
          입다 + 몽블랑 : 브랜드 가치가 높은 패션 쉐어 플랫폼의 명품이 되기위해!
          </p>
          <p className="title">
            What does LOGO mean?
          </p>
          <p className="description">
          로고 의미는 IV BLANC의 첫 이니셜을 따서 I, B인데 
두 개의 B는 저희가 또 공유 옷장, 즉 옷장을 공유하는 플랫폼이라 옷장의 문을 열어논것을 표현하였고
중간의 I는 옷장을 열어 나 자신 = I 로 표현해 나 스스로의 스타일을 찾아가겠다를 의미!
          </p>
        </div>
      </div>
    </Section>
  );
}

const Section = styled.section`
  min-height: 100vh;
  height: 140vh;
  background-color: var(--secondary-color);
  .background {
    position: relative;
    .design1 {
      position: absolute;
      right: 0;
      z-index: 1;
    }
    .design2 {
      position: absolute;
      left: 0;
      z-index: 1;
      top: 20rem;
    }
  }
  .sideTitle {
    h1 {
      color: white;
      font-size: 9rem;
      z-index: 2;
    }
  }
  .skills__title {
    padding: 6rem 10rem;
    p {
      text-transform: uppercase;
      letter-spacing: 0.2rem;
      color: var(--primary-color);
      z-index: 2;
    }
    h2 {
      color: white;
      font-size: 2rem;
      z-index: 2;
    }
  }
  .skills {
    display: flex;
    padding: 0 20rem;
    gap: 10rem;
    &__bars {
      transform: rotate(-90deg);
      width: max-content;
      height: max-content;
      display: flex;
      flex-direction: column;
      gap: 4rem;
      &__bar {
        display: flex;
        flex-direction: row-reverse;

        gap: 1rem;
        .logo{
          z-index: 1;
        }
        .logotext{
          z-index: 1;
        }
        .container {
          display: flex;
          flex-direction: column;
          gap: 2.5rem;
          span {
            text-transform: uppercase;
            letter-spacing: 0.2rem;
            color: var(--primary-color);
          }
          progress {
            width: 30rem;
            -webkit-appearance: none;
            appearance: none;
            &::-webkit-progress-bar {
              height: 3rem;
              background-color: white;
            }
            &::-webkit-progress-value {
              background-color: var(--primary-color);
            }
          }
        }
        h3 {
          transform: rotate(90deg);
          color: white;
          font-size: 2rem;
        }
      }
    }
    &__content {
      display: flex;
      flex-direction: column;
      gap: 2rem;
      color: white;
      z-index: 2;
      .title {
        font-weight: bolder;
        letter-spacing: 0.1rem;
      }
    }
  }
  @media screen and (min-width: 280px) and (max-width: 1080px) {
    overflow-x: hidden;
    padding: 2rem 0;
    .background {
      display: none;
    }
    .skills__title {
      padding: 2rem;
      text-align: center;
      h2 {
        font-size: 1.5rem;
      }
    }
    .skills {
      padding: 0;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      gap: 1rem;
      &__bars {
        padding: 0.2rem;
        gap: 0rem;
        align-items: center;
        justify-content: center;
        &__bar {
          .container {
            gap: 1rem;
            progress {
              width: 12rem;
              height: 0.5rem;
              &::-webkit-progress-bar {
                height: 0.3rem;
              }
            }
          }
          h3 {
            font-size: 1rem;
          }
        }
      }
      &__content {
        padding: 0 2rem;
      }
    }
  }
`;

export default Skills;

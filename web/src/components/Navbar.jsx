import React, { useState } from "react";
import styled from "styled-components";
import logo from "../assets/logo.png";
import { GiHamburgerMenu } from "react-icons/gi";
import { MdClose } from "react-icons/md";
import { motion } from "framer-motion";
import { navAnimation } from "../animations";
import { useScroll } from "./useScroll";
import { Link } from 'react-router-dom';

function isActive() {
  return window.location.pathname;
}

export default function Navbar() {
  const [isNavOpen, setIsNavOpen] = useState(false);
  const [element, controls] = useScroll();
  const html = document.querySelector("html");
  html.addEventListener("click", (e) => setIsNavOpen(false));

  return (
    <Nav
      state={isNavOpen ? 1 : 0}
      variants={navAnimation}
      transition={{ delay: 0.1 }}
      ref={element}
      animate={controls}
    >
      <div className="brand__container">
      <Link className='brand' to='/'>
          <img src={logo} alt="Logo" />
          </Link>
        <div className="toggle">
          {isNavOpen ? (
            <MdClose onClick={() => setIsNavOpen(false)} />
          ) : (
            <GiHamburgerMenu
              onClick={(e) => {
                e.stopPropagation();
                setIsNavOpen(true);
              }}
            />
          )}
        </div>
      </div>
      <div className={`links ${isNavOpen ? "show" : ""}`}>
        <ul> 
          <li className={(isActive() === '/') ? 'active' : null}>
          <Link className={(isActive() === '/') ? 'navbar-brand' : 'nav-link'} to='/'>Home</Link>
          </li>
          <li className={(isActive() === '/mycloset') ? 'active' : null}>
          <Link className={(isActive() === '/mycloset') ? 'navbar-brand' : 'nav-link'} aria-current='page' to='/mycloset' >Closet</Link>
          </li>
          <li className={(isActive() === '/mystyle') ? 'active' : null}>
          <Link className={(isActive() === '/mystyle') ? 'navbar-brand' : 'nav-link'} aria-current='page' to='/mystyle'>Pick</Link>
          </li>
          <li className={(isActive() === '/friends') ? 'active' : null}>
          <Link className={(isActive() === '/friends') ? 'navbar-brand' : 'nav-link'} aria-current='page' to='/friends'>Share</Link>
          </li>
          <li className={(isActive() === '/history') ? 'active' : null}>
          <Link className={(isActive() === '/history') ? 'navbar-brand' : 'nav-link'} aria-current='page' to='/history'>History</Link>
          </li>
          {/* <li>
            <a href="#contact">Notice</a>
          </li> */}
          <li>
          <Link className='nav-link active' aria-current='page' to='/signup'>SignUp</Link>
          </li>
          <li>
          <Link className='nav-link active' aria-current='page' to='/signin'>Sign in</Link>
          </li>
        </ul>
      </div>
    </Nav>
  );
}

const Nav = styled(motion.nav)`
  display: flex;
  justify-content: space-between;
  margin: 0 12rem;
  padding-top: 2rem;
  /* background-color: #ed6991; */
  color: white;
  .brand__container {
    margin: 0 2rem;
    .brand {
      img {
          width: 50px;
          height: 50px;
        }
    }
    .toggle {
      display: none;
    }
  }
  .links {
    ul {
      .active {
        a {
          border-bottom: 0.2rem solid var(--secondary-color);
        }
      }
      list-style: none;
      display: flex;
      gap: 3rem;
      li {
        a {
          color: white;
          text-decoration: none;
          font-weight: bold;
          // font-size: 1.1rem;
        }
      }
    }
  }
  @media screen and (min-width: 280px) and (max-width: 1080px) {
    margin: 0;
    position: relative;
    .brand__container {
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 100%;
      .brand {
        img {
          width: 100px;
          height: 100px;
        }
      }
      .toggle {
        padding-right: 1rem;
        display: block;
        z-index: 1;
      }
    }
    .show {
      opacity: 1 !important;
      visibility: visible !important;
    }

    .links {
      position: absolute;
      overflow-x: hidden;
      top: 0;
      right: 0;
      width: ${({ state }) => (state ? "60%" : "0%")};
      height: 100vh;
      background-color: var(--secondary-color);
      opacity: 0;
      visibility: hidden;
      transition: 0.4s ease-in-out;
      ul {
        flex-direction: column;
        text-align: center;
        height: 100%;
        justify-content: center;
      }
    }
  }
`;

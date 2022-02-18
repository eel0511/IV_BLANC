import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import logo2 from '../assets/logo2.png';
import { GiHamburgerMenu } from 'react-icons/gi';
import { MdClose } from 'react-icons/md';
import { TiVendorAndroid } from 'react-icons/ti';
import { motion } from 'framer-motion';
import { navAnimation } from '../animations';
import { useScroll } from './useScroll';
import { Link, useNavigate } from 'react-router-dom';
import Notice from './Friends/Notice';
import Profile from './Profile';
import CreateJWT from './CreateJWT';

function isActive() {
  return window.location.pathname;
}

export default function Navbar() {
  const [isNavOpen, setIsNavOpen] = useState(false);
  const [element, controls] = useScroll();
  const html = document.querySelector('html');
  html.addEventListener('click', (e) => setIsNavOpen(false));

  // JWT 있는지 없는지 찾기
  const [isToken, setIsToken] = useState(false);
  useEffect(() => {
    let keys = Object.keys(localStorage);
    for (let key of keys) {
      if (key === 'JWT') {
        setIsToken(true);
        // console.log('true')
      }
    }
  }, []);

  let navigate = useNavigate();

  const isLogin = (e) => {
    if (isToken === false) {
      e.preventDefault();
      alert('로그인을 해야 이용가능한 서비스 입니다');
      navigate('/signin');
    }
    // setTitle(e.target.outerText);
  };

  return (
    <Nav
      state={isNavOpen ? 1 : 0}
      variants={navAnimation}
      transition={{ delay: 0.1 }}
      ref={element}
      animate={controls}
    >
      <div className='brand__container'>
        <Link className='brand' to='/'>
          <img src={logo2} alt='Logo' />
        </Link>
        <div className='toggle'>
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
      <div className={`links ${isNavOpen ? 'show' : ''}`}>
        <ul>
          <li className={isActive() === '/' ? 'active' : null}>
            <Link className='nav-link' to='/'>
              Home
            </Link>
          </li>
          <li className={isActive() === '/mycloset' ? 'active' : null}>
            <Link
              className='nav-link'
              aria-current='page'
              to='/mycloset'
              onClick={isLogin}
            >
              Closet
            </Link>
          </li>
          <li className={isActive() === '/mystyle' ? 'active' : null}>
            <Link
              className='nav-link'
              aria-current='page'
              to='/mystyle'
              onClick={isLogin}
            >
              Pick
            </Link>
          </li>
          <li className={isActive() === '/friends' ? 'active' : null}>
            <Link
              className='nav-link'
              aria-current='page'
              to='/friends'
              onClick={isLogin}
            >
              Share
            </Link>
          </li>
          <li className={isActive() === '/history' ? 'active' : null}>
            <Link
              className='nav-link'
              aria-current='page'
              to='/history'
              onClick={isLogin}
            >
              History
            </Link>
          </li>

          {isToken === false ? (
            <>
              <li className={isActive() === '/signup' ? 'active' : null}>
                <Link className='nav-link' aria-current='page' to='/signup'>
                  SignUp
                </Link>
              </li>
              <li className={isActive() === '/signin' ? 'active' : null}>
                <Link className='nav-link' aria-current='page' to='/signin'>
                  Sign in
                </Link>
              </li>
            </>
          ) : (
            <>
              <li>
                <Notice />
              </li>
              <li>
                <Profile />
              </li>
              {/* <li>
                <CreateJWT />
              </li> */}
            </>
          )}
          <li>
            <a href='https://storage.googleapis.com/iv-blanc.appspot.com/app-release.apk'>
              <TiVendorAndroid
                className='Android__icon'
                id='basic-button'
                aria-haspopup='true'
                style={{
                  width: '45px',
                  height: '45px',
                  position: 'relative',
                  top: '-5px',
                  cursor: 'pointer',
                }}
              />
            </a>
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
    /* margin: 20 20; */
    .brand {
      img {
        width: 9rem;
        height: 9rem;
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
          //   background-color: #ed6991;
          //   border-radius: 15px;
          border-bottom: 0.2rem solid var(--secondary-color);
        }
      }
      list-style: none;
      margin-top: 3rem;
      display: flex;
      gap: 3rem;
      li {
        a {
          color: white;
          text-decoration: none;
          font-weight: bold;
          font-size: 1.1rem;
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
      width: ${({ state }) => (state ? '60%' : '0%')};
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

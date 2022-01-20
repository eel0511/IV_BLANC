import React, { Component } from 'react';
import { render } from 'react-dom';
import { useLocation } from 'react-router-dom';

class NaverLogin extends Component {
  componentDidMount() {
    // Naver sdk import
    const naverScript = document.createElement('script');
    naverScript.src = 'https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js';
    naverScript.type = 'text/javascript';
    document.head.appendChild(naverScript);

    // Naver sdk 스크립트 로드 완료시
    naverScript.onload = () => {
      const naverLogin = new window.naver.LoginWithNaverId({
        clientId: 'yx7UoBGqqxvx15EH1IQW',
        callbackUrl: 'http://localhost:3000/oauth/naver/callback',
        callbackHandle: true,
        isPopup: false, // 로그인 팝업여부
        loginButton: {
          color: 'green', // 색상(white, green)
          type: 2, // 버튼타입(1,2,3)
          height: 60, // 배너 및 버튼 높이
        },
      });

      naverLogin.init();
      naverLogin.logout(); // 네이버 로그인이 계속 유지되는 경우가 있다. 초기화시 로그아웃
      naverLogin.getLoginStatus((status) => {
        if (status) {
          console.log('Naver 로그인 상태', naverLogin.user);
          const { id, email, gender } = naverLogin.user;

          // 필수 제공 동의 조건
          // if (gender === undefined) {
          //   alert('성별은 필수 동의입니다. 정보제공을 동의해주세요.');
          //   naverLogin.reprompt();
          //   return;
          // } else {
          //   console.log('Naver 비로그인 상태');
          // }
        }
      });
    };
  }

  render() {
    return <div id='naverIdLogin'></div>;
  }
}

export default NaverLogin;

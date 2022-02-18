import React, { Component } from 'react';

class KakaoLogin extends Component {
  componentDidMount() {
    // Kakao sdk import
    const kakaoScript = document.createElement('script');
    kakaoScript.src = 'https://developers.kakao.com/sdk/js/kakao.min.js';
    document.head.appendChild(kakaoScript);

    // kakao sdk 스크립트 로드 완료시
    kakaoScript.onload = () => {
      window.Kakao.init('710fb0b52bae8c8d5d7e650b2611ea81'); // 자바스크립트 키
      window.Kakao.Auth.createLoginButton({
        container: '#kakao-login-btn',
        success: (auth) => {
          // kakao 로그인 성공 시, 사용자 정보 API 호출
          window.Kakao.API.request({
            url: '/v2/user/me',
            success: (res) => {},
            fail: (err) => {
              console.log(err);
            },
          });
        },
        fail: (err) => {
          console.log(err);
        },
      });
    };
  }

  render() {
    return <div id='kakao-login-btn'></div>;
  }
}

export default KakaoLogin;

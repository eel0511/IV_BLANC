import React from 'react';

const NaverLogin = () => {
  const url = window.location.href.split('=')[1];
  const loca = url.split('&')[0];
  //   const header = {
  //     Authorization: loca,
  //   };

  //   fetch('http://', {
  //     method: 'get',
  //     headers: header,
  //   })
  //     .then((res) => res.json())
  //     .then((res) => {
  //       localStorage.setItem('wtw-token', res.token);
  //       setData(res.user);
  //     });
  // const { hostname, protocol } = window.location;

  // const callbackUrl = `${protocol}//${hostname}/naver-login`;
  return <div>waiting for a minute...</div>;
};

export default NaverLogin;

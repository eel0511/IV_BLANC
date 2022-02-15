import React from 'react';

export default function CreateJWT() {
  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const createToken = () => {
    localStorage.setItem('JWT', token);
  };

  return (
    <div className='CreateJWT'>
      <button
          type='button'
          className='btn'
          onClick={createToken}
        >
          토큰생성
        </button>
    </div>
  );
}

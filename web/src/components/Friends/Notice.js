import React from 'react';

export default function Notice() {
  return (
    <>
      <img
        className='Notice'
        src={require('../../assets/알림.png')}
        alt='Notice'
        style={{ width: '50px', height: '50px' }}
      />
    </>
  );
}

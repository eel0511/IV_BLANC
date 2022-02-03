import React from 'react';
import './Friends.css';

export default function FriendsCreateButton() {
  return (
    <>
      <h1>footer</h1>
      <img
        className='friedsCreateButton'
        src={require('../../assets/친구등록.png')}
        alt='Create'
        style={{ width: '100px', height: '100px' }}
      />
    </>
  );
}

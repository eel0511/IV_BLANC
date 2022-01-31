import React from 'react';
import FriendsList from '../../layouts/Friends/FriendsList';
import FriendsCreateButton from '../../components/Friends/FriendsCreateButton';

export default function Friends() {
  return (
    <>
      <h1>Friends</h1>
      <body><FriendsList /></body>
      <footer><FriendsCreateButton /></footer>
    </>
  );
}

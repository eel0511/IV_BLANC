import React from 'react';
import FriendsList from '../../layouts/Friends/FriendsList';
import FriendsCreateButton from '../../components/Friends/FriendsCreateButton';
import Notice from '../../components/Friends/Notice';

export default function Friends() {
  return (
    <>
      <h1>Friends</h1>
      <div><FriendsList /></div>
      <footer>
        <Notice />
        <FriendsCreateButton />
      </footer>
    </>
  );
}

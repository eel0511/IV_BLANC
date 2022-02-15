import React from 'react';
import { Avatar, MenuItem } from '@mui/material';
import axios from 'axios';

export default function FriendsAcceptButton({ friend }) {
  const token = localStorage.getItem('JWT');
  // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDM4NTQ1MDIsImV4cCI6MTY0NjQ0NjUwMn0.s4B6viyO_tR8lZMUdxW62u82uT08ZltwgEBpuvTBZOQ';
  const friendsAcceptRequest = () => {
    axios
      .post('http://i6d104.p.ssafy.io:9999/api/friend/isaccept', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
        applicant: 'b@a.com', // 로그인한 사용자 email
        friendName: `${friend.friendEmail}`,
      })
      .then(() => {
        alert('친구추가 성공');
      });
  };

  return (
    <>
      <div className='FriendsAcceptButton'>
        <Avatar alt='' src='/static/images/avatar/1.jpg' />
        {friend.friendName}
        <MenuItem onClick={friendsAcceptRequest}>수락</MenuItem>
      </div>
    </>
  );
}

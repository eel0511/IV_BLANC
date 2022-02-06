import React from 'react';
import { Avatar, MenuItem } from '@mui/material';
import axios from 'axios';

export default function FriendsAcceptButton({ friend }) {

  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDM4NTQ1MDIsImV4cCI6MTY0NjQ0NjUwMn0.s4B6viyO_tR8lZMUdxW62u82uT08ZltwgEBpuvTBZOQ';
  const friendsAcceptRequest = () => {
    axios
      .post('http://i6d104.p.ssafy.io:9999/api/friend/friendrequest',
      {
        headers: {
          'X-AUTH-TOKEN': `${token}`
        },
        applicant: 'aaa@bbb.com',
        friendName: `${friend.friendEmail}`

      })
      .then(() => {
        alert('친구추가 성공')
      });
  };

  return (
    <>
      <Avatar alt='' src='/static/images/avatar/1.jpg'/>
      {friend.friendName}
      <MenuItem onClick={test}>수락</MenuItem>
    </>
  );
}

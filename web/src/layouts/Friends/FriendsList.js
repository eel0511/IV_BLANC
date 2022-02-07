import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Avatar, Paper, Box } from '@mui/material';
import './Friends.css';

export default function FriendsList() {
  const [friendsList, setFriendsList] = useState([
    {
      friendEmail: 'aaa@bbb.com',
      friendName: '김나박이'
    },
    {
      friendEmail: 'aaa@bbb.com',
      friendName: '이나박이'
    },
    {
      friendEmail: 'aaa@bbb.com',
      friendName: '장나박이'
    }
  ]);
  const [friendsClothesList, setFriendsClothesList] = useState([
    {
      "category": 0,
      "clothesId": 0,
      "color": "string",
      "count": 0,
      "createDate": "2022-02-07T02:00:58.443Z",
      "dislikePoint": 0,
      "favorite": 0,
      "likePoint": 0,
      "material": 0,
      "season": 0,
      "size": 0,
      "updateDate": "2022-02-07T02:00:58.443Z",
      "url": "string",
      "userId": 0
    }
  ]);

  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDM4NTQ1MDIsImV4cCI6MTY0NjQ0NjUwMn0.s4B6viyO_tR8lZMUdxW62u82uT08ZltwgEBpuvTBZOQ';
  const getFriendsList = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/friend/isaccept',
      { 
        headers: {
          'X-AUTH-TOKEN': `${token}`
        },
        params: {
          applicant: 'abc@naver.com'
        }
      })
      .then((response) => {
        // setFriendsList(response.data);
        console.log(response.data.data)
      });
  };

  // useEffect(() => {
  //   getFriendsList();
  // }, []);

  return (
    <div className='friend'>
      {/* 친구 프로필 이름, 친구옷 스타일링 */}
      <div className='friend__header'>
        <Avatar
          className='friend__avatar'
          alt=''
          src='/static/images/avatar/1.jpg'
        />
        <h3>friendName</h3>
      </div>

      {/* 친구 옷 */}
      <div className='friend__body'>
        <Box
          sx={{
            display: 'flex',
            flexWrap: 'wrap',
            '& > :not(style)': {
              m: 1,
              width: 128,
              height: 128,
            },
          }}
        >
          <Paper elevation={8} />
          <Paper elevation={8} />
          <Paper elevation={8} />
          <Paper elevation={8} />
          <Paper elevation={8} />
        </Box>
      </div>
    </div>
  );
}
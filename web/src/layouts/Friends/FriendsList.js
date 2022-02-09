import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Avatar, Paper, Box } from '@mui/material';
import './Friends.css';
import FirendsListItem from '../../components/Friends/FriendsListItem';
import pricing1 from "../../assets/pricing1.png";
import pricing2 from "../../assets/pricing2.png";
import Title from '../../components/Home/Title';
import FriendsCreateButton from '../../components/Friends/FriendsCreateButton';
import Notice from '../../components/Friends/Notice';

export default function FriendsList() {
  const [friendsList, setFriendsList] = useState([
    {
      friendEmail: 'aaa@bbb.com',
      friendName: '김나박이',
    },
    {
      friendEmail: 'aaa@bbb.com',
      friendName: '이나박이',
    },
    {
      friendEmail: 'aaa@bbb.com',
      friendName: '장나박이',
    },
  ]);

  const token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDM4NTQ1MDIsImV4cCI6MTY0NjQ0NjUwMn0.s4B6viyO_tR8lZMUdxW62u82uT08ZltwgEBpuvTBZOQ';
  const getFriendsList = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/friend/isaccept', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
        params: {
          applicant: 'abc@naver.com', // 로그인한 사용자 email
        },
      })
      .then((response) => {
        setFriendsList(response.data.data);
        // console.log(response.data.data)
      });
  };

  // useEffect(() => {
  //   getFriendsList();
  // }, []);

  return (
    <div className='friend'>
      <Title value="SHARE" />
      <div className="background">
        <img src={pricing1} alt="background" className="bg1" />
        <img src={pricing2} alt="background" className="bg2" />
      </div>
      <div className='friend__content'>
        {friendsList.map((friend, id) => (
          <div key={id}>
            <FirendsListItem friend={friend} />
          </div>
        ))}
      </div>
      <FriendsCreateButton />
    </div>
  );
}

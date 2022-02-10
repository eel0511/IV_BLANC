import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Avatar, Paper, Box } from '@mui/material';
import { BsPlusCircle } from 'react-icons/bs';
import { Link } from 'react-router-dom';
import './Friends.css';

export default function FirendsListItem({ friend }) {
  const [friendsClothesList, setFriendsClothesList] = useState([
    {
      createDate: '2022-02-09T09:52:15',
      updateDate: '2022-02-09T09:52:15',
      styleId: 3,
      madeby: 'b@a.com',
      favorite: 0,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/a03811f4-21ae-44d4-9316-b747a2f610b7.png',
      photoName: null,
      userId: 1,
      styleDetails: [
        {
          sdId: 3,
          clothes: {
            createDate: '2022-02-08T21:49:24',
            updateDate: '2022-02-08T21:49:24',
            clothesId: 73,
            category: 10,
            color: '#ABAEB6',
            material: 1,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/45e3779f-5878-4106-a422-ab4719acf55d.jfif',
            favorite: 0,
            size: 95,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 4,
          clothes: {
            createDate: '2022-02-08T15:51:09',
            updateDate: '2022-02-08T15:51:09',
            clothesId: 62,
            category: 21,
            color: '#000000',
            material: 1,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/b8c70839-8ded-4dd8-b198-e5044ebbfe32.jfif',
            favorite: 0,
            size: 100,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
      ],
    },
    {
      createDate: '2022-02-09T22:20:39',
      updateDate: '2022-02-09T22:20:39',
      styleId: 7,
      madeby: 'b@a.com',
      favorite: 0,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/e02e90e5-fdc6-42d9-8681-515820740ec2.png',
      photoName: null,
      userId: 1,
      styleDetails: [
        {
          sdId: 9,
          clothes: {
            createDate: '2022-02-09T10:19:17',
            updateDate: '2022-02-09T10:19:17',
            clothesId: 104,
            category: 10,
            color: '#C14D49',
            material: 1,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/bc7806a2-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 100,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 10,
          clothes: {
            createDate: '2022-02-09T10:14:34',
            updateDate: '2022-02-09T10:14:34',
            clothesId: 103,
            category: 20,
            color: '#27476F',
            material: 8,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/13e9c3e0-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 32,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 11,
          clothes: {
            createDate: '2022-02-09T19:23:44',
            updateDate: '2022-02-09T19:23:44',
            clothesId: 106,
            category: 60,
            color: '#000000',
            material: 4,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/5bbeabf8-8992-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 34,
            season: 4,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 12,
          clothes: {
            createDate: '2022-02-09T19:30:45',
            updateDate: '2022-02-09T19:30:45',
            clothesId: 111,
            category: 70,
            color: '#5E4B48',
            material: 15,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/56d14d3e-8993-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 22,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
      ],
    },
    {
      createDate: '2022-02-09T22:35:08',
      updateDate: '2022-02-09T22:35:08',
      styleId: 8,
      madeby: 'b@a.com',
      favorite: 0,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/5b80f9f8-94ce-4ed7-9b3b-60a6235c4c76.png',
      photoName: null,
      userId: 1,
      styleDetails: [
        {
          sdId: 13,
          clothes: {
            createDate: '2022-02-09T10:19:17',
            updateDate: '2022-02-09T10:19:17',
            clothesId: 104,
            category: 10,
            color: '#C14D49',
            material: 1,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/bc7806a2-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 100,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 14,
          clothes: {
            createDate: '2022-02-09T10:14:34',
            updateDate: '2022-02-09T10:14:34',
            clothesId: 103,
            category: 20,
            color: '#27476F',
            material: 8,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/13e9c3e0-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 32,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 15,
          clothes: {
            createDate: '2022-02-09T19:23:44',
            updateDate: '2022-02-09T19:23:44',
            clothesId: 106,
            category: 60,
            color: '#000000',
            material: 4,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/5bbeabf8-8992-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 34,
            season: 4,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 16,
          clothes: {
            createDate: '2022-02-09T19:30:45',
            updateDate: '2022-02-09T19:30:45',
            clothesId: 111,
            category: 70,
            color: '#5E4B48',
            material: 15,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/56d14d3e-8993-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 22,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 17,
          clothes: {
            createDate: '2022-02-09T10:19:17',
            updateDate: '2022-02-09T10:19:17',
            clothesId: 104,
            category: 10,
            color: '#C14D49',
            material: 1,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/bc7806a2-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 100,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 18,
          clothes: {
            createDate: '2022-02-09T19:24:43',
            updateDate: '2022-02-09T19:24:43',
            clothesId: 108,
            category: 20,
            color: '#9C7E3A',
            material: 8,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/7f0f5e4a-8992-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 33,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 19,
          clothes: {
            createDate: '2022-02-09T19:28:16',
            updateDate: '2022-02-09T19:28:16',
            clothesId: 109,
            category: 51,
            color: '#27476F',
            material: 9,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/fde3b66c-8992-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 33,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
      ],
    },
    {
      createDate: '2022-02-09T22:36:37',
      updateDate: '2022-02-09T22:36:37',
      styleId: 9,
      madeby: 'b@a.com',
      favorite: 0,
      url: 'https://storage.googleapis.com/iv-blanc.appspot.com/ad28d1aa-dc5c-4159-8786-fa57ebb4a1eb.png',
      photoName: null,
      userId: 1,
      styleDetails: [
        {
          sdId: 20,
          clothes: {
            createDate: '2022-02-09T10:19:17',
            updateDate: '2022-02-09T10:19:17',
            clothesId: 104,
            category: 10,
            color: '#C14D49',
            material: 1,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/bc7806a2-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 100,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 21,
          clothes: {
            createDate: '2022-02-09T19:24:43',
            updateDate: '2022-02-09T19:24:43',
            clothesId: 108,
            category: 20,
            color: '#9C7E3A',
            material: 8,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/7f0f5e4a-8992-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 33,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 22,
          clothes: {
            createDate: '2022-02-09T20:20:04',
            updateDate: '2022-02-09T20:20:04',
            clothesId: 112,
            category: 70,
            color: '#F6D766',
            material: 15,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/3a5a368c-899a-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 11,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 23,
          clothes: {
            createDate: '2022-02-09T19:23:44',
            updateDate: '2022-02-09T19:23:44',
            clothesId: 106,
            category: 60,
            color: '#000000',
            material: 4,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/5bbeabf8-8992-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 34,
            season: 4,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 24,
          clothes: {
            createDate: '2022-02-09T10:19:17',
            updateDate: '2022-02-09T10:19:17',
            clothesId: 104,
            category: 10,
            color: '#C14D49',
            material: 1,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/bc7806a2-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 100,
            season: 2,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 25,
          clothes: {
            createDate: '2022-02-09T10:14:34',
            updateDate: '2022-02-09T10:14:34',
            clothesId: 103,
            category: 20,
            color: '#27476F',
            material: 8,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/13e9c3e0-8991-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 32,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
        {
          sdId: 26,
          clothes: {
            createDate: '2022-02-09T20:20:04',
            updateDate: '2022-02-09T20:20:04',
            clothesId: 112,
            category: 70,
            color: '#F6D766',
            material: 15,
            url: 'https://storage.googleapis.com/iv-blanc.appspot.com/3a5a368c-899a-11ec-b7ac-1b30477e6c72.png',
            favorite: 0,
            size: 11,
            season: 3,
            count: 0,
            likePoint: 0,
            dislikePoint: 0,
            userId: 1,
          },
        },
      ],
    },
  ]);

  const token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDM4NTQ1MDIsImV4cCI6MTY0NjQ0NjUwMn0.s4B6viyO_tR8lZMUdxW62u82uT08ZltwgEBpuvTBZOQ';
  const getFriendsClothesList = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/clothes/friendclothes', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
        params: {
          email: `${friend.friendEmail}`,
        },
      })
      .then((response) => {
        setFriendsClothesList(response.data.data);
        // console.log(response.data.data)
      });
  };

  // useEffect(() => {
  //   getFriendsClothesList();
  // }, []);

  return (
    <>
      <div className='friend__header'>
        <Avatar
          className='friend__avatar'
          alt=''
          src='/static/images/avatar/1.jpg'
        />
        <h3>{friend.friendName}</h3>
        <Link
          to={'/friends/closet'}
          state={{
            friendName: `${friend.friendName}`,
            friendEmail: `${friend.friendEmail}`,
          }}
          className='friend__link'
        >
          <BsPlusCircle className='friend__plusIcon' />
        </Link>
      </div>
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
          <Paper elevation={8}></Paper>
          <Paper elevation={8}></Paper>
          <Paper elevation={8}></Paper>
          <Paper elevation={8}></Paper>
          <Paper elevation={8}></Paper>
        </Box>
      </div>
    </>
  );
}

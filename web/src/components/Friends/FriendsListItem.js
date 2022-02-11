import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Avatar, Paper, Box } from '@mui/material';
import { BsPlusCircle } from 'react-icons/bs';
import { Link } from 'react-router-dom';
import './Friends.css';

export default function FriendsListItem({ friend }) {
  const [friendsStyleList, setFriendsStyleList] = useState([
    // {
    //   "createDate": "2022-02-09T19:37:18",
    //   "updateDate": "2022-02-09T19:37:18",
    //   "styleId": 4,
    //   "madeby": "ssu@a.com",
    //   "favorite": 0,
    //   "url": "https://storage.googleapis.com/iv-blanc.appspot.com/bd9b30d1-11ba-43ba-8b73-c3fcb13f0313.jpg",
    //   "photoName": null,
    //   "userId": 8,
    //   "styleDetails": [
    //     {
    //       "sdId": 5,
    //       "clothes": {
    //         "createDate": "2022-02-09T09:12:07",
    //         "updateDate": "2022-02-10T15:50:57",
    //         "clothesId": 100,
    //         "category": 1,
    //         "color": "#FFFFFF",
    //         "material": 1,
    //         "url": "https://storage.googleapis.com/iv-blanc.appspot.com/5a70ac1a-8988-11ec-b7ac-1b30477e6c72.png",
    //         "favorite": 1,
    //         "size": 100,
    //         "season": 1,
    //         "count": 0,
    //         "likePoint": 0,
    //         "dislikePoint": 0,
    //         "userId": 10
    //       }
    //     }
    //   ]
    // },
    // {
    //   "createDate": "2022-02-11T03:38:46",
    //   "updateDate": "2022-02-11T03:38:46",
    //   "styleId": 15,
    //   "madeby": "aaa@a.com",
    //   "favorite": 0,
    //   "url": "https://storage.googleapis.com/iv-blanc.appspot.com/340cd264-eb04-4fbd-a9f8-8ece052599da.jpg",
    //   "photoName": null,
    //   "userId": 8,
    //   "styleDetails": [
    //     {
    //       "sdId": 34,
    //       "clothes": {
    //         "createDate": "2022-02-08T10:27:35",
    //         "updateDate": "2022-02-08T10:27:35",
    //         "clothesId": 57,
    //         "category": 1,
    //         "color": "rrrr",
    //         "material": 1,
    //         "url": "https://storage.googleapis.com/iv-blanc.appspot.com/a9220fb9-2712-46bf-92b6-330280b830a8.jpeg",
    //         "favorite": 0,
    //         "size": 100,
    //         "season": 1,
    //         "count": 0,
    //         "likePoint": 0,
    //         "dislikePoint": 0,
    //         "userId": 8
    //       }
    //     }
    //   ]
    // },
    // {
    //   "createDate": "2022-02-11T13:29:04",
    //   "updateDate": "2022-02-11T13:29:04",
    //   "styleId": 18,
    //   "madeby": "aaa@a.com",
    //   "favorite": 0,
    //   "url": "https://storage.googleapis.com/iv-blanc.appspot.com/cd219cdb-d9f6-48cb-8e6a-d21681155b47.jpg",
    //   "photoName": null,
    //   "userId": 8,
    //   "styleDetails": [
    //     {
    //       "sdId": 38,
    //       "clothes": {
    //         "createDate": "2022-02-09T10:15:07",
    //         "updateDate": "2022-02-09T10:15:07",
    //         "clothesId": 78,
    //         "category": 1,
    //         "color": "#FFFFFF",
    //         "material": 1,
    //         "url": "https://storage.googleapis.com/iv-blanc.appspot.com/0089acec-894c-11ec-b7ac-1b30477e6c72.png",
    //         "favorite": 0,
    //         "size": 100,
    //         "season": 1,
    //         "count": 0,
    //         "likePoint": 0,
    //         "dislikePoint": 0,
    //         "userId": 8
    //       }
    //     }
    //   ]
    // },
    // {
    //   "createDate": "2022-02-11T13:35:00",
    //   "updateDate": "2022-02-11T13:35:00",
    //   "styleId": 19,
    //   "madeby": "aaa@a.com",
    //   "favorite": 0,
    //   "url": "https://storage.googleapis.com/iv-blanc.appspot.com/1f057042-ba9b-46b7-b986-a182dbeced94.jpg",
    //   "photoName": null,
    //   "userId": 8,
    //   "styleDetails": [
    //     {
    //       "sdId": 39,
    //       "clothes": {
    //         "createDate": "2022-02-09T10:09:29",
    //         "updateDate": "2022-02-09T10:09:29",
    //         "clothesId": 74,
    //         "category": 1,
    //         "color": "#FFFFFF",
    //         "material": 1,
    //         "url": "https://storage.googleapis.com/iv-blanc.appspot.com/2ee910f3-0636-46f9-8885-599f1e75d02c.png",
    //         "favorite": 0,
    //         "size": 100,
    //         "season": 1,
    //         "count": 0,
    //         "likePoint": 0,
    //         "dislikePoint": 0,
    //         "userId": 8
    //       }
    //     }
    //   ]
    // },
    // {
    //   "createDate": "2022-02-11T13:35:00",
    //   "updateDate": "2022-02-11T13:35:00",
    //   "styleId": 19,
    //   "madeby": "aaa@a.com",
    //   "favorite": 0,
    //   "url": "https://storage.googleapis.com/iv-blanc.appspot.com/1f057042-ba9b-46b7-b986-a182dbeced94.jpg",
    //   "photoName": null,
    //   "userId": 8,
    //   "styleDetails": [
    //     {
    //       "sdId": 39,
    //       "clothes": {
    //         "createDate": "2022-02-09T10:09:29",
    //         "updateDate": "2022-02-09T10:09:29",
    //         "clothesId": 74,
    //         "category": 1,
    //         "color": "#FFFFFF",
    //         "material": 1,
    //         "url": "https://storage.googleapis.com/iv-blanc.appspot.com/2ee910f3-0636-46f9-8885-599f1e75d02c.png",
    //         "favorite": 0,
    //         "size": 100,
    //         "season": 1,
    //         "count": 0,
    //         "likePoint": 0,
    //         "dislikePoint": 0,
    //         "userId": 8
    //       }
    //     }
    //   ]
    // }
  ]);

  const getFiveStyle = () => {
    const result =[];
    for (let i = 0; i < 5; i++) {
      result.push(friendsStyleList[i]);
    }
    return result;
  };

  const token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const getFriendsStyleList = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/style/findfriendstyle', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
        params: {
          FriendEmail: `${friend.friendEmail}`,
        },
      })
      .then((response) => {
        setFriendsStyleList(response.data.data);
        // console.log(response.data.data)
      });
  };

  // useEffect(() => {
  //   getFriendsStyleList();
  // }, []);

  return (
    <>
      <div className='friend__header'>
        <div className='friend_profile'>
          <Avatar
            className='friend__avatar'
            alt=''
            src='/static/images/avatar/1.jpg'
          />
          <h3>{friend.friendName}</h3>
        </div>
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
        {
          friendsStyleList.length === 0
          ? <div className='friend__card'>
              <div className='friend__cardBody'>
                <h2 className='friend__text'>저장된 룩이 없습니다.</h2>
              </div>
            </div>
          : ( friendsStyleList.length < 5
              ? friendsStyleList.map((friendStyle, id) => (
                <div className='friend__card' key={id}>
                  <div className='friend__cardBody'>
                    <img
                      className='friend__cardImg'
                      src={friendStyle.url}
                      alt='img'
                    />
                  </div>
                </div>
              ))
              : getFiveStyle().map((friendStyle, id) => (
                <div className='friend__card' key={id}>
                  <div className='friend__cardBody'>
                    <img
                      className='friend__cardImg'
                      src={friendStyle.url}
                      alt='img'
                    />
                  </div>
                </div>
              ))
            )
        }
      </div>
    </>
  );
}

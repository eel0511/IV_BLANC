import React, { useState, useEffect } from 'react';
import { Badge, Button, Menu, MenuItem, Avatar } from '@mui/material';
import { BsBell } from 'react-icons/bs';
import axios from 'axios';

export default function Notice() {
  const [friendRequest, setFriendRequest] = useState([
    {
      friendEmail: 'abc@naver.com',
      friendName: '김싸피',
    },
    {
      friendEmail: 'abc@naver.com',
      friendName: '이싸피',
    },
    {
      friendEmail: 'abc@naver.com',
      friendName: '정싸피',
    },
  ]);

  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDM4NTQ1MDIsImV4cCI6MTY0NjQ0NjUwMn0.s4B6viyO_tR8lZMUdxW62u82uT08ZltwgEBpuvTBZOQ';
  const friendRequestListRequest = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/friend/friendrequest',
      {
        headers: {
          'X-AUTH-TOKEN': `${token}`
        },
        params: {
          applicant: 'aaa@bbb.com'
        }
      })
      .then((response) => {
        console.log(response.data);
      });
  };

  // useEffect(() => {
  //   friendRequestListRequest();
  // }, [])

  return (
    <div className='Notice'>
      <Badge color='secondary' badgeContent={friendRequest.length} max={300}>
        <BsBell
          className='Notice__icon'
          id="basic-button"
          aria-controls={open ? 'basic-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={open ? 'true' : undefined}
          onClick={handleClick}
        />
      </Badge>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}
      >
        {friendRequest.map((friend, id) => (
          <div className='Notice__friendRequest' key={id}>
            <Avatar alt='' src='/static/images/avatar/1.jpg'/>
            {friend.friendName}
            <MenuItem onClick={handleClose}>수락</MenuItem>
          </div>
        ))}
      </Menu>
    </div>
  );
}

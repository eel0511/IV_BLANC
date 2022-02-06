import React, { useState } from 'react';
import { Badge, Button, Menu, MenuItem, Avatar } from '@mui/material';
import { BsBell } from 'react-icons/bs';

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

import React, { useState, useEffect } from 'react';
import { Badge, Menu,Divider, } from '@mui/material';
import { BsBell } from 'react-icons/bs';
import FriendsAcceptButton from './FriendsAcceptButton';
import axios from 'axios';

export default function Notice() {
  const [friendRequest, setFriendRequest] = useState([]);

  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const token = localStorage.getItem('JWT');
  // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const userEmail = localStorage.getItem('email');
  const getFriendRequestList = () => {
    axios
      .get('http://i6d104.p.ssafy.io:9999/api/friend/friendrequest', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
        params: {
          applicant: `${userEmail}`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        setFriendRequest(response.data.data);
      });
  };

  useEffect(() => {
    getFriendRequestList();
  }, []);

  return (
    <div className='Notice'>
      <Badge 
      color='secondary' 
      badgeContent={friendRequest.length} 
      max={300}
      sx={{ mb: 1.5 }}
      >
        <BsBell
          className='Notice__icon'
          id='basic-button'
          aria-controls={open ? 'basic-menu' : undefined}
          aria-haspopup='true'
          aria-expanded={open ? 'true' : undefined}
          onClick={handleClick}
          
        />
      </Badge>
      <Menu
        id='basic-menu'
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        sx={{ ml: 1, mt: 1.5 }}
        MenuListProps={{
          elevation: 0,
          sx: {
            overflow: "visible",
            color: "#ed6991",
            mt: 1.5,
            ml: 1.5,
            mb: 1.5,
            "& .MuiAvatar-root": {
              width: 32,
              height: 32,
              ml: 0.5,
              mr: 2,
            },
          },
        }}
      >
        <div>
          {friendRequest.length !== 0 ? (
            friendRequest.map((friend, id) => (
              <div className='Notice__friendRequest' key={id}>
                <FriendsAcceptButton friend={friend} handleClose={handleClose} />
                <Divider />
              </div>
            ))
          ) : (
            <h3>알림이 없습니다.</h3>
          )}
        </div>
      </Menu>
    </div>
  );
}

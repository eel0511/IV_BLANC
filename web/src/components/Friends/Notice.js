import React, { useState, useEffect } from 'react';
import { Badge, Menu } from '@mui/material';
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
        console.log(response.data.data);
        setFriendRequest(response.data.data);
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
        {
          {friendRequest}.length === 0 ?
          <div>
            {friendRequest.map((friend, id) => (
              <div className='Notice__friendRequest' key={id}>
                <FriendsAcceptButton friend={friend} />
              </div>
            ))}
          </div>
          : <h3>새로운 알림이 없습니다.</h3>
        }
      </Menu>
    </div>
  );
}

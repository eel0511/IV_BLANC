import React, { useState } from 'react';
import { BsPersonCircle } from 'react-icons/bs';
import { Menu } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function Profile() {
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  let navigate = useNavigate();
  const logOut = () => {
    localStorage.removeItem('JWT');
    localStorage.removeItem('name');
    localStorage.removeItem('email');
    navigate('/signin');
  };

  return (
    <div className='Profile' style={{ cursor: 'pointer' }}>
      <BsPersonCircle
        className='Profile__icon'
        id='basic-button'
        aria-controls={open ? 'basic-menu' : undefined}
        aria-haspopup='true'
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
        style={{ width: '35px', height: '35px' }}
      />
      <Menu
        id='basic-menu'
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}
      >
        <button
          type='button'
          className='btn'
          onClick={logOut}
        >
          로그아웃
        </button>
      </Menu>

    </div>
  );
}
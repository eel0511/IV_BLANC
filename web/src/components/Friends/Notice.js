import React from 'react';
import { Badge } from '@mui/material';
import { BsBell } from 'react-icons/bs';


export default function Notice() {
  const handleChange = () => {
    alert('hello');
  };

  return (
    <div className='Notice'>
      <Badge color="secondary" badgeContent={1000} max={999}>
        <BsBell className='Notice__icon' onClick={handleChange}/>
      </Badge>
    </div>
  );
}

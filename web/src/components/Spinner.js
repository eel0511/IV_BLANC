import React from 'react';

function Spinner() {
  return (
    <div
      className='spinner-border'
      style={{
        width: '3rem',
        height: '3rem',
        position: 'relative',
        left: '50%',
        right: '50%',
      }}
      role='status'
    ></div>
  );
}

export default Spinner;

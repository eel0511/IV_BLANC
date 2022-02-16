import React from 'react';

function ShowLookImg({ styleLook }) {
  return (
    <div
      className='Look'
      style={{ marginTop: '100px', verticalAlign: 'middle' }}
    >
      <div id='LookImg'>
        <img
          className='MyStyleLook'
          src={styleLook.url}
          alt={styleLook.styleId}
        />
      </div>
    </div>
  );
}

export default ShowLookImg;

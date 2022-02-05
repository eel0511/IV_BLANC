import React, { useState } from 'react';

function SelectedImage({ selectedClothes }) {
  return (
    <div className='wrapper'>
      <div className='imgContainer'>
        {selectedClothes.map((clothesData) => (
          <img
            className='MyStyleClothesItemImg'
            key={clothesData.clothesId}
            src={require(`../../assets/${clothesData.url}`)}
            alt={clothesData.clothesId}
            style={{
              width: '100px',
              height: '100px',
            }}
          />
        ))}
      </div>
    </div>
  );
}

export default SelectedImage;

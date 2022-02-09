import React from 'react';

function SelectedImage({ selectedClothes }) {
  return (
    <div className='wrapper' style={{ marginTop: '50px' }}>
      <div className='imgContainer'>
        {selectedClothes.map((clothesData) => (
          <img
            className='MyStyleClothesItemImg'
            key={clothesData.clothesId}
            src={clothesData.url}
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

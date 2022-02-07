import React from 'react';

function StyleLook({ selectedClothes }) {
  return (
    <div className='wrapper'>
      <div className='col'>
        {selectedClothes.map((clothesData) => (
          <div className='look' key={clothesData.clothesId}>
            <img
              className='MyStyleClothesItemImg'
              src={require(`../../assets/${clothesData.url}`)}
              // src={clothesData.url}
              alt={clothesData.clothesId}
              style={{
                maxWidth: '100%',
                maxHeight: '100%',
              }}
            />
          </div>
        ))}
      </div>
    </div>
  );
}

export default StyleLook;

import React, { useState } from 'react';

function StyleLook({ selectedClothes }) {
  const [shirts, setShirts] = useState({});
  const [pants, setPants] = useState({});
  const [outer, setOuter] = useState({});
  const [shoes, setShoes] = useState({});
  const [bag, setBag] = useState({});
  const [hat, setHat] = useState({});
  const [others, setOthers] = useState({});

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

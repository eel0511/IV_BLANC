import React, { useState } from 'react';
import { imageList } from './SelectedImageList';

function SelectedImage() {
  // const [inputs, setInputs] = useState({
  //   category: 0,
  //   clothesId: 0,
  //   color: '',
  //   count: 0,
  //   createDate: '',
  //   dislikePoint: 0,
  //   favorite: 0,
  //   material: '',
  //   season: 0,
  //   size: 0,
  //   updateDate: '',
  //   url: '',
  //   userId: 0,
  // });

  // const [selectedImg, setSelectedImg] = imageList;

  return (
    <div className='wrapper'>
      <div
        className='imgContainer'
        style={{ display: 'flex', flexDirection: 'row' }}
      >
        {imageList.map((clothesData) => (
          <img
            className='MyStyleClothesItemImg'
            key={clothesData.clothesId}
            src={require(`../../assets/${clothesData.url}`)}
            alt={clothesData.clothesId}
            style={{
              width: '100px',
              height: '100px',
            }}
            // onClick={() => setSelectedImg()}
          />
        ))}
      </div>
    </div>
  );
}

export default SelectedImage;

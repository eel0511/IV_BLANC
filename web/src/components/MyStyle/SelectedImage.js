import React, { useState } from 'react';
import { imageList } from './SelectedImageList';

function SelectedImage() {
  const [selectedImg, setSelectedImg] = useState(false);

  return (
    <div className='imgContainer'>
      {ImageList.map((img, index) => (
        <img
          key={index}
          src={img}
          alt='이미지'
          onClick={() => setSelectedImg()}
        />
      ))}
    </div>
  );
}

export default SelectedImage;

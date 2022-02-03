import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { Container, Row, Col } from 'react-bootstrap';

import { imageList } from './SelectedImageList';

// import './MyStyleImg.css';
const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

export default function MyClosetClothesItem({ clothesData }) {
  const [selectedImg, setSelectedImg] = useState();

  // const [list, setList] = useState(imageList);

  // useEffect(() => {
  //   console.log(list);
  // });
  const imageRenderer = useCallback((e) => {
    console.log(e.target.alt);
    const clothId = Number(e.target.alt);
    // setList([...list, clothesData]);
    imageList.push(clothesData);
    // imageList.forEach((element) => {
    //   // console.log(element);
    //   // console.log(clothId in element);
    //   // console.log(Object.keys(element).includes(clothId));
    //   if (element.clothesId === clothId) return false;
    //   // if (!Object.keys(element).includes(clothId))
    //   if()
    //   imageList.push({ clotehsId: clothId });
    // });

    console.log(imageList);
  }, []);

  return (
    <div className='card h-100'>
      <div className='card-body'>
        <img
          className='MyClosetClothesItemImg'
          src={require(`../../assets/${clothesData.url}`)}
          alt={clothesData.clothesId}
          style={{
            maxWidth: '100%',
            maxHeight: '100%',
          }}
          onClick={imageRenderer}
        />
      </div>
    </div>
  );
}

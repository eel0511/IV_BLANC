import React, { useState } from 'react';

export default function MyClothesCreateModalBody() {
  const colorList = [
    '빨강',
    '주황',
    '베이지',
    '노랑',
    '핑크',
    '초록',
    '하늘',
    '파랑',
    '보라',
    '갈색',
    '회색',
    '네이비',
    '검정',
  ];
  const [selectColor, setSelectColor] = useState('빨강');

  const handleChange = (e) => {
    console.log('hello');
  };

  return (
    <form className='RadioForm'>
      <div><h2>색상</h2></div>
      {colorList.map((color, index) => {
        return (
          <React.Fragment key={index}>
            <input
              id={color}
              value={color}
              className='form-check-input'
              type='radio'
              name='colorGroup'
              onChange={handleChange()}
            />
            <label className='form-check-label' htmlFor={color}>
              {color}
            </label>
          </React.Fragment>
        );
      })}
    </form>
  );
}

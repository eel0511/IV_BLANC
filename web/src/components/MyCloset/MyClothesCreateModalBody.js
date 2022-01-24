import React from 'react';

export default function MyClothesCreateModalBody() {
  return (
    <div className='RadioForm'>
      <div className='form-check'>
        <input
          className='form-check-input'
          type='radio'
          name='flexRadioDefault'
          id='flexRadioDefault1'
        />
        <label className='form-check-label' for='flexRadioDefault1'>
          빨강
        </label>
      </div>
      <div className='form-check'>
        <input
          className='form-check-input'
          type='radio'
          name='flexRadioDefault'
          id='flexRadioDefault2'
        />
        <label className='form-check-label' for='flexRadioDefault2'>
          주황
        </label>
      </div>
    </div>
  );
}

import React from 'react';
import MyClosetClothesItem from '../../components/MyCloset/MyClosetClothesItem';
import MyClothesCreateButton from '../../components/MyCloset/MyClothesCreateButton';

export default function MyClosetClothes({ clothesDatas }) {
  return (
    <div className='MyClosetClothes'>
      <div>
        <MyClothesCreateButton />
      </div>
      <div className='container-fluid'>
        <div className='row'>
          {clothesDatas.map((clothesData) => (
            <div className='col-4 mt-3' key={clothesData.clothesId}>
              <MyClosetClothesItem clothesData={clothesData} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

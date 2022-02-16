import React from 'react';
import MyClosetClothesItem from '../../components/MyCloset/MyClosetClothesItem';
import MyClothesCreateButton from '../../components/MyCloset/MyClothesCreateButton';

export default function MyClosetClothes({ clothesDatas, getMyClothesData }) {
  return (
    <div className='MyClosetClothes'>
      <div>
        <MyClothesCreateButton getMyClothesData={getMyClothesData} />
      </div>
      <div className='container-fluid'>
        <div className='row'>
          {clothesDatas.map((clothesData) => (
            <div className='col-2 mt-3' key={clothesData.clothesId}>
              <MyClosetClothesItem
                clothesData={clothesData}
                getMyClothesData={getMyClothesData}
              />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

import React from 'react';
import MyStyleClothesItem from './MySyleClothesItem';

function AllClothes({ clothes }) {
  // const [myClothes, setMyClothes] = useState([]);
  const clothesDatas = [
    {
      category: 10,
      clothesId: 1,
      color: 'red',
      count: 0,
      createDate: '2022-01-19T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-19T08:28:17.455Z',
      url: '상의.jfif',
      userId: 1,
    },
    {
      category: 20,
      clothesId: 2,
      color: 'red',
      count: 0,
      createDate: '2022-01-20T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-20T08:28:17.455Z',
      url: '하의.jfif',
      userId: 1,
    },
    {
      category: 20,
      clothesId: 3,
      color: 'red',
      count: 0,
      createDate: '2022-01-20T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-20T08:28:17.455Z',
      url: 'logo.png',
      userId: 1,
    },
    {
      category: 20,
      clothesId: 4,
      color: 'red',
      count: 0,
      createDate: '2022-01-20T08:28:17.455Z',
      dislikePoint: 0,
      favorite: 0,
      material: 'string',
      season: 0,
      size: 0,
      updateDate: '2022-01-20T08:28:17.455Z',
      url: 'logo2.png',
      userId: 1,
    },
  ];

  return (
    <div className='container-fluid'>
      모든 옷
      <div className='row'>
        {clothesDatas.map((clothesData) => (
          <div className='col-4 mt-3' key={clothesData.clothesId}>
            {/* <div className='card h-100'>
              <div className='card-body'>
                <img
                  className='MyClosetClothesItemImg'
                  src={require(`${clothesData.url}`)}
                  alt={clothesData.clothesId}
                  style={{
                    maxWidth: '100%',
                    maxHeight: '100%',
                  }}
                />
              </div>
            </div> */}
            <MyStyleClothesItem clothesData={clothesData} />
          </div>
        ))}
      </div>
    </div>
  );
}

export default AllClothes;

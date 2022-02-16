import React, { useState } from 'react';
import axios from 'axios';
import { Button } from 'react-bootstrap';

function FriendStyleCreateModalBody({ saveClothesId, handleClose, getFriendsStyleList }) {
  const [selectedImg, setSelectedImg] = useState();
  const imgHandleChange = (e) => {
    console.log(e.target.files);
    setSelectedImg(e.target.files[0]);
  };

  const createStyle = () => {
    let clothesList = '';
    for (let i = 0; i < saveClothesId.length - 1; ++i) {
      clothesList += saveClothesId[i].clothesId + ',';
    }
    clothesList += saveClothesId[saveClothesId.length - 1].clothesId;

    const formData = new FormData();
    formData.append('photo', selectedImg);
    formData.append('clothesList', clothesList);

    console.log(saveClothesId);
    console.log(clothesList);

    axios
      .post('http://i6d104.p.ssafy.io:9999/api/style/add', formData, {
        headers: {
          'X-AUTH-TOKEN': localStorage.getItem('JWT'),
          // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
        },
      })
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          alert('추천되었습니다.');
          getFriendsStyleList();
          handleClose();
        }
      })
      .catch((err) => {
        console.log('실패');
      });
  };

  return (
    <div>
      <div>
        <h2>스타일 추천</h2>
      </div>
      {selectedImg && (
        <img
          alt='스타일 룩'
          src={selectedImg}
          style={{ margin: 'auto', width: '400px', height: '400px' }}
        />
      )}
      <input
        type='file'
        id='inputImg'
        accept='image/*'
        onChange={imgHandleChange}
      ></input>
      <div style={{ marginTop: '30px', float: 'right' }}>
        <Button variant='primary' onClick={createStyle}>
          추천
        </Button>
      </div>
    </div>
  );
}

export default FriendStyleCreateModalBody;

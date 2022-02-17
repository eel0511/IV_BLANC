import React, { useState } from 'react';
import axios from 'axios';
import './MyStyleImg.css';
import Spinner from '../Spinner';

function MyStyleCreateModalBody({ saveClothesId, handleClose, getStyleLook }) {
  const [selectedImg, setSelectedImg] = useState();
  const [previewImg, setPreviewImg] = useState();
  const [loading, setLoading] = useState(false);

  const imgHandleChange = (e) => {
    console.log(e.target.files);
    setSelectedImg(e.target.files[0]);
    setPreviewImg(URL.createObjectURL(e.target.files[0]));
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

    setLoading(true);

    axios
      .post('http://i6d104.p.ssafy.io:9999/api/style/add', formData, {
        headers: {
          'X-AUTH-TOKEN': localStorage.getItem('JWT'),
          // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
        },
      })
      .then((response) => {
        console.log(response);
        if (response.status === 200 && response.data.output === 1) {
          alert('등록되었습니다.');
          handleClose();
          setLoading(false);

          getStyleLook();
        } else if (response.status === 200 && response.data.output === 0) {
          alert(response.data.data);
          setLoading(false);
        } else {
          alert(response.data.data);
          setLoading(false);
        }
      })
      .catch((err) => {
        console.log('실패');
        setLoading(false);
      });
  };

  return (
    <>
      {loading ? (
        <Spinner />
      ) : (
        <div>
          <div>
            <h2>스타일 등록</h2>
          </div>
          {selectedImg && (
            <img alt='스타일 룩' src={previewImg} style={{ margin: 'auto' }} />
          )}

          <input
            type='file'
            id='inputImg'
            accept='image/*'
            onChange={imgHandleChange}
          ></input>
          <div className='Look' style={{ marginTop: '30px', float: 'right' }}>
            <button
              type='button'
              className='btn btn-danger'
              onClick={createStyle}
            >
              등록
            </button>
          </div>
        </div>
      )}
    </>
  );
}

export default MyStyleCreateModalBody;

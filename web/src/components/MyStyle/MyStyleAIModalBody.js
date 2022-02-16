import React, { useState } from 'react';
import axios from 'axios';
import './MyStyleImg.css';

function MyStyleAIModalBody() {
  const [selectedImg, setSelectedImg] = useState();
  const [previewImg, setPreviewImg] = useState();
  const [AIurl, setAIurl] = useState('');

  const imgHandleChange = (e) => {
    console.log(e.target.files);
    setSelectedImg(e.target.files[0]);
    setPreviewImg(URL.createObjectURL(e.target.files[0]));
  };

  const createStyle = () => {
    console.log(selectedImg);
    if (selectedImg === undefined) {
      alert('사진을 등록해주세요!');
      return;
    }

    const formData = new FormData();
    formData.append('photo', selectedImg);

    // 임시 데이터
    // const data =
    //   'https://storage.googleapis.com/iv-blanc.appspot.com/results/su/08909_logo.png';
    // setAIurl(data);

    const token = localStorage.getItem('JWT');
    // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
    axios
      .post('http://i6d104.p.ssafy.io:9999/api/clothes/beta', formData, {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        setAIurl(response.data.data);
      })
      .catch((err) => {
        console.log('실패');
      });
  };

  return (
    <div>
      <div>
        <h2>AI 스타일링</h2>
      </div>
      {selectedImg && (
        <img
          alt='적용할 옷'
          src={previewImg}
          style={{ margin: 'auto', width: '400px', height: '400px' }}
        />
      )}

      <div>
        <input
          type='file'
          id='inputImg'
          accept='image/*'
          onChange={imgHandleChange}
        ></input>
      </div>

      <div className='Look' style={{ float: 'left' }}>
        <button
          type='button'
          className='btn btn-danger'
          onClick={createStyle}
          style={{ marginTop: '20px' }}
        >
          AI 스타일링 확인
        </button>
      </div>

      <div>
        {AIurl && (
          <img
            alt='AI 스타일링 적용'
            src={`${AIurl}`}
            style={{ marginTop: '30px' }}
          />
        )}
      </div>
    </div>
  );
}

export default MyStyleAIModalBody;

import React, { useState } from 'react';
import axios from 'axios';
import { Button, Container, Row, Col } from 'react-bootstrap';

function MyStyleAIModalBody() {
  const [selectedImg, setSelectedImg] = useState();
  const [AIurl, setAIurl] = useState('');

  const imgHandleChange = (e) => {
    console.log(e.target.files);
    setSelectedImg(URL.createObjectURL(e.target.files[0]));
  };

  const createStyle = () => {
    const formData = new FormData();
    formData.append('photo', selectedImg);

    const data =
      'https://storage.googleapis.com/iv-blanc.appspot.com/results/su/08909_logo.png';
    setAIurl(data);

    // const token =
    //   'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
    // axios
    //   .post('http://i6d104.p.ssafy.io:9999/api/clothes/beta', formData, {
    //     headers: {
    //       'X-AUTH-TOKEN': `${token}`,
    //     },
    //   })
    //   .then((response) => {
    //     console.log(response);
    //     setAIurl(response.data);
    //   })
    //   .catch((err) => {
    //     console.log('실패');
    //   });
  };

  return (
    <div>
      <div>
        <h2>AI 스타일링</h2>
      </div>
      {selectedImg && (
        <img
          alt='적용할 옷'
          src={selectedImg}
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

      <div style={{ float: 'left' }}>
        <Button
          variant='primary'
          onClick={createStyle}
          style={{ marginTop: '20px' }}
        >
          AI 스타일링 확인
        </Button>
      </div>

      {selectedImg && AIurl && (
        <img
          alt='AI 스타일링 적용'
          src={AIurl}
          style={{ marginTop: '30px', width: '400px', height: '400px' }}
        />
      )}
    </div>
  );
}

export default MyStyleAIModalBody;

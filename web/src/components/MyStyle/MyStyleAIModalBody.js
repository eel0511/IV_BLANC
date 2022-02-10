import React, { useState } from 'react';
import axios from 'axios';
import { Button, Container, Row, Col } from 'react-bootstrap';

function MyStyleAIModalBody() {
  const [selectedImg, setSelectedImg] = useState();
  const imgHandleChange = (e) => {
    console.log(e.target.files);
    setSelectedImg(URL.createObjectURL(e.target.files[0]));
  };

  const createStyle = () => {
    const formData = new FormData();
    formData.append('photo', selectedImg);

    axios
      .post('http://i6d104.p.ssafy.io:9999/api/style/add', formData, {
        headers: {
          'X-AUTH-TOKEN':
            'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
        },
      })
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          alert('등록되었습니다.');
        }
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
      {/* <Container>
        <Row>
          <Col>
            <Button
              variant='primary'
              style={{ float: 'left' }}
              onClick={createStyle}
            >
              확인
            </Button>
          </Col>
          <Col xs={7}></Col>
          <Col>
            <Button variant='secondary' onClick={handleClose}>
              Close
            </Button>
          </Col>
        </Row>
      </Container> */}
      <div style={{ marginTop: '30px', float: 'right' }}>
        <Button variant='primary' onClick={createStyle}>
          확인
        </Button>
      </div>
    </div>
  );
}

export default MyStyleAIModalBody;

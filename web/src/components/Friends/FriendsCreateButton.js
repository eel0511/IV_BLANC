import React, { useState } from 'react';
import './Friends.css';
import { Modal, Button } from 'react-bootstrap';
import axios from 'axios';

export default function FriendsCreateButton() {
  const [friendName, setFriendName] = useState('');
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const friendNameHandleChange = (e) => {
    setFriendName(e.target.value);
    // console.log(friendName)
  };

  const token = localStorage.getItem('JWT');
  // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY';
  const userEmail = localStorage.getItem('email');
  const friendRequest = () => {
    axios
      .post('http://i6d104.p.ssafy.io:9999/api/friend/request', {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
        applicant: `${userEmail}`, // 로그인한 사용자 email
        friendName: `${friendName}`,
      })
      .then((response) => {
        console.log(response.data);
        alert('친구요청 보내기 성공');
        handleClose();
      });
  };

  return (
    <>
      <img
        className='friedsCreateButton'
        src={require('../../assets/친구등록.png')}
        alt='Create'
        style={{ width: '100px', height: '100px', color: '#ed6991' }}
        onClick={handleShow}
      />
      <Modal show={show} onHide={handleClose} centered='true'>
        <Modal.Header closeButton>
          <Modal.Title>친구 추가</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form>
            <label htmlFor='friendName' className='form-label'>
              친구 이메일
            </label>
            <input
              id='friendName'
              className='form-control'
              type='email'
              placeholder='name@example.com'
              onChange={friendNameHandleChange}
            />
          </form>
        </Modal.Body>
        <Modal.Footer>
          <Button
            className='btn btn-danger'
            onClick={friendRequest}
            style={{ backgroundColor: '#ed6991', border: 'none' }}
          >
            요청
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

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

  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDM4NTQ1MDIsImV4cCI6MTY0NjQ0NjUwMn0.s4B6viyO_tR8lZMUdxW62u82uT08ZltwgEBpuvTBZOQ';
  const friendRequest = () => {
    axios
      .post('http://i6d104.p.ssafy.io:9999/api/friend/request',
      {
        headers: {
          "Authorization": `Bearer ${token}`
        },
        data: {
          applicant: 'user',
          friendName: {friendName}
        }
      })
      .then((response) => {
        console.log(response.data);
        handleClose();
      });
  };

  return (
    <>
      <img
        className='friedsCreateButton'
        src={require('../../assets/친구등록.png')}
        alt='Create'
        style={{ width: '100px', height: '100px' }}
        onClick={handleShow}
      />
      <Modal show={show} onHide={handleClose} centered='true'>
        <Modal.Header closeButton>
          <Modal.Title>친구 추가</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form>
            <label htmlFor='friendName' className='form-label'>
              친구 이메일 :
            </label>
            <input
              id='friendName'
              className='form-control'
              type='email'
              placeholder='name@example.com'
              onChange={friendNameHandleChange}
            />
            <Button onClick={friendRequest}>요청</Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}

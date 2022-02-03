import React, { useState } from 'react';
import './Friends.css';
import { Modal, Button } from 'react-bootstrap';

export default function FriendsCreateButton() {
  const [friendName, setFriendName] = useState('');
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const friendNameHandleChange = (e) => {
    setFriendName(e.target.value);
    // console.log(friendName)
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
            <Button>요청</Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}

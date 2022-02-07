import React, { useState } from 'react';
import {InputGroup, FormControl, Form} from 'react-bootstrap'

export default function HistoryCreateModalBody() {
  const [files, setFiles] = useState('');

  const onLoadFile = (e) => {
    const file = e.target.files;
    console.log(file);
    setFiles(file);
  };

  return (
    <form>
      <div className="row">
          <div className="col-md-5">
              <Form.Group controlId="dob">
                  <Form.Label>Select Date</Form.Label>
                  <Form.Control type="date" name="dob" placeholder="Date of Birth" />
              </Form.Group>
          </div>
      </div>
      <Form.Label>Title</Form.Label>
      <InputGroup className="mb-3">
        <FormControl />
      </InputGroup>

      <InputGroup>
      <InputGroup.Text>Comment</InputGroup.Text>
      <FormControl as="textarea" aria-label="With textarea" />
      </InputGroup>
      <hr/>
      <div className="upload__wrap">
        <div className="custom__img">
          <strong>업로드된 이미지</strong>
          <div className='img__wrap'>
            <img src='' alt='' />
          </div>
        </div>

        <form className='upload__input'>
          <input type="file" id='image' accept='img/*' onChange={onLoadFile} />
        </form>
      </div>
    </form>
  );
}

import React, { useState } from "react";
import { InputGroup, FormControl, Form, Button, Row, Col } from "react-bootstrap";
import axios from "axios";
import StyleSelectButton from "./StyleSelectButton";

export default function HistoryCreateModalBody() {
  const [files, setFiles] = useState("");

  const onLoadFile = (e) => {
    const file = e.target.files;
    console.log(file);
    setFiles(file);
  };

  const [selectedImg, setSelectedImg] = useState();
  const [selectedStyle, setSelectedStyle] = useState();
  const [selectedDate, setSelectedDate] = useState();
  const [selectedSubject, setSelectedSubject] = useState();
  const [selectedText, setSelectedText] = useState();

  const imgHandleChange = (e) => {
    console.log(e.target.files);
    setSelectedImg(e.target.files[0]);
  };
  const styleHandleChange = (e) => {
    console.log(e.target.value);
    setSelectedImg(e.target.value);
  };
  const dateHandleChange = (e) => {
    setSelectedDate(e.target.value);
    console.log(e.target.value);
  };
  const subjectHandleChange = (e) => {
    setSelectedSubject(e.target.value);
    console.log(e.target.value);
  };
  const textHandleChange = (e) => {
    setSelectedText(e.target.value);
    console.log(e.target.value);
  };

  const getMyStylesData = () => {
    axios
      .get("http://i6d104.p.ssafy.io:9999/api/style/finduserstyle", {
        headers: {
          "X-AUTH-TOKEN": `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY`,
        },
      })
      .then((response) => {
        // console.log(response.data.data);
        // setSelectedStyle(response.data.data.url);
        console.log(response.data.data);
        console.log("성공");
        setSelectedStyle(response.data.data);
      });
  };

  const createHistories = () => {
    const formData = new FormData();
    formData.append("photoList", selectedImg);
    formData.append("styleId", Number(selectedStyle.styleId));
    formData.append("date", selectedDate);
    formData.append("subject", selectedSubject);
    formData.append("text", selectedText);

    axios
      .post("http://i6d104.p.ssafy.io:9999/api/history/add", formData, {
        headers: {
          "X-AUTH-TOKEN":
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY",
        },
      })
      .then((response) => {
        console.log(response);
      })
      .catch((err) => {
        console.log("실패");
      });
  };

  const getImg = (data) => {
    console.log(data);
    setSelectedStyle(data);
  };

  return (
    <Form
      style={{ maxWidth: '100%', width: 'auto'}}
    >
      <Row>
        <Col xs={4} md={4}>
        
          <Form.Group controlId='dob'>
            <Form.Label>Select Date</Form.Label>
            <Form.Control
              type='date'
              name='dob'
              placeholder='Date of Birth'
              onChange={dateHandleChange}
            />
          </Form.Group>
        
        <Form.Label>Title</Form.Label>
      <InputGroup className='mb-3'>
        <FormControl onChange={subjectHandleChange} />
      </InputGroup>

      <InputGroup>
        <InputGroup.Text>Comment</InputGroup.Text>
        <FormControl
          rows={12}
          as='textarea'
          aria-label='With textarea'
          onChange={textHandleChange}
        />
      </InputGroup>
      </Col>

      <Col xs={4} md={4}>
      <div className='d-flex flex-column align-content-center justify-content-center'>
        <div
          style={{ marginLeft: "23%", marginBottom: "2%",}}
        >
          <h3>스타일가져오기</h3>
        </div>
        <div>
          {selectedStyle && (
            <img
              alt={selectedStyle.styleId}
              src={selectedStyle.url}
              date={selectedStyle.createDate}
              madeby={selectedStyle.madeby}
              style={{ marginLeft: "2%", maxWidth: "300px", maxHeight: "400px" }}
              // onChange={styleHandleChange}
            />
          )}
          <div 
            style={{ marginLeft: "30%", marginBottom: "2%",}}
          >
            <StyleSelectButton getImg={getImg} />
          </div>
        </div>
      </div>

      </Col>

      <Col xs={4} md={4}>
      <h3
        style={{ marginLeft: "18%", }}
      >이미지 미리보기</h3>
      <table>
        <tbody>
          <tr>
            <td>
              <div>
                {selectedImg && (
                  <img
                    alt='sample'
                    src={URL.createObjectURL(selectedImg)}
                    style={{
                      marginLeft: "2%",
                      width: "300px",
                      height: "400px",
                    }}
                  />
                )}
                <div
                  style={{
                    alignItems: "center",
                    justifyContent: "center",
                    display: "flex",
                  }}
                >
                  
                  <input
                    style={{ marginLeft: "26%", marginTop: "10%", backgroundColor: '#ed6991',
                    color: 'white',}}
                    name='imgUpload'
                    type='file'
                    accept='image/*'
                    onChange={imgHandleChange}
                  />
                  {/* <button 
              style={{ backgroundColor: "gray", color: "white", width: "55px", height: "40px", cursor: "pointer", }} 
              onClick={() => deleteFileImage()} > 삭제 </button>  */}
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      </Col>
      </Row>
      <hr></hr>
      <Button 
      style={{ marginLeft: "26%", marginTop: "1%", backgroundColor: '#662d91',
      color: 'white', borderStyle: 'none' , fontSize: '1.2rem', padding: '1% 20%'}}
      onClick={createHistories}>
        등록
      </Button>
    </Form>
  );
}

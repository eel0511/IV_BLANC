import React, { useState, useEffect } from "react";
import axios from "axios";
import { Carousel, Badge } from "react-bootstrap";
import styled from "styled-components";
import moment from "moment";

export default function HistoryCreateModalBody({ getStyleLook }) {
  const [selectedStyles, setSelectedStyles] = useState([]);

  useEffect(() => {
    axios
      .get("http://i6d104.p.ssafy.io:9999/api/style/finduserstyle", {
        headers: {
          "X-AUTH-TOKEN": `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        console.log("성공");
        setSelectedStyles(response.data.data);
      });
  }, []);

  const styleHandleChange = (e) => {
    e.preventDefault();
    console.log(e.target);
    console.log(e.target.src);
    console.log(e.target.alt);
    console.log(e.target.createDate);
    const data = {
      url: e.target.src,
      styleId: e.target.alt,
      date: e.target.createDate,
      madeby: e.target.madeby
    };
    getStyleLook(data);
    // console.log(e.target.files);
    // setSelectedStyles(e.target.files[0]);
  };

  return (
    <StyleCarousel className="">
      <Carousel className="">
      {selectedStyles.map((selectStyle) => (
          <Carousel.Item key={selectStyle.styleId} className="">
            <div className="date"><Badge bg="warning" text="dark">{moment(selectStyle.createDate).format("YYYY-MM-DD HH:mm:ss")}</Badge></div>
            <h3 className="madeby"><Badge bg="success" text="dark">Made By : {selectStyle.madeby}</Badge></h3>
            <img
              className="img"
              onClick={styleHandleChange}
              src={selectStyle.url}
              alt={selectStyle.styleId}
              style={{
                width: '60%',
                height: '50%',
                objectFit: 'fill',
                borderRadius: '1rem',

              }}
            />
          </Carousel.Item>
      ))}
      </Carousel>
    </StyleCarousel>
  );
}

const StyleCarousel = styled.div`
  .carousel {
    background-color: #eb93c9;
  }
  .date {
    margin-left: 150px;
    margin-top: 10px;
    margin-bottom: 10px;
    font-size: 1.2rem;
  }
  .madeby {
    margin-left: 120px;
  }
  .img 
{
  width: 100%;
  margin-left: 90px;
  margin-bottom: 50px;
}

.img:hover {
    border: 10px solid #a35ac5;
    /* transform: scale(1.1); */
}
`;

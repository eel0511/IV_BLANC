import React, { useState, useEffect } from "react";
import { Modal, Button, ListGroup, Container, Row, Col } from "react-bootstrap";
import moment from "moment";
import Checkbox from "@mui/material/Checkbox";
import FavoriteBorder from "@mui/icons-material/FavoriteBorder";
import Favorite from "@mui/icons-material/Favorite";
import axios from "axios";
import codeData from "../../codeData.json";
import "./MyCloset.css";
import DetailClothes from "./MyClosetDetailItem";

const label = { inputProps: { "aria-label": "Checkbox demo" } };

export default function MyClosetClothesItem({ clothesData }) {
  const [show, setShow] = useState(false);
  const [date, setDate] = useState(clothesData.createDate);
  const [favoriteChecked, setFavoriteChecked] = useState(false);

  // 종류, 색깔, 소재, 계절
  const [category, setCategory] = useState("");
  const [color, setColor] = useState("");
  const [material, setMaterial] = useState("");
  const [season, setSeason] = useState("");

  useEffect(() => {
    setDate(moment(clothesData.createDate).format("YYYY-MM-DD HH:mm:ss"));

    let mainCategory = "";
    switch (parseInt(clothesData.category / 10)) {
      case 1:
        mainCategory = codeData["category"].상의;
        break;
      case 2:
        mainCategory = codeData["category"].하의;
        break;
      case 4:
        mainCategory = codeData["category"].아우터;
        break;
      case 5:
        mainCategory = codeData["category"].신발;
        break;
      case 6:
        mainCategory = codeData["category"].가방;
        break;
      case 7:
        mainCategory = codeData["category"].모자;
        break;
      // case 10:
      //   mainCategory = codeData["category"].티셔츠;
      //   break;
      // case 11:
      //   mainCategory = codeData["category"].셔츠;
      //   break;
      // case 12:
      //   mainCategory = codeData["category"].후드;
      //   break;
      // case 13:
      //   mainCategory = codeData["category"].맨투맨;
      //   break;
      // case 14:
      //   mainCategory = codeData["category"].블라우스;
      //   break;
      // case 15:
      //   mainCategory = codeData["category"].크롭탑;
      //   break;
      // case 16:
      //   mainCategory = codeData["category"].드레스;
      //   break;
      // case 17:
      //   mainCategory = codeData["category"].상의기타;
      //   break;
      // case 20:
      //   mainCategory = codeData["category"].청바지;
      //   break;
      // case 21:
      //   mainCategory = codeData["category"].면바지;
      //   break;
      // case 22:
      //   mainCategory = codeData["category"].반바지;
      //   break;
      // case 23:
      //   mainCategory = codeData["category"].정장바지;
      //   break;
      // case 24:
      //   mainCategory = codeData["category"].운동복;
      //   break;
      // case 25:
      //   mainCategory = codeData["category"].스커트;
      //   break;
      // case 26:
      //   mainCategory = codeData["category"].레깅스;
      //   break;
      // case 27:
      //   mainCategory = codeData["category"].하의기타;
      //   break;
      // case 30:
      //   mainCategory = codeData["category"].코트;
      //   break;
      // case 31:
      //   mainCategory = codeData["category"].자켓;
      //   break;
      // case 32:
      //   mainCategory = codeData["category"].패딩;
      //   break;
      // case 33:
      //   mainCategory = codeData["category"].조끼;
      //   break;
      // case 34:
      //   mainCategory = codeData["category"].가디건;
      //   break;
      // case 35:
      //   mainCategory = codeData["category"].아우터기타;
      //   break;
      // case 40:
      //   mainCategory = codeData["category"].스니커즈;
      //   break;
      // case 41:
      //   mainCategory = codeData["category"].운동화;
      //   break;
      // case 42:
      //   mainCategory = codeData["category"].로퍼플랫;
      //   break;
      // case 43:
      //   mainCategory = codeData["category"].부츠;
      //   break;
      // case 44:
      //   mainCategory = codeData["category"].샌들슬리퍼;
      //   break;
      // case 45:
      //   mainCategory = codeData["category"].힐;
      //   break;
      // case 46:
      //   mainCategory = codeData["category"].신발기타;
      //   break;
      // case 50:
      //   mainCategory = codeData["category"].백팩;
      //   break;
      // case 51:
      //   mainCategory = codeData["category"].크로스백팩;
      //   break;
      // case 52:
      //   mainCategory = codeData["category"].숄더백;
      //   break;
      // case 53:
      //   mainCategory = codeData["category"].핸드백;
      //   break;
      // case 54:
      //   mainCategory = codeData["category"].가방기타;
      //   break;
      default:
        mainCategory = codeData["category"].기타;
        break;
    }

    let mainColor = "";
    switch (clothesData.colors) {
      case "#C14D49":
        mainColor = codeData["colors"].빨강;
        break;
      case "#E0633B":
        mainColor = codeData["colors"].주황;
        break;
      case "#F6EFDF":
        mainColor = codeData["colors"].베이지;
        break;
      case "#F6D766":
        mainColor = codeData["colors"].노랑;
        break;
      case "#ECC6C0":
        mainColor = codeData["colors"].핑크;
        break;
      case "#35774D":
        mainColor = codeData["colors"].초록;
        break;
      case "#CDDEEC":
        mainColor = codeData["colors"].하늘;
        break;
      case "#27476F":
        mainColor = codeData["colors"].파랑;
        break;
      case "#6D4859":
        mainColor = codeData["colors"].보라;
        break;
      case "#5E4B48":
        mainColor = codeData["colors"].갈색;
        break;
      case "#ABAEB6":
        mainColor = codeData["colors"].회색;
        break;
      case "#9C7E3A":
        mainColor = codeData["colors"].네이비;
        break;
      case "#FFFFFF":
        mainColor = codeData["colors"].흰색;
        break;
      default:
        mainColor = codeData["colors"].검정;
        break;
    }

    let mainMaterial = "";
    switch (clothesData.material / 10) {
      case 1:
        mainMaterial = codeData["material"].면;
        break;
      case 2:
        mainMaterial = codeData["material"].린넨;
        break;
      case 3:
        mainMaterial = codeData["material"].폴리에스테르;
        break;
      case 4:
        mainMaterial = codeData["material"].니트올;
        break;
      case 5:
        mainMaterial = codeData["material"].퍼;
        break;
      case 6:
        mainMaterial = codeData["material"].트위드;
        break;
      case 7:
        mainMaterial = codeData["material"].나일론;
        break;
      case 8:
        mainMaterial = codeData["material"].데님;
        break;
      case 9:
        mainMaterial = codeData["material"].가죽;
        break;
      case 10:
        mainMaterial = codeData["material"].스웨이드;
        break;
      case 11:
        mainMaterial = codeData["material"].벨벳;
        break;
      case 12:
        mainMaterial = codeData["material"].쉬폰;
        break;
      case 13:
        mainMaterial = codeData["material"].실크;
        break;
      case 14:
        mainMaterial = codeData["material"].코듀로이;
        break;
      case 15:
        mainMaterial = codeData["material"].메탈릭;
        break;
      default:
        mainMaterial = codeData["material"].기타;
        break;
    }

    let mainSeason = "";
    switch (clothesData.season / 10) {
      case 1:
        mainSeason = codeData["season"].봄;
        break;
      case 2:
        mainSeason = codeData["season"].여름;
        break;
      case 3:
        mainSeason = codeData["season"].가을;
        break;
      default:
        mainColor = codeData["season"].겨울;
        break;
    }

    // console.log(mainCategory);
    // console.log(Object.keys(mainCategory).find((key) => mainCategory[key] === clothesData.category));
    setCategory(
      Object.keys(mainCategory).find(
        (key) => mainCategory[key] === clothesData.category
      )
    );
    setColor(
      Object.keys(mainColor).find(
        (key) => mainColor[key] === clothesData.color
      )
    );
    setMaterial(
      Object.keys(mainMaterial).find(
        (key) => mainMaterial[key] === clothesData.material
      )
    );
    setSeason(
      Object.keys(mainSeason).find(
        (key) => mainSeason[key] === clothesData.season
      )
    );

    // 즐겨찾기 데이터를 받아와서 favorite === 1이면 setFavoriteChecked 수정
    if (clothesData.favorite === 1) {
      setFavoriteChecked(true);
    }
  }, []);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleChange = async (e) => {
    // console.log(e.target.checked);
    const favoriteCurrent = e.target.checked;
    setFavoriteChecked(favoriteCurrent);
    // console.log(favoriteCurrent);

    // 체크했을 때 서버에 즐겨찾기 등록 정보 전달
    // favoriteChecked===true이면 추가, 아니면 삭제
    // 추후에 토큰 사용한 백엔드 연동 구현
    if (favoriteCurrent) {
      try {
        await axios
          .put('http://i6d104.p.ssafy.io:9999/api/clothes/addfavorite', {
            headers: {
              'X-AUTH-TOKEN':
                'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              clothesId: `${clothesData.clothesId}`
            }
          })
          .then((res) => {
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              alert(res.data.msg);
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err.response.data);
      }
    } else {
      try {
        await axios
          .put('http://i6d104.p.ssafy.io:9999/api/clothes/deletefavorite', {
            headers: {
              'X-AUTH-TOKEN':
                'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              clothesId: `${clothesData.clothesId}`
            }
          })
          .then((res) => {
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              alert(res.data.msg);
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err.response.data);
      }
    }
  };

  const handleDelete = async (e) => {
    e.preventDefault();

    if (window.confirm("진짜 삭제하시겠습니까?")) {
      // 삭제 기능 구현
      // 토큰 포함 버전으로 바꿔야 함
      try {
        await axios
          .delete('http://i6d104.p.ssafy.io:9999/api/clothes/deleteById', {
            headers: {
              'X-AUTH-TOKEN':
                'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              clothesId: `${clothesData.clothesId}`,
            },
          })
          .then((res) => {
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              console.log(res.data.msg);
              alert('삭제되었습니다.');
              setShow(false);
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err.response.data);
      }
    } else {
      alert("취소합니다.");
    }
  };

  return (
    <div className='card h-100'>
      <div className='card-body'>
        <img
          className='MyClosetClothesItemImg'
          src={`${clothesData.url}`}
          alt={clothesData.clothesId}
          style={{ maxWidth: '100%', maxHeight: '100%' }}
          onClick={handleShow}
        />

        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Detail</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Container>
              <Row>
                <Col md={{ span: 7, offset: 2 }}>
                  <img
                    src={`${clothesData.url}`}
                    alt={clothesData.clothesId}
                    style={{ maxWidth: '100%', maxHeight: '100%' }}
                  />
                </Col>
                <Col md={{ span: 1, offset: 1 }}>
                  <Checkbox
                    {...label}
                    icon={<FavoriteBorder />}
                    checkedIcon={<Favorite />}
                    checked={favoriteChecked}
                    onChange={handleChange}
                    color='error'
                  />
                </Col>
              </Row>
            </Container>

            <ListGroup variant='flush'>
              <DetailClothes item='종류' value={category} />
              <DetailClothes item='색깔' value={color} />
              <DetailClothes item='소재' value={material} />
              <DetailClothes item='계절' value={season} />
              <DetailClothes item='입은 횟수' value={clothesData.count} />
              <DetailClothes item='좋아요' value={clothesData.likePoint} />
              <DetailClothes item='싫어요' value={clothesData.dislikePoint} />
              <DetailClothes item='등록날짜' value={date} />
            </ListGroup>
          </Modal.Body>
          <Modal.Footer>
            <Container>
              <Row>
                <Col>
                  <Button
                    variant='danger'
                    style={{ float: 'left' }}
                    onClick={handleDelete}
                  >
                    삭제
                  </Button>
                </Col>
                <Col xs={7}></Col>
                <Col>
                  <Button variant='secondary' onClick={handleClose}>
                    Close
                  </Button>
                </Col>
              </Row>
            </Container>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
}

import React, { useState } from "react";
import codeData from "../../codeData.json";
import { Button, Row, Col } from "react-bootstrap";
import axios from "axios";


export default function MyClothesCreateModalBody({
  handleClose,
  getMyClothesData,
}) {
  const [selectedColor, setSelectedColor] = useState("#C14D49");
  const [selectedMaterial, setSelectedMaterial] = useState(1);
  const [selectedMainCategory, setSelectedMainCategory] = useState("상의");
  const [selectedSeason, setSelectedSeason] = useState(1);
  const [selectedSize, setSelectedSize] = useState();
  const [selectedSubCategory, setSelectedSubCategory] = useState();
  const [selectedImg, setSelectedImg] = useState();

  const colorHandleChange = (e) => {
    setSelectedColor(e.target.value);
    // console.log(e.target.value);
  };

  const materialHandleChange = (e) => {
    setSelectedMaterial(e.target.value);
    // console.log(e.target.value);
  };

  const seasonHandleChange = (e) => {
    setSelectedSeason(e.target.value);
    // console.log(e.target.value);
  };

  const sizeHandleChange = (e) => {
    setSelectedSize(e.target.value);
    // console.log(e.target.value);
  };

  const mainCategoryHandleChange = (e) => {
    setSelectedMainCategory(e.target.value);
    // console.log(e.target.value);
  };

  const subCategoryHandleChange = (e) => {
    setSelectedSubCategory(e.target.value);
    // console.log(e.target.value);
  };

  const imgHandleChange = (e) => {
    // console.log(e.target.files)
    setSelectedImg(e.target.files[0]);
  };

  const createClothes = () => {
    const formData = new FormData();
    formData.append("photo", selectedImg);
    formData.append("category", Number(selectedSubCategory));
    formData.append("color", selectedColor);
    formData.append("material", Number(selectedMaterial));
    formData.append("season", Number(selectedSeason));
    formData.append("size", Number(selectedSize));

    axios
      .post("http://i6d104.p.ssafy.io:9999/api/clothes/add", formData, {
        headers: {
          "X-AUTH-TOKEN":
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY",
        },
      })
      .then((response) => {
        if (response.status === 200 && response.data.msg === "성공") {
          alert(response.data.msg);
          handleClose();
          getMyClothesData();
        } else {
          alert(response.data.msg);
        }
      })
      .catch((err) => {
        console.log(err.msg);
      });
  };

  return (
    <>
      <Row>
        <Col xs={6} md={6}>
        <h4
          // style={{
          //   marginLeft: "45%",
          // }}
        >사진 등록</h4>
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
                        // marginLeft: "20%",
                        width: "200px",
                        height: "200px",
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
                      // style={{marginLeft: "20%",}}
                      name='imgUpload'
                      type='file'
                      accept='image/*'
                      onChange={imgHandleChange}
                    />
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        </Col>

        <Col xs={6} md={6}>
        <h4>카테고리</h4>
      {Object.entries(codeData["category"]).map((mainCategoryArray, index) => {
        return (
          <React.Fragment key={index}>
            <input
              id={"카테고리" + mainCategoryArray[0]}
              value={mainCategoryArray[0]}
              className='form-check-input'
              type='radio'
              name='mainCategoryGroup'
              defaultChecked={
                selectedMainCategory === mainCategoryArray[0] ? true : false
              }
              onChange={mainCategoryHandleChange}
            />
            <label
              className='form-check-label'
              htmlFor={"카테고리" + mainCategoryArray[0]}
            >
              {mainCategoryArray[0]}
            </label>
          </React.Fragment>
        );
      })}
      <div>
        {Object.entries(codeData["category"]).map(
          (mainCategoryArray, index) => {
            return (
              <React.Fragment key={index}>
                {selectedMainCategory === mainCategoryArray[0] &&
                  Object.entries(mainCategoryArray[1]).map(
                    (subCategoryArray) => {
                      return (
                        <React.Fragment key={subCategoryArray[1]}>
                          <input
                            id={"카테고리" + subCategoryArray[0]}
                            value={subCategoryArray[1]}
                            className='form-check-input'
                            type='radio'
                            name='subCategoryGroup'
                            onChange={subCategoryHandleChange}
                          />
                          <label
                            className='form-check-label'
                            htmlFor={"카테고리" + subCategoryArray[0]}
                          >
                            {subCategoryArray[0]}
                          </label>
                        </React.Fragment>
                      );
                    }
                  )}
              </React.Fragment>
            );
          }
        )}
      </div>
        </Col>
      </Row>
      <hr />
      <Row>
        <Col xs={4} md={4}>
          <h4>색상</h4>
          <div className='container row'>
            {Object.entries(codeData["colors"]).map((colorArray) => {
              return (
                <div key={colorArray[1]} className='col-3 d-flex '>
                  <input
                    id={"색상" + colorArray[1]}
                    value={colorArray[1]}
                    className='form-check-input'
                    type='radio'
                    name='colorGroup'
                    defaultChecked={
                      selectedColor === colorArray[1] ? true : false
                    }
                    onChange={colorHandleChange}
                  />
                  <label
                    className='form-check-label create__colorLabel'
                    htmlFor={"색상" + colorArray[1]}
                    style={{ backgroundColor: colorArray[1] }}
                  ></label>
                </div>
              );
            })}
          </div>
          <Row>
            <Col xs={12} md={12}>
            <h4>사이즈</h4>
            <React.Fragment>
        <input
          type={"number"}
          placeholder='사이즈를 입력해주세요'
          onChange={sizeHandleChange}
        />
      </React.Fragment>

            </Col>

            <Col xs={12} md={12}>
            <h4>시즌</h4>
        {Object.entries(codeData["season"]).map((seasonArray) => {
          return (
            <React.Fragment key={seasonArray[1]}>
              <input
                id={"시즌" + seasonArray[0]}
                value={seasonArray[1]}
                className='form-check-input'
                type='radio'
                name='seasonGroup'
                defaultChecked={
                  selectedSeason === seasonArray[1] ? true : false
                }
                onChange={seasonHandleChange}
              />
              <label
                className='form-check-label'
                htmlFor={"시즌" + seasonArray[0]}
              >
                {seasonArray[0]}
              </label>
            </React.Fragment>
          );
        })}
            </Col>
          </Row>
        </Col>

        <Col xs={8} md={8}>
          <h4>소재</h4>
          <div className='container row'>
            {Object.entries(codeData["material"]).map((materialArray) => {
              const name = materialArray[0]
              const val = name["id"]
              return (
                <div key={materialArray["val"]} className='col-3'>
                  <input
                    id={"소재" + materialArray["val"]}
                    value={materialArray["val"]}
                    className='form-check-input'
                    type='radio'
                    name='materialGroup'
                    defaultChecked={
                      selectedMaterial === materialArray["val"] ? true : false
                    }
                    onChange={materialHandleChange}
                  />
                  <label
                    className='form-check-label'
                    htmlFor={"소재" + materialArray["val"]}
                    style={{ backgroundImage: `url({materialArray["src"]})` }}
                  >
                    {name}
                  </label>
                </div>
              );
            })}
          </div>
        </Col>
      </Row>
      <hr />

      <Button variant='secondary' onClick={createClothes}>
        등록
      </Button>
    </>
  );
}

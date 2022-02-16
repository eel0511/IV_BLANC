import React, { useState } from 'react';
import codeData from '../../codeData.json';
import { Button, Row, Col, Nav, NavDropdown, Tabs, Tab } from 'react-bootstrap';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function MyClothesCreateModalBody({
  handleClose,
  getMyClothesData,
}) {
  const [selectedColor, setSelectedColor] = useState('#C14D49');
  const [selectedMaterial, setSelectedMaterial] = useState(1);
  const [selectedMainCategory, setSelectedMainCategory] = useState('상의');
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
    console.log(e.target.value);
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
  const [key, setKey] = useState('');

  const createClothes = () => {
    const formData = new FormData();
    formData.append('photo', selectedImg);
    formData.append('category', Number(selectedSubCategory));
    formData.append('color', selectedColor);
    formData.append('material', Number(selectedMaterial));
    formData.append('season', Number(selectedSeason));
    formData.append('size', Number(selectedSize));

    axios
      .post('http://i6d104.p.ssafy.io:9999/api/clothes/add', formData, {
        headers: {
          'X-AUTH-TOKEN': localStorage.getItem('JWT'),
        },
      })
      .then((response) => {
        if (response.status === 200 && response.data.msg === '성공') {
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
        <Col xs={5} md={5}>
          <h4
            style={{
              backgroundColor: '#662d91',
              marginLeft: '36%',
              marginRight: '40%',
              color: 'white',
              borderRadius: '15px',
            }}
          >
            사진 등록
          </h4>
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
                          marginLeft: '40%',
                          width: '200px',
                          height: '200px',
                        }}
                      />
                    )}
                    <div
                      style={{
                        alignItems: 'center',
                        justifyContent: 'center',
                        display: 'flex',
                      }}
                    >
                      <input
                        style={{ marginLeft: '60%' }}
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

        <Col xs={7} md={7}>
          <h4
            style={{
              backgroundColor: '#662d91',
              marginLeft: '37%',
              marginRight: '47%',
              color: 'white',
              borderRadius: '15px',
            }}
          >
            카테고리
          </h4>
          <Tabs
            tabClassName='tabs'
            name='mainCategoryGroup'
            activeKey={selectedMainCategory}
            onSelect={(k) => setSelectedMainCategory(k)}
          >
            {Object.entries(codeData['category']).map(
              (mainCategoryArray, index) => {
                return (
                  <Tab
                    tabClassName='tab'
                    key={index}
                    eventKey={mainCategoryArray[0]}
                    title={mainCategoryArray[0]}
                  />
                );
              }
            )}
          </Tabs>
          <div>
            {Object.entries(codeData['category']).map(
              (mainCategoryArray, index) => {
                return (
                  <React.Fragment key={index}>
                    {selectedMainCategory === mainCategoryArray[0] &&
                      Object.entries(mainCategoryArray[1]).map(
                        (subCategoryArray) => {
                          return (
                            <React.Fragment key={subCategoryArray[1]}>
                              <input
                                id={'카테고리' + subCategoryArray[0]}
                                value={subCategoryArray[1]}
                                className='form-check-input'
                                type='radio'
                                name='subCategoryGroup'
                                onChange={subCategoryHandleChange}
                              />
                              <label
                                className='form-check-label'
                                htmlFor={'카테고리' + subCategoryArray[0]}
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
        <Col xs={5} md={5}>
          <h4
            style={{
              backgroundColor: '#662d91',
              marginLeft: '40%',
              marginRight: '47%',
              color: 'white',
              borderRadius: '15px',
            }}
          >
            색상
          </h4>
          <div className='container row'>
            {Object.entries(codeData['colors']).map((colorArray) => {
              return (
                <div key={colorArray[1]} className='col-3 d-flex '>
                  <input
                    id={'색상' + colorArray[1]}
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
                    htmlFor={'색상' + colorArray[1]}
                    style={{
                      backgroundColor: colorArray[1],
                      marginBottom: '10%',
                    }}
                  ></label>
                </div>
              );
            })}
          </div>
          <Row>
            <Col xs={12} md={12}>
              <h4
                style={{
                  backgroundColor: '#662d91',
                  marginLeft: '38%',
                  marginRight: '44%',
                  color: 'white',
                  borderRadius: '15px',
                  marginTop: '2%',
                }}
              >
                사이즈
              </h4>
              <React.Fragment>
                <input
                  type={'number'}
                  placeholder='사이즈를 입력해주세요'
                  onChange={sizeHandleChange}
                  style={{
                    marginLeft: '27%',
                    marginTop: '2%',
                    marginBottom: '2%',
                  }}
                />
              </React.Fragment>
            </Col>

            <Col xs={12} md={12}>
              <h4
                style={{
                  backgroundColor: '#662d91',
                  marginLeft: '40%',
                  marginTop: '2%',
                  marginRight: '47%',
                  color: 'white',
                  borderRadius: '15px',
                }}
              >
                시즌
              </h4>
              {Object.entries(codeData['season']).map((seasonArray) => {
                return (
                  <React.Fragment key={seasonArray[1]}>
                    <input
                      id={'시즌' + seasonArray[0]}
                      style={{
                        marginLeft: '11%',
                      }}
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
                      htmlFor={'시즌' + seasonArray[0]}
                    >
                      {seasonArray[0]}
                    </label>
                  </React.Fragment>
                );
              })}
            </Col>
          </Row>
        </Col>

        <Col xs={7} md={7}>
          <h4
            style={{
              backgroundColor: '#662d91',
              marginLeft: '40%',
              marginRight: '51%',
              color: 'white',
              borderRadius: '15px',
            }}
          >
            소재
          </h4>
          <div className='container row'>
            {Object.entries(codeData['material']).map(
              (materialArray, index) => {
                return (
                  <div
                    key={index}
                    className='col-3 d-flex flex-column align-content-center justify-content-center'
                  >
                    <div>
                      <img
                        src={require(`../../assets/material/${materialArray[1]}`)}
                        alt={materialArray[0]}
                        width={60}
                        height={40}
                        style={{ objectFit: 'cover' }}
                      />
                    </div>
                    <div>
                      <input
                        id={'소재' + materialArray[0]}
                        value={index + 1}
                        className='form-check-input'
                        type='radio'
                        name='materialGroup'
                        defaultChecked={
                          selectedMaterial === materialArray[0] ? true : false
                        }
                        onChange={materialHandleChange}
                        style={{
                          marginBottom: '20%',
                        }}
                      />
                      <label
                        className='form-check-label'
                        htmlFor={'소재' + materialArray[0]}
                        // style={{ backgroundImage: `url({materialImg[1]})` }}
                      >
                        {materialArray[0]}
                      </label>
                    </div>
                  </div>
                );
              }
            )}
          </div>
        </Col>
      </Row>
      <hr />

      <Button
        style={{
          backgroundColor: '#662d91',
          fontSize: '1.2rem',
          marginLeft: '85%',
          color: 'white',
          borderStyle: 'none',
        }}
        onClick={createClothes}
      >
        등록
      </Button>
    </>
  );
}

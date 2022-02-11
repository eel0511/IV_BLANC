import React, { useState } from 'react';
import codeData from '../../codeData.json';
import { Button } from 'react-bootstrap';
import axios from 'axios';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { display } from '@mui/system';

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

  const Label = styled.label`
    display: inline-block;
    width: 40px;
    height: 40px;
    border-radius: 20px;
    border: none;
    margin: 10px;
    background-color: ${(props) => props.color};
    border: 1px solid black;
    h4 {
      font-size: 0.8rem;
      color: red;
    }
  `;

  const ColorSelectorContainer = styled.div`
    display: inline;
    justify-content: space-between;
    width: 500px;
    margin-top: 8px;
    padding: 10px;
    /* input {
    display: none;
  } */
    /* border: 1px solid salmon; */
  `;

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
    formData.append('photo', selectedImg);
    formData.append('category', Number(selectedSubCategory));
    formData.append('color', selectedColor);
    formData.append('material', Number(selectedMaterial));
    formData.append('season', Number(selectedSeason));
    formData.append('size', Number(selectedSize));

    axios
      .post('http://i6d104.p.ssafy.io:9999/api/clothes/add', formData, {
        headers: {
          'X-AUTH-TOKEN':
            'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
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
      <h1>이미지 미리보기</h1>
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
                      margin: 'auto',
                      padding: 'auto',
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
                  <div>
                    <h4>사진 등록</h4>
                  </div>
                  <input
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
      <hr />
      <div className='create__color'>
      <div className='col-3'>
        <h2>색상</h2>
      </div>
      <div className='container row'>
      {Object.entries(codeData['colors']).map((colorArray) => {
        return (
          <div key={colorArray[1]} className='col-3'>
            <input
              id={'색상' + colorArray[1]}
              value={colorArray[1]}
              className='form-check-input'
              type='radio'
              name='colorGroup'
              defaultChecked={selectedColor === colorArray[1] ? true : false}
              onChange={colorHandleChange}
            />
            <label
              className='form-check-label create__colorLabel'
              htmlFor={'색상' + colorArray[1]}
              style={{ backgroundColor: colorArray[1]}}
            >
            </label>
            {/* <Label 
            id={'색상' + colorArray[1]}
            value={colorArray[1]}
            className='form-check-input'
            type='radio'
            name='colorGroup'
            defaultChecked={selectedColor === colorArray[1] ? true : false}
            onChange={colorHandleChange}
            htmlFor={colorArray[0]} 
            color={colorArray[1]} 

            >
            <h4> {colorArray[0]} </h4>
              </Label> */}
          </div>
        );
      })}
      </div>
      </div>
      <hr />
      <div>
        <h2>소재</h2>
      </div>
      {Object.entries(codeData['material']).map((materialArray) => {
        return (
          <React.Fragment key={materialArray[1]}>
            <input
              id={'소재' + materialArray[0]}
              value={materialArray[1]}
              className='form-check-input'
              type='radio'
              name='materialGroup'
              defaultChecked={
                selectedMaterial === materialArray[1] ? true : false
              }
              onChange={materialHandleChange}
            />
            <label
              className='form-check-label'
              htmlFor={'소재' + materialArray[0]}
            >
              {materialArray[0]}
            </label>
          </React.Fragment>
        );
      })}
      <hr />
      <div>
        <h2>시즌</h2>
      </div>
      {Object.entries(codeData['season']).map((seasonArray) => {
        return (
          <React.Fragment key={seasonArray[1]}>
            <input
              id={'시즌' + seasonArray[0]}
              value={seasonArray[1]}
              className='form-check-input'
              type='radio'
              name='seasonGroup'
              defaultChecked={selectedSeason === seasonArray[1] ? true : false}
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
      <hr />
      <div>
        <h2>사이즈</h2>
      </div>
      <React.Fragment>
        <input
          type={'number'}
          placeholder='사이즈를 입력해주세요'
          onChange={sizeHandleChange}
        />
      </React.Fragment>
      <hr />
      <div>
        <h2>카테고리</h2>
      </div>
      {Object.entries(codeData['category']).map((mainCategoryArray, index) => {
        return (
          <React.Fragment key={index}>
            <input
              id={'카테고리' + mainCategoryArray[0]}
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
              htmlFor={'카테고리' + mainCategoryArray[0]}
            >
              {mainCategoryArray[0]}
            </label>
          </React.Fragment>
        );
      })}
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
      {/* <hr />
      <div>
        <h2>사진 등록</h2>
      </div>
      <input
        type='file'
        id='inputImg'
        accept='image/*'
        onChange={imgHandleChange}
      ></input> */}
      <Button variant='secondary' onClick={createClothes}>
        등록
      </Button>
    </>
  );
}

import React, { useState } from 'react';
import codeData from '../../codeData.json';
import { Button } from 'react-bootstrap';
import axios from 'axios';

export default function MyClothesCreateModalBody() {
  const [selectedColor, setSelectedColor] = useState('빨강');
  const [selectedMaterial, setSelectedMaterial] = useState(0);
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
    // console.log(e.target.files[0])
    setSelectedImg(e.target.files[0])
  };

  const createClothes = () => {
    const formData = new FormData();
    formData.append('file', selectedImg);

    axios
      .post('http://119.56.162.61:8888/api/clothes/add', formData,
      {
        params: {
          category: Number(selectedSubCategory),
          color: selectedColor,
          material: Number(selectedMaterial),
          season: Number(selectedSeason),
          size: Number(selectedSize),
        }
      },
      {
        headers: {
          'X-AUTH-TOKEN': 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjIiLCJpYXQiOjE2NDMyNjUzNzAsImV4cCI6MTY0NTg1NzM3MH0.4cg27bxcecL15ItYRFutBZOKKW2P9MJyziWiz8S9_QE'
        }
      }
      )
      .then((response) => {
        console.log(response)
      })
      .catch((err) => {
        console.log('실패')
      })
  };

  return (
    <form className='RadioForm'>
      <div>
        <h2>색상</h2>
      </div>
      {Object.entries(codeData['colors']).map((colorArray) => {
        return (
          <React.Fragment key={colorArray[1]}>
            <input
              id={'색상' + colorArray[0]}
              value={colorArray[0]}
              className='form-check-input'
              type='radio'
              name='colorGroup'
              defaultChecked={selectedColor === colorArray[0] ? true : false}
              onChange={colorHandleChange}
            />
            <label
              className='form-check-label'
              htmlFor={'색상' + colorArray[0]}
            >
              {colorArray[0]}
            </label>
          </React.Fragment>
        );
      })}
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
      <hr />
      <div>
        <h2>사진 등록</h2>
      </div>
      <input
        type='file'
        id='inputImg'
        accept='image/*'
        onChange={imgHandleChange}
      ></input>
      <Button variant='secondary' onClick={createClothes}>
        등록
      </Button>
    </form>
  );
}

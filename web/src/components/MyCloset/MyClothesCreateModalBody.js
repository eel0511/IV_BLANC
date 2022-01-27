import React, { useEffect, useState } from 'react';
import codeData from '../../codeData.json';

export default function MyClothesCreateModalBody({ getClothesData }) {
  const [selectedColor, setselectedColor] = useState('빨강');
  const [selectedMaterial, setselectedMaterial] = useState('면');
  const [selectedMainCategory, setselectedMainCategory] = useState('상의');
  const [selectedSeason, setselectedSeason] = useState('봄');
  const [selectedSize, setselectedSize] = useState();
  const [selectedSubCategory, setselectedSubCategory] = useState();
  const [selectedImg, setselectedImg] = useState();

  const colorHandleChange = (e) => {
    setselectedColor(e.target.value);
    // console.log(e.target.value);
  };

  const materialHandleChange = (e) => {
    setselectedMaterial(e.target.value);
    // console.log(e.target.value);
  };

  const seasonHandleChange = (e) => {
    setselectedSeason(e.target.value);
    // console.log(e.target.value);
  };

  const sizeHandleChange = (e) => {
    setselectedSize(e.target.value);
    // console.log(e.target.value);
  };

  const mainCategoryHandleChange = (e) => {
    setselectedMainCategory(e.target.value);
    // console.log(e.target.value);
  };

  const subCategoryHandleChange = (e) => {
    setselectedSubCategory(e.target.value);
    // console.log(e.target.value);
  };

  const imgHandleChange = (e) => {
    setselectedImg(e.target.file[0]);
    // console.log(e.target.files[0]);
  };

  useEffect(() => {
    getClothesData({
      'category': selectedSubCategory,
      'color': selectedColor,
      'file': selectedImg,
      'material': selectedMaterial,
      'season': selectedSeason,
      'size': selectedSize,
      'userId': 1
    });
  });

  return (
    <form className='RadioForm'>
      <div>
        <h2>색상</h2>
      </div>
      {Object.entries(codeData['colors']).map(colorArray => {
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
            <label className='form-check-label' htmlFor={'색상' + colorArray[0]}>
              {colorArray[0]}
            </label>
          </React.Fragment>
        );
      })}
      <hr/>
      <div>
        <h2>소재</h2>
      </div>
      {Object.entries(codeData['material']).map(materialArray => {
        return (
          <React.Fragment key={materialArray[1]}>
            <input
              id={'소재' + materialArray[0]}
              value={materialArray[0]}
              className='form-check-input'
              type='radio'
              name='materialGroup'
              defaultChecked={selectedMaterial === materialArray[0] ? true : false}
              onChange={materialHandleChange}
            />
            <label className='form-check-label' htmlFor={'소재' + materialArray[0]}>
              {materialArray[0]}
            </label>
          </React.Fragment>
        );
      })}
      <hr/>
      <div>
        <h2>시즌</h2>
      </div>
      {Object.entries(codeData['season']).map(seasonArray => {
        return (
          <React.Fragment key={seasonArray[1]}>
            <input
              id={'시즌' + seasonArray[0]}
              value={seasonArray[0]}
              className='form-check-input'
              type='radio'
              name='seasonGroup'
              defaultChecked={selectedSeason === seasonArray[0] ? true : false}
              onChange={seasonHandleChange}
            />
            <label className='form-check-label' htmlFor={'시즌' + seasonArray[0]}>
              {seasonArray[0]}
            </label>
          </React.Fragment>
        );
      })}
      <hr/>
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
      <hr/>
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
              defaultChecked={selectedMainCategory === mainCategoryArray[0] ? true : false}
              onChange={mainCategoryHandleChange}
            />
            <label className='form-check-label' htmlFor={'카테고리' + mainCategoryArray[0]}>
              {mainCategoryArray[0]}
            </label>
          </React.Fragment>
        );
      })}
      <div>
        {Object.entries(codeData['category']).map((mainCategoryArray, index) => {
          return (
            <React.Fragment key={index}>
              {
                selectedMainCategory === mainCategoryArray[0] && (
                  Object.entries(mainCategoryArray[1]).map(subCategoryArray => {
                    return (
                      <React.Fragment key={subCategoryArray[1]}>
                        <input
                          id={'카테고리' + subCategoryArray[0]}
                          value={subCategoryArray[0]}
                          className='form-check-input'
                          type='radio'
                          name='subCategoryGroup'
                          onChange={subCategoryHandleChange}
                        />
                        <label className='form-check-label' htmlFor={'카테고리' + subCategoryArray[0]}>
                          {subCategoryArray[0]}
                        </label>
                      </React.Fragment>
                    );
                  })
                )
              }
            </React.Fragment>
          );
        })}
      </div>
      <hr/>
      <div><h2>사진 등록</h2></div>
      <input type="file" id="inputImg" accept="image/*" onChange={imgHandleChange}></input>
    </form>
  );
}

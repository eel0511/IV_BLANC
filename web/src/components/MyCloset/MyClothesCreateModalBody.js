import React, { useState } from 'react';
import codeData from '../../codeData.json';

export default function MyClothesCreateModalBody() {
  const [selectColor, setSelectColor] = useState('빨강');
  const [selectMaterial, setSelectMaterial] = useState('면');
  const [selectMainCategory, setSelectMainCategory] = useState('상의');
  const [selectSubCategory, setSelectSubCategory] = useState();

  const colorHandleChange = (e) => {
    setSelectColor(e.target.value);
    // console.log(e.target.value);
  };

  const materialHandleChange = (e) => {
    setSelectMaterial(e.target.value);
    // console.log(e.target.value);
  };

  const mainCategoryHandleChange = (e) => {
    setSelectMainCategory(e.target.value);
    // console.log(e.target.value);
  };

  const subCategoryHandleChange = (e) => {
    setSelectSubCategory(e.target.value);
    // console.log(e.target.value);
  };

  return (
    <form className='RadioForm'>
      <div>
        <h2>색상</h2>
      </div>
      {Object.entries(codeData['colors']).map(colorArray => {
        return (
          <React.Fragment key={colorArray[1]}>
            <input
              id={colorArray[0]}
              value={colorArray[0]}
              className='form-check-input'
              type='radio'
              name='colorGroup'
              defaultChecked={selectColor === colorArray[0] ? true : false}
              onChange={colorHandleChange}
            />
            <label className='form-check-label' htmlFor={colorArray[0]}>
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
              id={materialArray[0]}
              value={materialArray[0]}
              className='form-check-input'
              type='radio'
              name='materialGroup'
              defaultChecked={selectMaterial === materialArray[0] ? true : false}
              onChange={materialHandleChange}
            />
            <label className='form-check-label' htmlFor={materialArray[0]}>
              {materialArray[0]}
            </label>
          </React.Fragment>
        );
      })}
      <hr/>
      <div>
        <h2>카테고리</h2>
      </div>
      {Object.entries(codeData['category']).map((mainCategoryArray, index) => {
        return (
          <React.Fragment key={index}>
            <input
              id={mainCategoryArray[0]}
              value={mainCategoryArray[0]}
              className='form-check-input'
              type='radio'
              name='mainCategoryGroup'
              defaultChecked={selectMainCategory === mainCategoryArray[0] ? true : false}
              onChange={mainCategoryHandleChange}
            />
            <label className='form-check-label' htmlFor={mainCategoryArray[0]}>
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
                selectMainCategory === mainCategoryArray[0] && (
                  Object.entries(mainCategoryArray[1]).map(subCategoryArray => {
                    return (
                      <React.Fragment key={subCategoryArray[1]}>
                        <input
                          id={subCategoryArray[0]}
                          value={subCategoryArray[0]}
                          className='form-check-input'
                          type='radio'
                          name='subCategoryGroup'
                          onChange={subCategoryHandleChange}
                        />
                        <label className='form-check-label' htmlFor={subCategoryArray[0]}>
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
    </form>
  );
}

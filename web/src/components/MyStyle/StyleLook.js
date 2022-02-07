import React, { useEffect, useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import './MyStyleImg.css';

function StyleLook({ selectedClothes }) {
  const [shirts, setShirts] = useState({});
  const [pants, setPants] = useState({});
  const [outer, setOuter] = useState({});
  const [shoes, setShoes] = useState({});
  const [bag, setBag] = useState({});
  const [hat, setHat] = useState({});
  const [others, setOthers] = useState({});

  useEffect(() => {
    // 값이 없다면 undefined가 뜬다.
    for (let i = 0; i < selectedClothes.length; ++i) {
      switch (selectedClothes[i].category) {
        case 1:
          setShirts(selectedClothes.find((clothes) => clothes.category === 1));
          break;
        case 2:
          setPants(selectedClothes.find((clothes) => clothes.category === 2));
          break;
        case 3:
          setOuter(selectedClothes.find((clothes) => clothes.category === 3));
          break;
        case 4:
          setShoes(selectedClothes.find((clothes) => clothes.category === 4));
          break;
        case 5:
          setBag(selectedClothes.find((clothes) => clothes.category === 5));
          break;
        case 6:
          setHat(selectedClothes.find((clothes) => clothes.category === 6));
          break;
        default:
          setOthers(selectedClothes.find((clothes) => clothes.category === 7));
      }
    }
  }, []);

  return (
    <div className='colName'>
      <div
        className='col'
        style={{
          display: 'inline-block',
          float: 'left',
        }}
      >
        {Object.keys(hat).length > 0 && (
          <img
            className='MyStyleHat'
            src={require(`../../assets/${hat.url}`)}
            // src={hat.url}
            alt={hat.clothesId}
          />
        )}
        <br />
        {Object.keys(shirts).length > 0 && (
          <img
            className='MyStyleShirts'
            src={require(`../../assets/${shirts.url}`)}
            // src={shirts.url}
            alt={shirts.clothesId}
          />
        )}
        <br />
        {Object.keys(pants).length > 0 && (
          <img
            className='MyStylePants'
            src={require(`../../assets/${pants.url}`)}
            // src={pants.url}
            alt={pants.clothesId}
          />
        )}
        <br />
        {Object.keys(shoes).length > 0 && (
          <img
            className='MyStyleShoes'
            src={require(`../../assets/${shoes.url}`)}
            // src={shoes.url}
            alt={shoes.clothesId}
          />
        )}

        {/* <img
          className='MyStyleHat'
          src={require(`../../assets/상의.jfif`)}
          // src={hat.url}
          alt={1}
        />
        <br></br>
        <img
          className='MyStyleShirts'
          src={require(`../../assets/상의.jfif`)}
          // src={shirts.url}
          alt={1}
        />
        <br></br>
        <img
          className='MyStylePants'
          src={require(`../../assets/상의.jfif`)}
          // src={shirts.url}
          alt={1}
        />
        <br></br>
        <img
          className='MyStyleShoes'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
        /> */}
      </div>

      <div
        className='col'
        style={{
          display: 'inline-block',
          float: 'right',
        }}
      >
        {Object.keys(others).length > 0 && (
          <img
            className='MyStyleOthers'
            src={require(`../../assets/${others.url}`)}
            // src={shoes.url}
            alt={others.clothesId}
          />
        )}
        <br />
        {Object.keys(outer).length > 0 && (
          <img
            className='MyStyleOuter'
            src={require(`../../assets/${outer.url}`)}
            // src={shoes.url}
            alt={outer.clothesId}
          />
        )}
        <br />
        {Object.keys(bag).length > 0 && (
          <img
            className='MyStyleBag'
            src={require(`../../assets/${bag.url}`)}
            // src={shoes.url}
            alt={bag.clothesId}
          />
        )}

        {/* <img
          className='MyStyleOthers'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
        />
        <br></br>
        <img
          className='MyStyleOuter'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
        />
        <br></br>
        <img
          className='MyStyleBag'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
        /> */}
      </div>
    </div>
  );
}

export default StyleLook;

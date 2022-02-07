import React, { useEffect, useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';

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
    // setShirts(selectedClothes.find((clothes) => clothes.category === 1));
    // setPants(selectedClothes.find((clothes) => clothes.category === 2));
    // setOuter(selectedClothes.find((clothes) => clothes.category === 3));
    // setShoes(selectedClothes.find((clothes) => clothes.category === 4));
    // setBag(selectedClothes.find((clothes) => clothes.category === 5));
    // setHat(selectedClothes.find((clothes) => clothes.category === 6));
    // setOthers(selectedClothes.find((clothes) => clothes.category === 7));
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
        <img
          className='MyStyleClothesItemImg'
          src={require(`../../assets/상의.jfif`)}
          // src={hat.url}
          alt={1}
          style={{
            maxWidth: '150px',
            maxHeight: '150px',
            verticalAlign: 'middle',
            marginLeft: '70px',
          }}
        />
        <br></br>
        <img
          className='MyStyleClothesItemImg'
          src={require(`../../assets/상의.jfif`)}
          // src={shirts.url}
          alt={1}
          style={{
            maxWidth: '400px',
            maxHeight: '400px',
            verticalAlign: 'middle',
            marginTop: '20px',
            marginLeft: '70px',
          }}
        />
        <br></br>
        <img
          className='MyStyleClothesItemImg'
          src={require(`../../assets/상의.jfif`)}
          // src={shirts.url}
          alt={1}
          style={{
            maxWidth: '400px',
            maxHeight: '400px',
            verticalAlign: 'middle',
            marginTop: '20px',
            marginLeft: '70px',
          }}
        />
        <br></br>
        <img
          className='MyStyleClothesItemImg'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
          style={{
            maxWidth: '150px',
            maxHeight: '150px',
            verticalAlign: 'middle',
            marginTop: '20px',
            marginLeft: '70px',
          }}
        />
      </div>

      <div
        className='col'
        style={{
          display: 'inline-block',
          float: 'right',
        }}
      >
        <img
          className='MyStyleClothesItemImg'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
          style={{
            maxWidth: '150px',
            maxHeight: '150px',
            verticalAlign: 'middle',
            marginRight: '80px',
          }}
        />
        <br></br>
        <img
          className='MyStyleClothesItemImg'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
          style={{
            maxWidth: '300px',
            maxHeight: '300px',
            verticalAlign: 'middle',
            marginTop: '70px',
            marginRight: '80px',
          }}
        />
        <br></br>
        <img
          className='MyStyleClothesItemImg'
          src={require(`../../assets/상의.jfif`)}
          // src={shoes.url}
          alt={1}
          style={{
            maxWidth: '150px',
            maxHeight: '150px',
            verticalAlign: 'middle',
            marginTop: '70px',
            marginRight: '80px',
          }}
        />
      </div>
    </div>
  );
}

export default StyleLook;

{
  /* {Object.keys(hat).length > 0 && (
          <img
            className='MyStyleClothesItemImg'
            src={require(`../../assets/${hat.url}`)}
            // src={hat.url}
            alt={hat.clothesId}
            style={{
              maxWidth: '200px',
              maxHeight: '200px',
            }}
          />
        )}

        {Object.keys(shirts).length > 0 && (
          <img
            className='MyStyleClothesItemImg'
            src={require(`../../assets/${shirts.url}`)}
            // src={shirts.url}
            alt={shirts.clothesId}
            style={{
              maxWidth: '400px',
              maxHeight: '400px',
            }}
          />
        )}

        {Object.keys(pants).length > 0 && (
          <img
            className='MyStyleClothesItemImg'
            src={require(`../../assets/${pants.url}`)}
            // src={pants.url}
            alt={pants.clothesId}
            style={{
              maxWidth: '400px',
              maxHeight: '400px',
            }}
          />
        )}

        {Object.keys(shoes).length > 0 && (
          <img
            className='MyStyleClothesItemImg'
            src={require(`../../assets/${shoes.url}`)}
            // src={shoes.url}
            alt={shoes.clothesId}
            style={{
              maxWidth: '300px',
              maxHeight: '300px',
            }}
          />
        )} */
}

{
  /* {selectedClothes.map((clothesData) => (
          <div className='look' key={clothesData.clothesId}>
            <img
              className='MyStyleClothesItemImg'
              src={require(`../../assets/${clothesData.url}`)}
              // src={clothesData.url}
              alt={clothesData.clothesId}
              style={{
                maxWidth: '100%',
                maxHeight: '100%',
              }}
            />
          </div>
        ))} */
}

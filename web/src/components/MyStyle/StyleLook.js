import React, { useEffect, useState } from 'react';
import html2canvas from 'html2canvas';
import Button from '@mui/material/Button';
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
      switch (parseInt(selectedClothes[i].category / 10)) {
        case 1:
          setShirts(
            selectedClothes.find(
              (clothes) => parseInt(clothes.category / 10) === 1
            )
          );
          break;
        case 2:
          setPants(
            selectedClothes.find(
              (clothes) => parseInt(clothes.category / 10) === 2
            )
          );
          break;
        case 3:
          setOuter(
            selectedClothes.find(
              (clothes) => parseInt(clothes.category / 10) === 3
            )
          );
          break;
        case 4:
          setShoes(
            selectedClothes.find(
              (clothes) => parseInt(clothes.category / 10) === 4
            )
          );
          break;
        case 5:
          setBag(
            selectedClothes.find(
              (clothes) => parseInt(clothes.category / 10) === 5
            )
          );
          break;
        case 6:
          setHat(
            selectedClothes.find(
              (clothes) => parseInt(clothes.category / 10) === 6
            )
          );
          break;
        default:
          setOthers(
            selectedClothes.find(
              (clothes) => parseInt(clothes.category / 10) === 7
            )
          );
      }
    }
  }, []);

  const saveLook = () => {
    console.log('클릭은 됐나');
    console.log('onCapture');
    html2canvas(document.getElementById('StyleImg')).then((canvas) => {
      saveAs(canvas.toDataURL(), 'capture-test.png');
    });
    // const onCapture = () => {
    //   console.log('onCapture');
    //   html2canvas(document.getElementById('StyleImg')).then((canvas) => {
    //     saveAs(canvas.toDataURL('image/png'), 'capture-test.png');
    //   });
    // };
    const saveAs = (uri, filename) => {
      // 캡쳐된 파일을 이미지 파일로 내보낸다.
      console.log('onSaveAs');
      const link = document.createElement('a');
      if (typeof link.download === 'string') {
        link.href = uri;
        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } else {
        window.open(uri);
      }
    };
  };

  return (
    <div className='Look'>
      <Button variant='contained' color='success' onClick={saveLook}>
        캡처
      </Button>
      <div id='StyleImg'>
        <div
          className='col'
          style={{
            display: 'inline-block',
            float: 'left',
          }}
        >
          {Object.keys(hat).length > 0 && (
            <img className='MyStyleHat' src={hat.url} alt={hat.clothesId} />
          )}
          <br />
          {Object.keys(shirts).length > 0 && (
            <img
              className='MyStyleShirts'
              src={shirts.url}
              alt={shirts.clothesId}
            />
          )}
          <br />
          {Object.keys(pants).length > 0 && (
            <img
              className='MyStylePants'
              src={pants.url}
              alt={pants.clothesId}
            />
          )}
          <br />
          {Object.keys(shoes).length > 0 && (
            <img
              className='MyStyleShoes'
              src={shoes.url}
              alt={shoes.clothesId}
            />
          )}
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
              src={others.url}
              alt={others.clothesId}
            />
          )}
          <br />
          {Object.keys(outer).length > 0 && (
            <img
              className='MyStyleOuter'
              src={outer.url}
              alt={outer.clothesId}
            />
          )}
          <br />
          {Object.keys(bag).length > 0 && (
            <img className='MyStyleBag' src={bag.url} alt={bag.clothesId} />
          )}
        </div>
      </div>
    </div>
  );
}

export default StyleLook;

{
  /* <img
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
        /> */
}

{
  /* <img
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
        /> */
}

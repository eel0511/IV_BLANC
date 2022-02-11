import { useState, useRef } from "react";
import cn from "classnames";
import { ReplyFill as Icon } from "react-bootstrap-icons";

import styled from "styled-components";

function FlipCard({
  myHistory: { historyId, date, subject, text, styleUrl, photos },
}) {
  const [showBack, setShowBack] = useState(false);

  // function handleClick() {
  //   if (variant === "click") {
  //     setShowBack(!showBack);
  //   }
  // }

  function handleFocus() {
    setShowBack(true);
  }

  // function handleBlur() {
  //   if (variant === "focus") {
  //     setShowBack(false);
  //   }
  // }
  const ref = useRef();
  return (
    <>
      {/* <div
      tabIndex={historyId}
      className={cn("flip-card-outer",  "focus" )}
      // onClick={handleClick}
      onFocus={handleFocus}
      // onBlur={handleBlur}
    >
      <div className={cn("flip-card-inner", { showBack })}>
        <div
          className="card front"
          style={{
            backgroundImage: `linear-gradient(#00000000, #00000050), url(${photos[0].url})`
          }}
        >
          <div className="card-body position-relative d-flex justify-content-center align-items-end">
          <div className="d-flex flex-column justify-content-center align-items-end">
              <p className="price">{date}</p>
            </div>
            <div className="icon">
              <Icon size={15} />
            </div>
          </div>
        </div>
        <div className="card back">
          <div className="card-body d-flex flex-column justify-content-around align-items-center">
            <img src={styleUrl} alt={styleUrl} height={200} />
            <div className="d-flex flex-column justify-content-center align-items-center">
              <p className="brand">{subject}</p>
              <p className="name">{text}</p>
            </div>
            <div className="icon">
              <Icon size={25} />
            </div>
          </div>
        </div>
      </div>
    </div> */}
      <HistoryStyle>
        <div className='card-container d-flex align-content-center justify-content-center'>
          <div className='card-body'>
            <div className='card-side side-back'>
              <div className='container-fluid'>

                <form formAction='' className='card-form'>
                  <div className='row'>
                    <div className='col-xs-6'>
                      <img src={styleUrl} alt={styleUrl} height={300} />
                    </div>

                    <div className='col-xs-6 d-flex flex-column'>
                      <p>{subject}</p>
                      <p>{text}</p>
                    </div>
                  </div>
                </form>
              </div>
            </div>

            <div className='card-side side-front'>
              <div className='container-fluid'>
                <div className='row'>
                  <div
                    className='col-xs-6'
                    // style={{
                    //   backgroundImage: `linear-gradient(#00000000, #00000050), url(${photos[0].url})`,
                    // }}
                  >
                    <div className='col-xs-6 side-front-content d-flex flex-column align-content-center justify-content-center'>
                    <div>{date}</div>
                    <img src={photos[0].url} alt={photos[0].url} height={300} />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </HistoryStyle>
    </>
  );
}

export default FlipCard;

const HistoryStyle = styled.div`
  body {
    font-family: "Montserrat", "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 100%;
    background-color: #dfafbd;
  }

  /*- Card container -*/
  .card-container {
    position: relative;
    z-index: 1;
    margin: 30px 10px 10px 10px;
    width: 300px;
    height: 400px;
    perspective: 1000px;
  }

  img {
    max-width: 100%;
    max-height: 100%;
    perspective: 1000px
  }

  /*- Card body -*/
  .card-body {
    width: 100%;
    height: 100%;
    transform-style: preserve-3d;
    transition: all 0.7s linear;
  }

  /*- Flipping the card -*/
  .card-container:hover .card-body {
    transform: rotateY(180deg);
  }

  .card-container:hover > .card-body > .side-front {
    opacity: 0;
    visibility: hidden;
    transition: opacity 1s ease-in, visibility 0.75s linear;
  }

  /*- Card sides -*/
  .card-side {
    position: absolute;
    top: 0;
    overflow: hidden;
    width: 100%;
    height: 100%;
    color: #212121;
    background-color: #e2cfd4;
    backface-visibility: hidden;
    box-shadow: 0 10px 35px rgba(50, 50, 93, 0.1),
      0 2px 15px rgba(0, 0, 0, 0.07);
  }

  /*- Front side -*/
  .side-front [class^="col-xs"]:first-of-type {
    padding-left: 100;
  }

  .side-front-content {
    padding-top: 32px;
    padding-right: 32px;
    padding-bottom: 32px;
  }

  /*- Back side -*/
  .side-back {
    z-index: 2;
    padding: 32px;
    text-align: center;
    transform: rotateY(180deg);
  }

  /*- Form -*/
  .card-form {
    margin-top: 32px;
  }

  .card-form .row + .row,
  .card-form .row + fieldset,
  .card-form fieldset + fieldset {
    margin-top: 16px;
  }

  input,
  textarea {
    padding: 8px;
    width: 100%;
    border-top: 0;
    border-right: 0;
    border-bottom: 1px solid #eee;
    border-left: 0;

    &:focus {
      outline: 0;
      border-bottom: 1px solid #0c81f6;
    }
  }

  textarea {
    max-height: 80px;
    resize: vertical;
  }
`;

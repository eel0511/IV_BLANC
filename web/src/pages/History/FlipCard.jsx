import { useState } from "react";
import cn from "classnames";
import { ReplyFill as Icon } from "react-bootstrap-icons";
import styled from "styled-components";
import ReactCardFlip from "react-card-flip";
function FlipCard({ myHistory: { historyId, date, photos, subject, text , styleUrl} }) {
  const [showBack, setShowBack] = useState(false);
  const handleFocus = (e) => {
      setShowBack(!showBack);
  }; 
  // function handleClick() {
  //   if (variant === "click") {
  //     setShowBack(!showBack);
  //   }
  // }

  // function handleFocus() {
  //   if (variant === "focus") {
  //     setShowBack(true);
  //   }
  // }

  // function handleBlur() {
  //   if (variant === "blur") {
  //     setShowBack(false);
  //   }
  // }

  return (
    <StyledHistory>
      <div
      tabIndex={historyId}
      className={cn("flip-card-outer")}
      // onClick={handleClick}
      // onFocus={handleFocus}
      // onBlur={handleBlur}
    >
      <div className="flip-card-inner" showBack={ showBack }>
        <div
          className="card front"
          style={{
            backgroundImage: `linear-gradient(#00000000, #00000050), url(${styleUrl})`
          }}
          onFocus={handleFocus}
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
        <div className="card back" onFocus={handleFocus}>
          <div className="card-body d-flex flex-column justify-content-around align-items-center">
            <img src={photos.url} alt={photos} height={200} />
            <div className="d-flex flex-column justify-content-center align-items-center">
              <p className="brand">{subject}</p>
              <p className="name">{text}</p>
            </div>
            <div className="icon">
              <Icon size={15} />
            </div>
          </div>
        </div>
      </div>
      </div> 
    </StyledHistory>
  );
}

export default FlipCard;

const StyledHistory = styled.div`
  display: grid;

  *,
  *::after,
  *::before {
    display: flex;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
  }
  body {
    position: relative;
    font-family: 'Ubuntu', sans-serif;
    background-color: #000;

    .container {
      height: 100%;
      width: 100%;
      min-height: 100vh;
    }

    .overlay-background {
      position: fixed;
      top: 0;
      right:0;
      bottom: 0;
      left: 0;
      background-size: cover;
      filter: blur(2px) contrast(150%) opacity(25%);
  }

  .overlay-border {
    position: fixed;
    top: 50px;
    right: 50px;
    bottom: 50px;
    left: 50px;
    
  }

}
.flip-card-outer {
  width: 300px;
  height: 400px;
  perspective: 1000px;
  margin: 25px 0;
  

  &.flip-card-inner{
    transform: rotateY(180deg);
  }

  .flip-card-inner {
    z-index: 10;
    /* -webkit-backface-visibility: hidden; */
    backface-visibility: visible;
    -webkit-transform-style: preserve-3d;
    transform-style: preserve-3d;
    transition: .5s linear .1s;
    position: relative;
    width: inherit;
    height: inherit;
    

    &.showBack {
      transform: rotateY(180deg);
    }
  
    .card {
      position: absolute;
      top: 0;
      left: 0;
      overflow: hidden;
      width: 100%;
      height: 100%;
      border: 0;
      
  
      &.front {
        z-index: 9;
        transform: rotateY(0);
        background-repeat: no-repeat;
        background-size: cover;
        color: #fff;


        p {
          display: flex;
          align-items: flex-end;
          justify-content: center;
          background: #ed6991;
          color : rgba(255, 255, 255, 0.7);
        }

        .icon {
          position: absolute;
          bottom: 5px;
          right: 5px;
          transform: rotateY(180deg);
        }
      }
  
      &.back {
        z-index: 8;
        transform: rotateY(180deg);
        background-color: #fff;

        p {
          margin-bottom: 0;
        }

        .brand {
          font-size: 2rem;
          font-weight: bold;
        }

        .name {
          font-size: 1.4rem;
        }

        .price {
          font-size: 1.6rem;
          font-weight: bold;
        }

        .icon {
          position: absolute;
          bottom: 5px;
          right: 5px;
          transform: rotateY(180deg);
        }
      }
    }
  }
}
`;
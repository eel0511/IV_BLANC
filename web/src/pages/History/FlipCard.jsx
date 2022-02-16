import { useState, useRef } from 'react';
import cn from 'classnames';
import { ReplyFill as Icon } from 'react-bootstrap-icons';
import { Button } from 'react-bootstrap';
import styled from 'styled-components';
import axios from 'axios';

function FlipCard({
  myHistory: { historyId, date, subject, text, styleUrl, photos },
  getmyHistoriesData,
  index,
}) {
  const handleDelete = async (e) => {
    e.preventDefault();

    if (window.confirm('진짜 삭제하시겠습니까?')) {
      try {
        await axios
          .delete('http://i6d104.p.ssafy.io:9999/api/history/delete', {
            headers: {
              'X-AUTH-TOKEN': localStorage.getItem('JWT'),
              // 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY',
            },
            params: {
              historyId: `${historyId}`,
            },
          })
          .then((res) => {
            console.log('response:', res.data);
            if (res.status === 200 && res.data.output === 1) {
              console.log(res.data.msg);
              alert('삭제되었습니다.');
              getmyHistoriesData();
            } else if (res.status === 200 && res.data.output === 0) {
              alert(res.data.msg);
            } else {
              alert(res.data.msg);
            }
          });
      } catch (err) {
        console.error(err.response.data);
      }
    } else {
      alert('취소합니다.');
    }
  };
  return (
    <>
      <HistoryStyle>
        <div className='card-container d-flex align-content-center justify-content-center'>
          <div className='card-body'>
            <div className='card-side side-back'>
              <div className='container-fluid'>
                <form formAction='' className=''>
                  <div className='d-flex flex-column'>
                    <div className=' d-flex flex-column historycover'>
                      <div className='id mt-2'>{subject}</div>
                      <img
                        src={styleUrl}
                        alt={styleUrl}
                        height={300}
                        width={500}
                      />
                    </div>

                    <div className=' d-flex flex-column'>
                      {/* <div className='subject'>{subject}</div> */}
                      <div className='subject'>{text}</div>
                    </div>
                    <Button
                      variant='danger'
                      style={{
                        marginLeft: '60%',
                        backgroundColor: '#662d91',
                        color: 'white',
                        borderStyle: 'none',
                      }}
                      onClick={handleDelete}
                    >
                      삭제
                    </Button>
                  </div>
                </form>
              </div>
            </div>

            <div className='card-side side-front'>
              <div className='container-fluid'>
                <div className=''>
                  <div
                    className=' d-flex flex-column align-content-center justify-content-center'
                    style={
                      {
                        // backgroundImage: `url(${photos[0].url})`
                      }
                    }
                  >
                    <div className='date'>{date}</div>
                    <div className='frontImg'>
                      <img
                        src={photos[0].url}
                        alt={photos[0].url}
                        height={400}
                        width={300}
                      />
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
    font-family: 'Montserrat', 'Helvetica Neue', Helvetica, Arial, sans-serif;
    font-size: 100%;
    background-color: #dfafbd;
  }

  /*- Card container -*/
  .card-container {
    position: relative;
    z-index: 1;
    margin: 30px 10px 10px 10px;
    width: 300px;
    height: 450px;
    perspective: 1000px;
  }
  .historycover {
    img {
      object-fit: fill;
    }
  }
  .id {
    margin-top: -30px;
    margin-bottom: 10px;
    background-color: #b791e9;
    color: white;
    font-size: 1.2rem;
  }
  .subject {
    font-size: 1.2rem;
  }
  .text {
    font-size: 1rem;
  }
  .date {
    font-size: 1.2rem;
    background-color: #e991a8;
    color: white;
    margin-top: 30px;
  }
  .frontImg {
    margin-bottom: 30px;
    border-radius: 30px;
  }
  img {
    max-width: 100%;
    max-height: 100%;
    perspective: 1000px;
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
  .side-front [class^='col-xs']:first-of-type {
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
    /* padding: 32px; */
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

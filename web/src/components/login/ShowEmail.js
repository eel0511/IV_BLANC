import React from 'react';

export default function showEmail(props) {
  return props.email !== '' ? <h3 marginTop='10px'> {`${props.name}님의 아이디는 ${props.email}입니다.`} </h3> : <h3> {`등록된 아이디가 없습니다.`} </h3>;
}

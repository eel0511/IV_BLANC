import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function HistoryCreateModalBody() {

const [selectedStyle, setSelectedStyle] = useState([]);


const styleHandleChange = (e) => {
    console.log(e.target.files)
    setSelectedStyle(e.target.files[0])
};

const getMyStylesData = () => {
    axios
    .get('http://i6d104.p.ssafy.io:9999/api/style/finduserstyle', {
        headers: {
        'X-AUTH-TOKEN': `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY`,
        },
    })
    .then((response) => {
        console.log(response.data.data)
        console.log('성공')
        setSelectedStyle(response.data.data)
    });
    };
    useEffect(() => {
        getMyStylesData();}, );
return (
    <div>
        
    </div>
);
}

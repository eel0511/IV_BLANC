import React, { useState, useEffect } from "react";
import axios from "axios";

export default function HistoryCreateModalBody() {
  const [selectedStyles, setSelectedStyles] = useState([]);

  useEffect(() => {
    axios
      .get("http://i6d104.p.ssafy.io:9999/api/style/finduserstyle", {
        headers: {
          "X-AUTH-TOKEN": `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJQayI6IjEiLCJpYXQiOjE2NDM4Nzg4OTMsImV4cCI6MTY0NjQ3MDg5M30.Q2T5EQ38F53h1x037StKPwE-DBeqU0hBEAPY3D9w6WY`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        console.log("성공");
        setSelectedStyles(response.data.data);
      });
  });

  const styleHandleChange = (e) => {
    console.log(e.target.files);
    setSelectedStyles(e.target.files[0]);
  };

  return (
    <div>
      {selectedStyles.map((selectStyle) => (
        <div key={selectStyle.styleId}>
          <img
            src={selectStyle.url}
            alt={selectStyle.styleId}
            style={{
              maxWidth: "100%",
              maxHeight: "100%",
            }}
          />
        </div>
      ))}
    </div>
  );
}

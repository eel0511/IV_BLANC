import React, { useState, useEffect } from "react";
import { BsPersonCircle } from "react-icons/bs";
import { Menu, MenuItem, ListItemIcon, Divider, Avatar } from "@mui/material";
import Logout from "@mui/icons-material/Logout";
import { useNavigate } from "react-router-dom";
import axios from "axios";



export default function Profile() {
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  let navigate = useNavigate();
  const logOut = () => {
    localStorage.removeItem("JWT");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
    navigate("/signin");
  };

  const [Info, setInfo] = useState([]);
  const data = {
    userId: Number(""),
    email: "",
    name: "",
    social: Number(""),
    phone: "",
    gender: Number(""),
    age: Number(""),
  };
  const token = localStorage.getItem('JWT');
  const getmyInfo = () => {
    axios
      .post("http://i6d104.p.ssafy.io:9999/api/user/info", data, {
        headers: {
          'X-AUTH-TOKEN': `${token}`,
        },
      })
      .then((response) => {
        console.log(response.data.data);
        setInfo(response.data.data);
        // console.log(Info)
      });
  };
  useEffect(() => {
    getmyInfo();
  }, []);

  return (
    <div className='Profile' style={{ cursor: "pointer" }}>
      <BsPersonCircle
        className='Profile__icon'
        id='basic-button'
        sx={{ ml: 2, mt: 1.5 }}
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup='true'
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        style={{ width: "35px", height: "35px" }}
      />
      <Menu
        id='basic-menu'
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        sx={{ ml: 1, mt: 1.5 }}
        MenuListProps={{
          elevation: 0,
          sx: {
            overflow: "visible",
            color: "#ed6991",
            mt: 1.5,
            "& .MuiAvatar-root": {
              width: 32,
              height: 32,
              ml: -0.5,
              mr: 1,
            },
          },
        }}

      >
        {/* <MenuItem>
          <Avatar style={{marginLeft: "10%"}}/>
        </MenuItem> */}
        <MenuItem>
          <div className='col d-flex flex-column justify-content-around align-items-center'>
          <div>이름 : {Info.name}</div>
          <div>이메일: {Info.email}</div>
          {/* <div>나이: {Info.age}</div>
          <div>전화번호: {Info.phone}</div> */}
          </div>
        </MenuItem>
        <Divider />
        <MenuItem onClick={logOut} sx={{ color: "#ed6991" , marginLeft: "10%"}}>
          <ListItemIcon>
            <Logout fontSize='small' sx={{ color: "#ed6991" , marginLeft: "10%"}} />
          </ListItemIcon>
          로그아웃
        </MenuItem>
      </Menu>
    </div>
  );
}

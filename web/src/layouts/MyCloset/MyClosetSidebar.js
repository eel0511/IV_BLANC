import React from 'react';
import MyClosetSidebarItem from '../../components/MyCloset/MyClosetSidebarItem';
import './MyCloset.css';
import { ButtonGroup, DropdownButton , Dropdown} from 'react-bootstrap';

export default function MyClosetSidebar({ clothesDatas, getFilterMyclothes }) {
  const menus = [
    { name: '전체' },
    { name: '상의', subName:['티셔츠', '셔츠', '맨투맨'] 
      // subName: [
      //   { name: "티셔츠" }, 
      //   { name: "셔츠" },
      //   { name: "후드" },
      //   { name: "맨투맨"},
      //   { name: "블라우스"},
      //   { name: "크롭탑"},
      //   { name: "드레스"},
      //   { name: "상의기타"}]
    },

    { name: '하의', 
      // subName: [
      //   {name: "청바지"},
      //   {name: "면바지"},
      //   {name: "반바지"},
      //   {name: "정장바지"},
      //   {name: "운동복"},
      //   {name: "스커트"},
      //   {name: "레깅스"},
      //   {name: "하의기타"}]
    },

    { name: '아우터', 
      // subName: [
      //   {name:"코트"},
      //   {name:"자켓"},
      //   {name:"패딩"},
      //   {name:"조끼"},
      //   {name:"가디건"},
      //   {name:"아우터기타"}]
    },
    { name: '신발', 
      // subName: [
      //   {name:"스니커즈"},
      //   {name:"운동화"},
      //   {name:"로퍼플랫"},
      //   {name:"부츠"},
      //   {name:"샌들슬리퍼"},
      //   {name:"힐"},
      //   {name:"신발기타"}]
    },
    { name: '가방', 
      // subName: [
      //   {name: "백팩"},
      //   {name: "크로스백팩"},
      //   {name:"숄더백"},
      //   {name:"핸드백"},
      //   {name:"가방기타"}]
    },
    { name: '모자' },
    { name: '기타' },
  ];

  return (
    <div className='MyClosetSidebar'>
      <div className='MyClosetSidebarWrapper'>
        <ul className='MyClosetSidebarList'>
          {menus.map((menu, index) => {
            return (
              <li className='MyClosetSidebarListItem' key={index}>
                <MyClosetSidebarItem menu={menu} id={index} clothesDatas={clothesDatas} getFilterMyclothes={getFilterMyclothes}/>
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
}

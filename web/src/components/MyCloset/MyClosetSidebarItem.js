import React from 'react';
import { ButtonGroup, DropdownButton , Dropdown} from 'react-bootstrap';

export default function MyClosetSidebarItem({ menu, id, clothesDatas, getFilterMyclothes }) {
  const showItem = () => {
    if (id !== 0) {
      const filterClothesDatas = clothesDatas.filter(
        (clothesData) => parseInt(clothesData.category/10) === id
      );
      console.log(filterClothesDatas);
      getFilterMyclothes(filterClothesDatas)
    } else {
      const filterClothesDatas = clothesDatas
      console.log(filterClothesDatas);
      getFilterMyclothes(filterClothesDatas)
    }
  }

  return (
    <div className='MyClosetSidebarItemButton d-grid gap-2'>
      <DropdownButton as={ButtonGroup}
        type='button'
        className='btn btn-outline-secondary btn-lg'
        onClick={showItem}
        title={menu.name}
        id="bg-vertical-dropdown-2"
      >
        <Dropdown.Item eventKey={id}>{menu.subName}</Dropdown.Item>
      </DropdownButton>
    </div>
  );
}

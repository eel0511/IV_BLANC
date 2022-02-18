import React from 'react';

export default function MyClosetSidebarItem({ menu, id, clothesDatas, getFilterMyclothes }) {
  const showItem = () => {
    if (id !== 0) {
      const filterClothesDatas = clothesDatas.filter(
        (clothesData) => parseInt(clothesData.category / 10) === id
      );
      getFilterMyclothes(filterClothesDatas)
    } else {
      const filterClothesDatas = clothesDatas
      getFilterMyclothes(filterClothesDatas)
    }
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <div className='MyClosetSidebarItemButton d-grid gap-2'>
      <button
        type='button'
        className='btn btn-outline-secondary btn-lg'
        onClick={showItem}
      >
        {menu.name}
      </button>
    </div>
  );
}

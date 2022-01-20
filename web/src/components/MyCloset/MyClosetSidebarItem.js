import React from 'react';

export default function MyClosetSidebarItem({ menu }) {
  const showItem = () => {
    alert('showItem');
  };

  return (
    <div className="MyClosetSidebarItemButton d-grid gap-2">
      <button
        type="button"
        className="btn btn-outline-secondary btn-lg"
        onClick={showItem}
      >
        {menu.name}
      </button>
    </div>
  );
}

import React from 'react';

export default function IvblancNavbar() {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container-fluid">
        <aside className="navbar-brand" href="/">
          Home
        </aside>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <a className="nav-link active" aria-current="page" href="/mycloset">
                내 옷장
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/styling">
                스타일링
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/history">
                히스토리
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/friends">
                친구 옷장
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
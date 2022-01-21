import React from 'react';
import { Link } from 'react-router-dom';

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
              <Link className="nav-link active" aria-current="page" to="/mycloset">
                내 옷장
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/styling">
                스타일링
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/history">
                히스토리
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/friends">
                친구 옷장
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
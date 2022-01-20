import React from 'react';

function IvblancNavbar() {
  return (
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <aside class="navbar-brand" href="/">Home</aside>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/">내 옷장</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/styling">스타일링</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/history">히스토리</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/friends">친구 옷장</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
  );
}

export default IvblancNavbar;
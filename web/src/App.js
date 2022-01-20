import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignIn from './pages/SignIn/Signin';
import KakaoAuth from './components/login/kakaoOAuth';
import NaverAuth from './components/login/naverOAuth';
import SignUp from './pages/SignUp/SignUp';

import IvblancNavbar from './layouts/IvblancNavbar';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <div className='App'>
      {/* <IvblancNavbar /> */}

      <Router>
        <Routes>
          <Route path='/' exact element={<SignIn />}></Route>
          <Route path='/oauth/kakao/callback' exact element={<KakaoAuth />}></Route>
          <Route path='/oauth/naver/callback' exact element={<NaverAuth />}></Route>
          <Route path='/signup' exact element={<SignUp />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;

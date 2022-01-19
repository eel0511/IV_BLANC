import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignIn from './pages/SignIn/Signin';
import KakaoAuth from './components/login/kakaoOAuth';
import NaverAuth from './components/login/naverOAuth';

function App() {
  return (
    <div className='App'>
      <Router>
        <Routes>
          <Route path='/' exact element={<SignIn />}></Route>
          <Route path='/oauth/kakao/callback' exact element={<KakaoAuth />}></Route>
          <Route path='/oauth/naver/callback' exact element={<NaverAuth />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;

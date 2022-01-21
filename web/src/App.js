import './App.css';
import IvblancNavbar from './layouts/IvblancNavbar';
import Home from './pages/Home/Home';
import MyCloset from './pages/MyCloset/MyCloset';
import MyStyle from './pages/MyStyle/MyStyle';
import History from './pages/History/History';
import Friends from './pages/Friends/Friends';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignIn from './pages/SignIn/Signin';
import KakaoAuth from './components/login/kakaoOAuth';
import NaverAuth from './components/login/naverOAuth';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <Router>
      <div className='App'>
        <IvblancNavbar />
        <Routes>
          <Route path='/' exact element={<Home />}></Route>
          <Route path='/mycloset' exact element={<MyCloset />}></Route>
          <Route path='/mystyle' exact element={<MyStyle />}></Route>
          <Route path='/history' exact element={<History />}></Route>
          <Route path='/friends' exact element={<Friends />}></Route>
          <Route path='/signin' exact element={<SignIn />}></Route>
          <Route
            path='/oauth/kakao/callback'
            exact
            element={<KakaoAuth />}
          ></Route>
          <Route
            path='/oauth/naver/callback'
            exact
            element={<NaverAuth />}
          ></Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;

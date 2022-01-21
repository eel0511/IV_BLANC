import './App.css';
import IvblancNavbar from './layouts/IvblancNavbar';
import MyCloset from './pages/MyCloset/MyCloset';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignIn from './pages/SignIn/Signin';
import KakaoAuth from './components/login/kakaoOAuth';
import NaverAuth from './components/login/naverOAuth';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
      <Router>
        <div className="App">
          <IvblancNavbar />
            <Routes>
              <Route path='/mycloset' exact element={<MyCloset />}></Route>
              <Route path='/styling' exact></Route>
              <Route path='/history' exact></Route>
              <Route path='/friends' exact></Route>
              <Route path='/signin' exact element={<SignIn />}></Route>
              <Route path='/oauth/kakao/callback' exact element={<KakaoAuth />}></Route>
              <Route path='/oauth/naver/callback' exact element={<NaverAuth />}></Route>
            </Routes>
        </div>
      </Router>
  );
}

export default App;

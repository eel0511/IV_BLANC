import Home from './pages/Home/Home';
import MyCloset from './pages/MyCloset/MyCloset';
import MyStyle from './pages/MyStyle/MyStyle';
import History from './pages/History/History';
import Friends from './pages/Friends/Friends';
import SignIn from './pages/SignIn/Signin';
import KakaoAuth from './components/login/kakaoOAuth';
import NaverAuth from './components/login/naverOAuth';

export default [
  {
    path: '/',
    component: Home,
  },
  {
    path: '/mycloset',
    component: MyCloset,
  },
  {
    path: '/mystyle',
    component: MyStyle,
  },
  {
    path: '/history',
    component: History,
  },
  {
    path: '/friends',
    component: Friends,
  },
  {
    path: '/signin',
    component: SignIn,
  },
  {
    path: '/oauth/kakao/callback',
    component: KakaoAuth,
  },
  {
    path: '/oauth/naver/callback',
    component: NaverAuth,
  },
];

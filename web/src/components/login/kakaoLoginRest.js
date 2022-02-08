export default function AuthSocial() {
  const CLIENT_ID = 'd94857cc20c52361cc40e9344bc2dceb';
  const REDIRECT_URI = 'http://localhost:3000/oauth/kakao/callback';

  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  return (
    <div className='App'>
      <a href={KAKAO_AUTH_URL}>
        <img src={require('../../assets/kakao_logo.png')} alt='카카오 로그인' width='225px' height='auto'></img>
      </a>
    </div>
  );
}

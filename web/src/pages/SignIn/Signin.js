import * as React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';

import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import axios from 'axios';
import Container from '@mui/material/Container';
import AuthSocial from '../../components/login/AuthSocial';
import setAuthorizationToken from '../../utils/setAuthorizationToken';

function Copyright(props) {
  return (
    <Typography
      variant='body2'
      color='text.secondary'
      align='center'
      {...props}
    >
      {'Copyright © '}
      <Link color='inherit' href='https://mui.com/'>
        Your Website
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

// 쿠키에서 JWT 읽어오는 함수
function getCookie(cname) {
  const name = cname + '=';
  const decodedCookie = decodeURIComponent(document.cookie);

  const ca = decodedCookie.split(';');

  for (let i = 0; i < ca.length; i++) {
    const c = ca[i];

    while (c.charAt(0) === ' ') {
      c = c.substring(1);
    }

    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }

  return '';
}

const theme = createTheme();

export default function SignInSide() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isRemember, setIsRemember] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(['rememberEmail']);

  // 오류메시지 상태저장
  const [emailMessage, setEmailMessage] = useState('');

  // 유효성 검사
  const [isEmail, setIsEmail] = useState(false);

  let navigate = useNavigate();

  // 이메일 형식 확인
  const onChangeEmail = useCallback((e) => {
    const emailRegex =
      /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    const emailCurrent = e.target.value;
    setEmail(emailCurrent);

    if (!emailRegex.test(emailCurrent)) {
      setEmailMessage('이메일 형식이 틀렸어요! 다시 확인해주세요');
      setIsEmail(false);
    } else {
      setEmailMessage('올바른 이메일 형식이에요 :)');
      setIsEmail(true);
    }
  }, []);

  useEffect(() => {
    if (cookies.rememberEmail !== undefined) {
      setEmail(cookies.rememberEmail);
      setIsRemember(true);
    }
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    if (isRemember) {
      setCookie('rememberEmail', email, { maxAge: 2000 });
    } else {
      removeCookie('rememberEmail');
    }

    if (email === '') return alert('이메일을 입력해주세요');
    if (password === '') return alert('비밀번호를 입력해주세요');
    // eslint-disable-next-line no-console
    console.log({
      email: data.get('email'),
      password: data.get('password'),
    });

    // 백엔드 통신
    try {
      await axios
        .post(
          'http://localhost:9999/api/sign/login',
          // i6d104.p.ssafy.io:9999
          {
            email: data.get('email'),
            pw: data.get('password'),
            social: 0,
          },
          { withCredentials: true }
        )
        .then((res) => {
          console.log('response:', res.data);
          if (res.status === 200 && res.data.output === 1) {
            alert('로그인 성공!!');
            const token = getCookie('JWT');
            console.log(token);
            localStorage.setItem('JWT', token);
            setAuthorizationToken(token);
            navigate('/');
          } else if (res.status === 200 && res.data.output === 0) {
            alert(res.data.msg);
          } else {
            alert(res.data.msg);
          }
        });
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component='main'>
        <Grid container component='main' sx={{ height: '100vh' }}>
          <CssBaseline />
          <Grid
            item
            xs={false}
            sm={4}
            md={7}
            sx={{
              backgroundImage: 'url(https://source.unsplash.com/random)',
              backgroundRepeat: 'no-repeat',
              backgroundColor: (t) =>
                t.palette.mode === 'light'
                  ? t.palette.grey[50]
                  : t.palette.grey[900],
              backgroundSize: 'cover',
              backgroundPosition: 'center',
            }}
          />
          <Grid
            item
            xs={12}
            sm={8}
            md={5}
            component={Paper}
            elevation={6}
            square
          >
            <Box
              sx={{
                my: 8,
                mx: 4,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
              }}
            >
              <img
                src={require('../../assets/logo2.png')}
                alt='우리로고'
                width={'300px'}
              ></img>
              <Typography component='h1' variant='h5'>
                로그인
              </Typography>
              <Box
                component='form'
                noValidate
                onSubmit={handleSubmit}
                sx={{ mt: 1 }}
              >
                <TextField
                  required
                  fullWidth
                  id='email'
                  label='Email Address'
                  name='email'
                  autoComplete='email'
                  type='email'
                  value={email}
                  onChange={onChangeEmail}
                />
                {email.length > 0 && (
                  <span className={`message ${isEmail ? 'success' : 'error'}`}>
                    {emailMessage}
                  </span>
                )}

                <TextField
                  margin='normal'
                  required
                  fullWidth
                  name='password'
                  label='Password'
                  type='password'
                  id='password'
                  autoComplete='current-password'
                  onChange={(e) => {
                    setPassword(e.target.value);
                  }}
                />

                <FormControlLabel
                  control={
                    <Checkbox
                      value='remember'
                      color='primary'
                      onChange={(e) => setIsRemember(e.target.checked)}
                      checked={isRemember}
                    />
                  }
                  label='아이디 저장'
                />

                <AuthSocial />

                <Button
                  type='submit'
                  fullWidth
                  variant='contained'
                  sx={{ mt: 3, mb: 2 }}
                >
                  로그인
                </Button>
                <Grid container spacing={1}>
                  <Grid item xs={4}>
                    <Link href='/findemail' variant='body2'>
                      아이디 찾기
                    </Link>
                  </Grid>
                  <Grid item xs={4}>
                    <Link href='/findpassword' variant='body2'>
                      비밀번호 찾기
                    </Link>
                  </Grid>
                  <Grid item xs={4}>
                    <Link href='/signup' variant='body2'>
                      {'회원가입'}
                    </Link>
                  </Grid>
                </Grid>
                <Copyright sx={{ mt: 5 }} />
              </Box>
            </Box>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}

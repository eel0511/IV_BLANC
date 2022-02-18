import * as React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';

import { useState, useEffect, useCallback } from 'react';
import ShowEmail from '../../components/login/ShowEmail';

function Copyright(props) {
  return (
    <Typography variant='body2' color='text.secondary' align='center' {...props}>
      {'Copyright © '}
      <Link color='inherit' href='https://mui.com/'>
        Your Website
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const theme = createTheme();

export default function SignUp() {
  // 이메일, 이름, 폰 번호 확인
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [phoneNum, setphoneNum] = useState('');

  // 오류메시지 상태저장
  const [phoneNumMessage, setphoneNumMessage] = useState('');

  // 유효성 검사
  const [isPhoneNum, setIsPhoneNum] = useState(false);

  // 이메일 확인 컴포넌트 조건
  const [isShow, setIsShow] = useState(false);

  // 이름 확인
  const onChangeName = useCallback((e) => {
    const nameCurrent = e.target.value;
    setName(nameCurrent);
  }, []);

  // 전화번호 확인
  const onChangePhoneNum = useCallback((e) => {
    const phoneNumRegex = /^[0-9]{2,3}[0-9]{3,4}[0-9]{4}/;
    const phoneNumCurrent = e.target.value;
    setphoneNum(phoneNumCurrent);

    if (phoneNumCurrent.indexOf('-') !== -1) {
      setphoneNumMessage('-를 제외하고 입력해주세요.');
      setIsPhoneNum(false);
    } else if (!phoneNumRegex.test(phoneNumCurrent)) {
      setphoneNumMessage('휴대전화 형식에 맞춰주세요.');
      setIsPhoneNum(false);
    } else {
      setphoneNumMessage('');
      setIsPhoneNum(true);
    }
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    if (name === '') return alert('이름을 확인해주세요');
    if (phoneNum === '') return alert('전화번호를 입력해주세요');

    setIsShow(true);

    // eslint-disable-next-line no-console

    // 백엔드 통신
    // const router = useRouter();

    // try {
    //   await axios
    //     .post('http://119.56.162.61:8888/api/sign/signup', {
    //       name: data.get('name'),
    //       phone: data.get('phoneNum'),
    //     })
    //     .then((res) => {
    //       if (res.status === 200) {
    //         alert('아이디 찾기 성공!!');
    //       }
    //     });
    // } catch (err) {
    //   console.error(err);
    // }
  };

  let write = null;
  if (isShow) {
    write = <ShowEmail name={name} email={'a@a.com'} />;
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component='main' maxWidth='xs'>
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          {/* <img src={require('../../assets/logo2.png')} alt='우리로고' width={'300px'}></img> */}
          <Typography component='h1' variant='h5'>
            아이디 찾기
          </Typography>
          <Box component='form' noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField required fullWidth id='name' label='이름' name='name' autoComplete='family-name' onChange={onChangeName} />
              </Grid>

              <Grid item xs={12}>
                <TextField required fullWidth id='phoneNum' label='전화번호' name='phoneNum' autoComplete='phoneNumber' onChange={onChangePhoneNum} />
                {phoneNum.length > 0 && <span className={`message ${isPhoneNum ? 'success' : 'error'}`}>{phoneNumMessage}</span>}
              </Grid>
            </Grid>
            <Button type='submit' fullWidth variant='contained' sx={{ mt: 3, mb: 2 }}>
              아이디 찾기
            </Button>

            {write}

            <Grid container spacing={1}>
              <Grid item xs={12} sm={6}>
                <Link href='/signin' variant='body2'>
                  로그인
                </Link>
              </Grid>
              <Grid item xs={12} sm={6}>
                <Link href='/findpassword' variant='body2'>
                  비밀번호 찾기
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 5 }} />
      </Container>
    </ThemeProvider>
  );
}

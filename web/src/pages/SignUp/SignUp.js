import * as React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Paper from '@mui/material/Paper';
import Navbar from '../../components/Navbar';
import styled from 'styled-components';
import home from '../../assets/home.png';
import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../../styles/signup.scss';

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

const theme = createTheme();

export default function SignUp() {
  // 이메일, 비밀번호, 비밀번호 확인, 이름, 성별, 나이, 폰 번호 확인
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [name, setName] = useState('');
  const [gender, setGender] = useState('female'); // 성별 문자로 저장
  const [age, setAge] = useState(0);
  const [phoneNum, setphoneNum] = useState('');

  // 오류메시지 상태저장
  const [emailMessage, setEmailMessage] = useState('');
  const [passwordMessage, setPasswordMessage] = useState('');
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');
  const [nameMessage, setNameMessage] = useState('');
  const [ageMessage, setAgeMessage] = useState('');
  const [phoneNumMessage, setphoneNumMessage] = useState('');

  // 유효성 검사
  const [isEmail, setIsEmail] = useState(false);
  const [isEmailCheck, setIsEmailCheck] = useState(false);
  const [isPassword, setIsPassword] = useState(false);
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);
  const [isName, setIsName] = useState(false);
  const [isAge, setIsAge] = useState(false);
  const [isPhoneNum, setIsPhoneNum] = useState(false);

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
      setEmailMessage('올바른 이메일 형식이에요 : )');
      setIsEmail(true);
    }
  }, []);

  // 이메일 중복 체크
  const checkEmail = useCallback(async (e) => {
    e.preventDefault();
    const tmpEmail = document.getElementById('email').value;
    console.log(document.getElementById('email').value);

    try {
      await axios
        .get('http://localhost:9999/api/sign/checkEmail', {
          // i6d104.p.ssafy.io:9999
          params: {
            email: tmpEmail,
          },
        })
        .then((res) => {
          // console.log(res);
          console.log('response:', res.data);
          if (res.status === 200 && res.data.output === 1) {
            alert('사용가능한 이메일입니다.');
            setIsEmailCheck(true);
            setEmailMessage('사용가능한 이메일입니다.');
            setIsEmail(true);
          } else if (res.status === 200 && res.data.output === 0) {
            alert(res.data.msg);
            setEmailMessage(res.data.msg);
            setIsEmail(false);
          } else {
            alert('중복된 이메일입니다. 다시 입력해주세요.');
          }
        });
    } catch (err) {
      console.error(err);
    }
  }, []);

  // 비밀번호
  const onChangePassword = useCallback((e) => {
    const passwordRegex =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    const passwordCurrent = e.target.value;
    setPassword(passwordCurrent);

    if (!passwordRegex.test(passwordCurrent)) {
      setPasswordMessage(
        '숫자+영문자+특수문자 조합으로 8자리 이상 16자리 이하로 입력해주세요!'
      );
      setIsPassword(false);
    } else {
      setPasswordMessage('안전한 비밀번호에요 : )');
      setIsPassword(true);
    }
  }, []);

  // 비밀번호 확인
  const onChangePasswordConfirm = useCallback(
    (e) => {
      const passwordConfirmCurrent = e.target.value;
      setPasswordConfirm(passwordConfirmCurrent);

      if (password === passwordConfirmCurrent) {
        setPasswordConfirmMessage('비밀번호를 똑같이 입력했어요 :)');
        setIsPasswordConfirm(true);
      } else {
        setPasswordConfirmMessage('비밀번호가 틀려요. 다시 확인해주세요.');
        setIsPasswordConfirm(false);
      }
    },
    [password]
  );

  // 이름 확인
  const onChangeName = useCallback((e) => {
    const nameCurrent = e.target.value;
    setName(nameCurrent);

    if (nameCurrent.length < 2 || nameCurrent.length > 10) {
      setNameMessage('2글자 이상 10글자 미만으로 입력해주세요.');
      setIsName(false);
    } else {
      setNameMessage('올바른 이름 형식입니다 :)');
      setIsName(true);
    }
  }, []);

  // 성별 확인
  const onChangeGender = (e) => {
    setGender(e.target.value);
  };

  // 나이 확인
  const onChangeAge = useCallback((e) => {
    const ageRegex = /^[0-9]+$/;
    const ageCurrent = e.target.value;
    setAge(ageCurrent);

    if (!ageRegex.test(ageCurrent)) {
      setAgeMessage('숫자를 입력해주세요.');
      setIsAge(false);
    } else {
      setAgeMessage('올바른 나이 형식입니다 :)');
      setIsAge(true);
    }
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

    if (email === '') return alert('이메일을 입력해주세요');
    if (passwordConfirm === '') return alert('비밀번호를 입력해주세요');
    if (name === '') return alert('이름을 확인해주세요');
    if (age === '') return alert('나이를 입력해주세요');
    if (phoneNum === '') return alert('전화번호를 입력해주세요');

    if (!isEmailCheck) return alert('이메일 중복 확인을 확인해주세요');

    // eslint-disable-next-line no-console
    console.log({
      email: data.get('email'),
      password: data.get('password'),
      name: data.get('name'),
      gender: data.get('gender') === 'male' ? 1 : 2,
      age: Number(data.get('age')),
      phoneNum: data.get('phoneNum'),
    });

    // 백엔드 통신
    try {
      await axios
        .post('http://localhost:9999/api/sign/signup', {
          // i6d104.p.ssafy.io:9999
          email: data.get('email'),
          password: data.get('password'),
          password_chk: data.get('password_chk'),
          name: data.get('name'),
          gender: data.get('gender') === 'male' ? 1 : 2,
          age: Number(data.get('age')),
          phone: data.get('phoneNum'),
          social: 0,
        })
        .then((res) => {
          console.log('response:', res.data);
          if (res.status === 200 && res.data.output === 1) {
            alert('회원가입 성공!!');
            navigate('/signin');
          } else if (res.status === 200 && res.data.output === 0) {
            alert(res.data.msg);
          }
        });
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Section>
    <div className='MyCloset__Nav'>
        <Navbar />
      </div>
    <ThemeProvider theme={theme}>
      <Container component='main' maxWidth='xs' >
        <CssBaseline />
        <Box
          component={Paper}    
          sx={{
            marginTop: 5,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <img
            src={require('../../assets/logo.png')}
            alt='우리로고'
            width={'100px'}
          ></img>
          <Typography component='h1' variant='h5'>
            회원가입
          </Typography>
          <Box
            component='form'
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
            <Grid item xs={1}></Grid>
              <Grid item xs={6.5}>
                <TextField
                  required
                  fullWidth
                  id='email'
                  label='이메일'
                  name='email'
                  autoComplete='email'
                  value={email}
                  onChange={onChangeEmail}
                />
                {email.length > 0 && (
                  <span className={`message ${isEmail ? 'success' : 'error'}`}>
                    {emailMessage}
                  </span>
                )}
              </Grid>
              <Grid item xs={3.5}>
                <Button onClick={checkEmail} variant='outlined' sx={{ mt: 1 }}>
                  중복확인
                </Button>
              </Grid>
              <Grid item xs={1}></Grid>

              <Grid item xs={1}></Grid>
              <Grid item xs={10}>
                <TextField
                  required
                  fullWidth
                  name='password'
                  label='비밀번호'
                  type='password'
                  id='password'
                  autoComplete='new-password'
                  onChange={onChangePassword}
                />
                {password.length > 0 && (
                  <span
                    className={`message ${isPassword ? 'success' : 'error'}`}
                  >
                    {passwordMessage}
                  </span>
                )}
              </Grid>
              <Grid item xs={1}></Grid>
              <Grid item xs={1}></Grid>
              <Grid item xs={10}>
                <TextField
                  required
                  fullWidth
                  name='password_chk'
                  label='비밀번호 확인'
                  type='password'
                  id='password'
                  autoComplete='new-password'
                  onChange={onChangePasswordConfirm}
                />
                {passwordConfirm.length > 0 && (
                  <span
                    className={`message ${
                      isPasswordConfirm ? 'success' : 'error'
                    }`}
                  >
                    {passwordConfirmMessage}
                  </span>
                )}
              </Grid>
              <Grid item xs={1}></Grid>

              <Grid item xs={1}></Grid>
              <Grid item xs={10}>
                <TextField
                  required
                  fullWidth
                  id='name'
                  label='이름'
                  name='name'
                  autoComplete='family-name'
                  onChange={onChangeName}
                />
                {name.length > 0 && (
                  <span className={`message ${isName ? 'success' : 'error'}`}>
                    {nameMessage}
                  </span>
                )}
              </Grid>
              <Grid item xs={1}></Grid>

              <Grid item xs={3}></Grid>
              <Grid item xs={9} align='left'>
                <FormLabel id='demo-row-radio-buttons-group-label'>
                  성별
                </FormLabel>
                <RadioGroup
                  row
                  aria-labelledby='demo-row-radio-buttons-group-label'
                  name='gender'
                  value={gender}
                  onChange={onChangeGender}
                >
                  <FormControlLabel
                    value='female'
                    control={<Radio />}
                    label='Female'
                  />
                  <FormControlLabel
                    value='male'
                    control={<Radio />}
                    label='Male'
                  />
                </RadioGroup>
              </Grid>
            
              <Grid item xs={1}></Grid>
              <Grid item xs={10}>
                <TextField
                  required
                  fullWidth
                  id='age'
                  label='나이'
                  name='age'
                  autoComplete='age'
                  onChange={onChangeAge}
                />
                {isNaN(age) && (
                  <span className={`message ${isAge ? 'success' : 'error'}`}>
                    {ageMessage}
                  </span>
                )}
              </Grid>
              <Grid item xs={1}></Grid>
              
              <Grid item xs={1}></Grid>
              <Grid item xs={10}>
                <TextField
                  required
                  fullWidth
                  id='phoneNum'
                  label='전화번호'
                  name='phoneNum'
                  autoComplete='phoneNumber'
                  onChange={onChangePhoneNum}
                />
                {phoneNum.length > 0 && (
                  <span
                    className={`message ${isPhoneNum ? 'success' : 'error'}`}
                  >
                    {phoneNumMessage}
                  </span>
                )}
              </Grid>
              <Grid item xs={1}></Grid>

            </Grid>
            <Button
              type='submit'
              variant='contained'
              sx={{ mt: 3, mb: 2, mx: 2, my: 2}}
            >
              회원가입
            </Button>
            <Grid container justifyContent='flex-end'>
              <Grid item sx={{ mr: 2 }}>
                <Link href='/signin' variant='body2'>
                  이미 계정이 있으신가요? 로그인
                </Link>
              </Grid>
            </Grid>

          </Box>
          <Grid item xs={12}>
        <Copyright sx={{ mt: 2 }} />
        </Grid>
        </Box>
        
      </Container>
    </ThemeProvider>
    </Section>
  );
}

const Section = styled.section`
  // background-image: url(${home});
  background-size: cover;
  min-height: 100vh;
  background-repeat: no-repeat;
  background-position: center;
  position: relative;
`;
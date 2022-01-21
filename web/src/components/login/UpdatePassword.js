import React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useState, useCallback } from 'react';
import axios from 'axios';

const theme = createTheme();

export default function UpdatePassword(props) {
  //  비밀번호, 비밀번호 확인

  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');

  // 오류메시지 상태저장
  const [passwordMessage, setPasswordMessage] = useState('');
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');

  // 유효성 검사
  const [isPassword, setIsPassword] = useState(false);
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);

  // 비밀번호
  const onChangePassword = useCallback((e) => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    const passwordCurrent = e.target.value;
    setPassword(passwordCurrent);

    if (!passwordRegex.test(passwordCurrent)) {
      setPasswordMessage('숫자+영문자+특수문자 조합으로 8자리 이상 16자리 이하로 입력해주세요!');
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

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    if (password === '') return alert('비밀번호를 입력해주세요');
    if (passwordConfirm === '') return alert('비밀번호를 입력해주세요');

    // eslint-disable-next-line no-console
    console.log({
      password: data.get('password'),
      password_chk: data.get('password_chk'),
    });

    // 백엔드 통신
    // const router = useRouter();

    // try {
    //   await axios
    //     .post('http://119.56.162.61:8888/api/sign/signup', {
    //       email: data.get('email'),
    //       password: data.get('password'),
    //       password_chk: data.get('password_chk'),
    //     })
    //     .then((res) => {
    //       console.log('response:', res.data);
    //       if (res.status === 200) {
    //         alert('회원가입 성공!!');
    //       }
    //     });
    // } catch (err) {
    //   console.error(err);
    // }
  };

  return props.email !== '' ? (
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
          <Box component='form' noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField required fullWidth name='password' label='비밀번호' type='password' id='password' autoComplete='new-password' onChange={onChangePassword} />
                {password.length > 0 && <span className={`message ${isPassword ? 'success' : 'error'}`}>{passwordMessage}</span>}
              </Grid>
              <Grid item xs={12}>
                <TextField required fullWidth name='password_chk' label='비밀번호 확인' type='password' id='password' autoComplete='new-password' onChange={onChangePasswordConfirm} />
                {passwordConfirm.length > 0 && <span className={`message ${isPasswordConfirm ? 'success' : 'error'}`}>{passwordConfirmMessage}</span>}
              </Grid>
            </Grid>
            <Button type='submit' fullWidth variant='contained' sx={{ mt: 3, mb: 2 }}>
              비밀번호 수정
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  ) : (
    <h3>이메일이 없습니다.</h3>
  );
}

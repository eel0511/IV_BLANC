// material
import { Stack, Divider, Typography } from '@mui/material';
// 소셜로그인
import NaverLogin from './naverLogin';
// import KakaoLogin from './kakaoLogin';
import KakaoLoginRest from './kakaoLoginRest';
// ----------------------------------------------------------------------

export default function AuthSocial() {
  return (
    <>
      <Divider sx={{ my: 3 }}>
        <Typography variant='body2' sx={{ color: 'text.secondary' }}>
          OR
        </Typography>
      </Divider>

      <Stack direction='row' spacing={2}>
        <NaverLogin />
        {/* <KakaoLogin /> */}
        <KakaoLoginRest />
      </Stack>
    </>
  );
}

package com.ivblanc.api.service;

import java.util.Collections;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.LoginUserReqDTO;
import com.ivblanc.api.dto.req.SignUpReqDTO;
import com.ivblanc.core.entity.User;
import com.ivblanc.core.exception.ApiMessageException;
import com.ivblanc.core.repository.UserRepository;
import com.ivblanc.core.utils.CheckValidate;
import com.ivblanc.core.utils.PasswordValidate;


@Service
@Transactional(readOnly = true)
public class SignService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    SignService(UserRepository userRepository, @Lazy JwtTokenProvider jwtTokenProvider,
        @Lazy PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * id로 회원정보 조회
     * @param id
     * @return
     * @throws Exception
     */
    public User findUserById(int id) throws Exception{
        User user = userRepository.findById(id).orElseThrow( () -> new ApiMessageException("존재하지 않는 회원정보입니다.") );
        return user;
    }

    /**
     * email로 user 조회
     * @param email
     * @return
     * @throws Exception
     */
    public User findByEmail(String email) throws Exception{
        return userRepository.findByEmail(email);
    }

    /**
     * 회원가입 후 userId 리턴
     * @param req
     * @return
     */
    @Transactional(readOnly = false)
    public int userSignUp(SignUpReqDTO req) throws Exception {
        User emailChk = findByEmail(req.getEmail());
        if(emailChk != null)
            throw new ApiMessageException("중복된 이메일의 회원이 존재합니다.");

        if(!req.getPassword().equals(req.getPassword_chk())){
            throw new ApiMessageException("비밀번호가 일치하지 않습니다.");
        }

        // DB에 저장할 User Entity 세팅
        User user = User.builder()
            .social(req.getSocial())
            .email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword()))
            .name(req.getName() == null ? "" : req.getName())
            .phone(req.getPhone())
            .age(req.getAge())
            .gender(req.getGender())
            .roles(Collections.singletonList("ROLE_USER")) // 인증된 회원인지 확인하기 위한 JWT 토큰에 사용될 데이터
            .build();

        // 회원가입 (User Entity 저장)
        int userId = userRepository.save(user).getUserId();
        return userId;
    }

    /**
     * email, type으로 회원정보 조회
     * @param email
     * @param social
     * @return
     */
    public User findUserByEmailType(String email, int social){
        return userRepository.findUserLogin(email, social);
    }


    @Transactional(readOnly = false)
    public void userLogin(LoginUserReqDTO req, HttpServletResponse response){
        if(!CheckValidate.checkEmailForm(req.getEmail())){
            throw new ApiMessageException("이메일 형식을 확인해주세요.");
        }

        User user = findUserByEmailType(req.getEmail(), req.getSocial());
        if(user == null){
            throw new ApiMessageException("존재하지 않는 회원정보입니다.");
        } else if (!passwordEncoder.matches(req.getPw(), user.getPassword())){
            throw new ApiMessageException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(String.valueOf(user.getUserId()), Collections.singletonList("ROLE_USER"));

        Cookie cookie = new Cookie("JWT", token);
        cookie.setMaxAge(1000 * 1000);
        response.addCookie(cookie);

        ResponseCookie rCookie = ResponseCookie.from("JWT", token)
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(false)
            .domain("i6d104.p.ssafy.io")
            .maxAge(1000 * 1000)
            .build();

        response.setHeader("Set-Cookie", rCookie.toString());
        response.setHeader("Access-Control-Allow-Origin", "http://i6d104.p.ssafy.io");
        response.setHeader("Access-Control-Allow-Method", "GET, POST, OPTIONS, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        // 회원 토큰값, 디바이스 정보 업데이트
        user.updateTokenFCM(req.getToken_fcm());
        user.updateTokenJWT(token);
        userRepository.save(user);
    }
}



















































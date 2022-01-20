package com.ivblanc.api.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.LoginUserReqDTO;
import com.ivblanc.api.dto.req.SignUpReqDTO;
import com.ivblanc.api.dto.res.LoginUserResDTO;
import com.ivblanc.api.dto.res.UserIdResDTO;
import com.ivblanc.api.service.SignService;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.User;
import com.ivblanc.core.exception.ApiMessageException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"가입/로그인"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign")
public class SignController {
    private final SignService signService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 로그인 : get /login
     * 회원가입 일반 : post /signup
     */


    // 일반 회원가입
    @ApiOperation(value = "일반 회원가입", notes = "일반 회원가입")
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<UserIdResDTO> userSignUp(@Valid SignUpReqDTO req) throws Exception{
        // 이메일 중복되는 값이 존재하는지 확인 (uid = 고유한 값, 이메일)
        User emailChk = signService.findByEmail(req.getEmail());
        if(emailChk != null)
            throw new ApiMessageException("중복된 이메일의 회원이 존재합니다.");

        System.out.print("=== 이메일 중복체크 완료 ===");

        // DB에 저장할 User Entity 세팅
        User user = User.builder()
                .social(req.getSocial())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName() == null ? "" : req.getName())
                .phone(req.getPhone())
                .age(req.getAge())
                .gender(req.getGender())

                // 기타 필요한 값 세팅
                .roles(Collections.singletonList("ROLE_USER")) // 인증된 회원인지 확인하기 위한 JWT 토큰에 사용될 데이터
                .build();

        System.out.print("=== user entity 세팅 완료 ===");

        // 회원가입 (User Entity 저장)
        long userId = signService.userSignUp(user);

        System.out.print("=== user entity 저장 완료 ===");

        // 저장된 User Entity의 PK가 없을 경우 (저장 실패)
        if(userId <= 0)
            throw new ApiMessageException("회원가입에 실패했습니다. 다시 시도해 주세요.");

        return responseService.getSingleResult(UserIdResDTO.builder().id(userId).build());
    }

    // 일반 로그인
    @ApiOperation(value = "일반 로그인", notes = "일반 로그인")
    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<LoginUserResDTO> login(@Valid LoginUserReqDTO req) throws Exception{
        // Email값과 회원가입 타입으로 해당되는 회원정보 조회
        User user = signService.findUserByEmailType(req.getEmail(), req.getSocial());

        if(user == null){
            throw new ApiMessageException("존재하지 않는 회원정보입니다.");
        } else if (!passwordEncoder.matches(req.getPw(), user.getPassword())){
            throw new ApiMessageException("비밀번호가 일치하지 않습니다.");
        }

        // return data 세팅
        LoginUserResDTO dto = LoginUserResDTO.builder()
            .userId(user.getUserId())
            .build();

        dto.setToken_jwt(jwtTokenProvider.createToken(String.valueOf(user.getUserId()), Collections.singletonList("ROLE_USER")));

        // 회원 FCM 토큰값, 디바이스 정보 업데이트
        user.updateTokenFCM(req.getToken_fcm());
        signService.saveUser(user);

        return responseService.getSingleResult(dto, "로그인 성공", 200);
    }

}

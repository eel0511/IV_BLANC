package com.ivblanc.api.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.dto.req.CheckEmailReqDTO;
import com.ivblanc.api.dto.req.LoginUserReqDTO;
import com.ivblanc.api.dto.req.SignUpReqDTO;
import com.ivblanc.api.service.SignService;
import com.ivblanc.api.service.common.CommonResult;
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
@CrossOrigin("*")
@RequestMapping(value = "/api/sign")
public class SignController {
    private final SignService signService;
    private final ResponseService responseService;

    /**
     * 로그인 : post /login
     * 회원가입 일반 : post /signup
     * 이메일 중복체크 : get /checkEmail
     */

    // 이메일 중복체크
    @ApiOperation(value = "이메일 중복체크", notes = "이메일 중복체크")
    @GetMapping(value = "/checkEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    CommonResult checkEmail(@Valid @RequestBody CheckEmailReqDTO req) throws Exception{
        // 존재하는 회원인지 확인
        User user = signService.findByEmail(req.getEmail());
        if(user != null)
            throw new ApiMessageException("이미 등록된 회원입니다.");

        return responseService.getSuccessResult("사용 가능한 이메일입니다.");
    }

    // 일반 회원가입
    @ApiOperation(value = "일반 회원가입", notes = "일반 회원가입")
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<String> userSignUp(@Valid @RequestBody SignUpReqDTO req) throws Exception{
        int userId = signService.userSignUp(req);

        if(userId <= 0)
            throw new ApiMessageException("회원가입에 실패했습니다. 다시 시도해 주세요.");

        return responseService.getSingleResult(userId + "번 회원 가입 성공");
    }

    // 일반 로그인
    @ApiOperation(value = "일반 로그인", notes = "일반 로그인")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CommonResult login(@Valid @RequestBody LoginUserReqDTO req, HttpServletResponse response) throws Exception{
        int userId = signService.userLogin(req, response);
        if(userId <= 0)
            throw new ApiMessageException("로그인에 실패했습니다. 다시 시도해 주세요.");

        return responseService.getSuccessResult("로그인 성공");
    }

}

package com.ivblanc.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.SignOutReqDTO;
import com.ivblanc.api.dto.req.UpdatePersonalReqDTO;
import com.ivblanc.api.dto.req.UpdatePwReqDTO;
import com.ivblanc.api.service.UserService;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"USER"})
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserService userService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원정보 변경 : put /update
     * 회원탈퇴 : delete /signOut
     */

    // 회원 정보 변경 - 비밀번호
    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 변경")
    @PutMapping(value = "/update/pw", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SingleResult<String> userUpdatePw(@Valid @RequestBody UpdatePwReqDTO req,
        @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception{
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        userService.updatePw(req, userId);
        return responseService.getSingleResult(userId + "번 유저 비밀번호 변경 완료");
    }

    // 회원 정보 변경 - 개인정보
    @ApiOperation(value = "개인정보 변경", notes = "개인정보 변경")
    @PutMapping(value = "/update/personal", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<String> userUpdatePersonal(@Valid @RequestBody UpdatePersonalReqDTO req,
        @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception{
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        userService.updatePersonal(req, userId);
        return responseService.getSingleResult(userId + "번 유저 비밀번호 변경 완료");
    }

    // 회원 탈퇴
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴")
    @DeleteMapping(value = "/signOut", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<String> userSignOut(@Valid @RequestBody SignOutReqDTO req,
        @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception{
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        userService.deleteUser(req, userId);

        return responseService.getSingleResult(userId + "번 유저 탈퇴 완료");
    }

}

package com.ivblanc.api.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDTO {
    @NotBlank
    @ApiModelProperty(value = "이메일", required = true, example = "a@a.com")
    private String email;

    @NotNull
    @ApiModelProperty(value = "회원가입 타입(1:일반회원, 2:카카오, 3:구글, 4:네이버)", required = true, example = "0")
    private int social;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;

    @ApiModelProperty(value = "이름", required = false, example = "카카오")
    private String name;

    @ApiModelProperty(value = "핸드폰번호('-'값 없이 입력)", required = false, example = "01012345678")
    private String phone;

    @ApiModelProperty(value = "나이", required = false, example = "26")
    private int age;

    @ApiModelProperty(value = "성별(남자 1, 여자 2)", required = false, example = "1")
    private int gender;
    
}

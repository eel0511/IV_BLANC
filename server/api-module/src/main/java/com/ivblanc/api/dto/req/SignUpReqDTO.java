package com.ivblanc.api.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식을 확인해주세요.")
    @ApiModelProperty(value = "이메일", required = true, example = "a@a.com")
    private String email;

    @NotNull(message = "회원가입 타입을 입력해주세요.(1:일반회원, 2:카카오, 3:구글, 4:네이버)")
    @ApiModelProperty(value = "회원가입 타입(1:일반회원, 2:카카오, 3:구글, 4:네이버)", required = true, example = "0")
    private int social;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,14}|(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,14}$|(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,14}$",
        message = "영문, 숫자, 특수문자 중 2가지 이상을 포함한 8~14자의 비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "비밀번호", required = true, example = "123a")
    private String password;

    @NotBlank(message = "비밀번호 확인값을 입력해주세요.")
    @ApiModelProperty(value = "비밀번호 확인", required = true, example = "123a")
    private String password_chk;

    @Size(min = 0, max = 32, message = "이름이 너무 깁니다.")
    @ApiModelProperty(value = "이름", required = false, example = "카카오")
    private String name;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]*$", message = "전화번호는 -를 제외한 숫자만 입력해주세요.")
    @Pattern(regexp = "^01{1}[016789]{1}[0-9]{7,8}$", message = "전화번호 형식을 확인해주세요")
    @ApiModelProperty(value = "핸드폰번호('-'값 없이 입력)", required = true, example = "01012345678")
    private String phone;

    @Positive(message = "나이를 확인해주세요.")
    @ApiModelProperty(value = "나이", required = false, example = "26")
    private int age;

    @ApiModelProperty(value = "성별(남자 1, 여자 2)", required = false, example = "1")
    private int gender;
    
}

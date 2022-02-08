package com.ivblanc.api.dto.req;

import javax.validation.constraints.NotBlank;
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
public class UpdatePersonalReqDTO {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @ApiModelProperty(value = "이메일", required = true, example = "123@a.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @ApiModelProperty(value = "비밀번호", required = true, example = "123a")
    private String pw;

    @Positive(message = "나이를 확인해주세요.")
    @ApiModelProperty(value = "나이", required = false, example = "23")
    private int age;

    @ApiModelProperty(value = "성별", required = false, example = "1")
    private int gender;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]*$", message = "전화번호는 -를 제외한 숫자만 입력해주세요.")
    @Pattern(regexp = "^01{1}[016789]{1}[0-9]{7,8}$", message = "전화번호 형식을 확인해주세요")
    @ApiModelProperty(value = "핸드폰 번호", required = false, example = "01012345678")
    private String phone;

    @Size(min = 0, max = 32, message = "이름이 너무 깁니다.")
    @ApiModelProperty(value = "이름", required = false, example = "홍길동")
    private String name;
}

package com.ivblanc.api.dto.req;

import javax.validation.constraints.NotBlank;

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
    @NotBlank
    @ApiModelProperty(value = "uid (이메일)", required = true, example = "123")
    private String uid;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;

    @ApiModelProperty(value = "나이", required = false, example = "23")
    private int age;

    @ApiModelProperty(value = "성별", required = false, example = "1")
    private int gender;

    @ApiModelProperty(value = "전화번호", required = false, example = "010-1234-5678")
    private String phone;

}

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
public class SignOutReqDTO {
    @NotBlank
    @ApiModelProperty(value = "uid (이메일)", required = true, example = "ssafy@ssafy.com")
    private String uid;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;
}

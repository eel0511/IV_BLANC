package com.ivblanc.api.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class UpdatePwReqDTO {
    @NotBlank
    @ApiModelProperty(value = "user id", required = true, example = "123")
    private int user_id;

    @NotBlank
    @ApiModelProperty(value = "기존 비밀번호", required = true, example = "123")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "새 비밀번호", required = true, example = "123")
    private String password_new;

    @NotBlank
    @ApiModelProperty(value = "새 비밀번호 확인", required = true, example = "123")
    private String password_check;
    
}

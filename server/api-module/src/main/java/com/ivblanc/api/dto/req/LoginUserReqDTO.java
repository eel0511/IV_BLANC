package com.ivblanc.api.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.google.firebase.database.annotations.NotNull;

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
public class LoginUserReqDTO {
    @NotBlank
    @ApiModelProperty(value = "uid (일반회원:아이디, sns로그인:uid값)", required = true, example = "kakao123")
    private String uid;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;

    @ApiModelProperty(value = "토큰값", required = false, example = "")
    private String token;

    @NotNull
    @Pattern(regexp = "^(none|sns)$")   // type으로 none과 sns 외에는 들어올 수 없음
    @ApiModelProperty(value = "로그인 타입 (none, sns)", required = true, example = "none")
    private String type;
    
}

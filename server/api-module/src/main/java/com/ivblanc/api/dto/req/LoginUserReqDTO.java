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
public class LoginUserReqDTO {

	@NotBlank
	@ApiModelProperty(value = "이메일", required = true, example = "a@a.com")
	private String email;

	@NotBlank
	@ApiModelProperty(value = "비밀번호", required = true, example = "123a")
	private String pw;

	@ApiModelProperty(value = "FCM 토큰값", required = false, example = "")
	private String token_fcm;

	@NotNull
	@ApiModelProperty(value = "회원가입 타입(1:일반회원, 2:카카오, 3:구글, 4:네이버)", required = true, example = "0")
	private int social;
}

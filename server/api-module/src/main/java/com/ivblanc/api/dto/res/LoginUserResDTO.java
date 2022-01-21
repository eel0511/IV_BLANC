package com.ivblanc.api.dto.res;

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
public class LoginUserResDTO {

	@ApiModelProperty(value = "회원 아이디", required = true, example = "1")
	private int userId;

	@ApiModelProperty(value = "JWT 토큰값", required = true, example = "")
	private String token_jwt;
}
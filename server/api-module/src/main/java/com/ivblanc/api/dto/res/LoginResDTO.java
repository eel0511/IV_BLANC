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
public class LoginResDTO {
	@ApiModelProperty(value = "아이디", required = true, example = "1")
	private int userId;

	@ApiModelProperty(value = "이메일", required = true, example = "a@a.com")
	private String email;

	@ApiModelProperty(value = "이름", required = true, example = "홍길동")
	private String name;
}

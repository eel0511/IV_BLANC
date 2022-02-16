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
public class UserInfoResDTO {
	@ApiModelProperty(value = "아이디", required = true, example = "1")
	private int userId;

	@ApiModelProperty(value = "이메일", required = true, example = "a@a.com")
	private String email;

	@ApiModelProperty(value = "이름", required = true, example = "홍길동")
	private String name;

	@ApiModelProperty(value = "회원이 가입한 타입 (1:일반회원, 2:카카오, 3:구글, 4:네이버)", required = true, example = "1")
	private int social;

	@ApiModelProperty(value = "회원 휴대폰", example = "01012341234")
	private String phone;

	@ApiModelProperty(value = "성별 (남자 1, 여자 2)", example = "1")
	private int gender;

	@ApiModelProperty(value = "회원 나이", example = "26")
	private int age;
}

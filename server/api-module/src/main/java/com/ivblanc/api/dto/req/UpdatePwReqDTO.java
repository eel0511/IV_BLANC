package com.ivblanc.api.dto.req;

import javax.validation.constraints.NotBlank;
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
	@NotBlank(message = "이메일은 필수 입력값입니다.")
	@ApiModelProperty(value = "이메일", required = true, example = "123@ssafy.com")
	private String email;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@ApiModelProperty(value = "기존 비밀번호", required = true, example = "123a")
	private String pw;

	@NotBlank(message = "새로운 비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,14}|(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,14}$|(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,14}$",
		message = "영문, 숫자, 특수문자 중 2가지 이상을 포함한 8~14자의 비밀번호를 입력해주세요.")
	@ApiModelProperty(value = "새 비밀번호", required = true, example = "123a")
	private String pw_new;

	@NotBlank(message = "비밀번호 확인값을 입력해주세요.")
	@ApiModelProperty(value = "새 비밀번호 확인", required = true, example = "123a")
	private String pw_check;

}

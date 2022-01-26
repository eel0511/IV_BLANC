package com.ivblanc.api.dto.req;

import javax.validation.constraints.Email;
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
public class FcmPushReqDTO {
	@NotBlank
	@ApiModelProperty(value = "FCM 토큰", required = true, example = "토큰")
	private String targetToken;

	@NotBlank
	@ApiModelProperty(value = "메시지 제목", required = true, example = "제목")
	private String title;

	@NotBlank
	@ApiModelProperty(value = "메시지 내용", required = true, example = "내용")
	private String body;
}

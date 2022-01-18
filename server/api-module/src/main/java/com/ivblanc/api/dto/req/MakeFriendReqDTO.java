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
public class MakeFriendReqDTO {

	@NotBlank
	@ApiModelProperty(value = "applicant : 자신 이메일",required = true,example = "user@email.com")
	private String applicant;

	@NotBlank
	@ApiModelProperty(value = "friend : 친구 이메일",required = true,example = "friend@email.com")
	private String friend;
}

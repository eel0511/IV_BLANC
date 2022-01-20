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
public class FriendResDTO {
	@ApiModelProperty(value = "친구아이디", required = true, example = "1")
	private String friend_name;
}

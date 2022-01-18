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
public class FriendIdResDTO {
	@ApiModelProperty(value = "친구생성아이디", required = true, example = "1")
	private int id;
}

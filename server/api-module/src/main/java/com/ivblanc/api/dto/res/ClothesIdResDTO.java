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
public class ClothesIdResDTO {
	@ApiModelProperty(value = "옷 아이디(번호)", required = true, example = "1")
	private int clothes_id;
}

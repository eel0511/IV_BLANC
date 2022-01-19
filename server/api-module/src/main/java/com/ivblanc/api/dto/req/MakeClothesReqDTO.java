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
public class MakeClothesReqDTO {

	@NotBlank
	@ApiModelProperty(value = "category 카테고리 번호", required = true, example = "1")
	private int category;

	@NotBlank
	@ApiModelProperty(value = "color 옷 색깔", required = true, example = "검정")
	private String color;

	@NotBlank
	@ApiModelProperty(value = "material 옷 소재", required = true, example = "면")
	private String material;

	@NotBlank
	@ApiModelProperty(value = "size 옷 사이즈", required = true, example = "100")
	private int size;

	@NotBlank
	@ApiModelProperty(value = "season 계절 (1:봄, 2:여름, 3:가을, 4:겨울)", required = true, example = "1")
	private int season;

	@NotBlank
	@ApiModelProperty(value = "userId 옷 주인", required = true, example = "1")
	private int userId;
}

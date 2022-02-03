package com.ivblanc.api.dto.req;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MakeClothesReqDTO {

	@NotNull
	@ApiModelProperty(value = "category 카테고리 번호", required = true, example = "1")
	private int category;

	@NotBlank
	@ApiModelProperty(value = "color 옷 색깔", required = true, example = "검정")
	private String color;

	@NotNull
	@ApiModelProperty(value = "material 옷 소재", required = true, example = "1")
	private int material;

	@NotNull
	@ApiModelProperty(value = "size 옷 사이즈", required = true, example = "100")
	private int size;

	@NotNull
	@ApiModelProperty(value = "season 계절 (1:봄, 2:여름, 3:가을, 4:겨울)", required = true, example = "1")
	private int season;

	@JsonProperty("file")
	@ApiModelProperty(value = "사진")
	private MultipartFile file;
}

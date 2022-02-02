package com.ivblanc.api.dto.req;

import org.springframework.web.multipart.MultipartFile;

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
public class PhotoReqDTO {

	@ApiModelProperty(value = "사진")
	private MultipartFile file;
}

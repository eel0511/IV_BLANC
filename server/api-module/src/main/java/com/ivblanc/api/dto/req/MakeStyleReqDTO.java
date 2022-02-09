package com.ivblanc.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeStyleReqDTO {
    @NotNull
    @ApiModelProperty(value = "clothesIdList", required = true)
    private List<MakeStyleDetailReqDTO> clothesIdList;

    @NotNull
    @ApiModelProperty(value = "file", required = true)
    private MultipartFile file;
}

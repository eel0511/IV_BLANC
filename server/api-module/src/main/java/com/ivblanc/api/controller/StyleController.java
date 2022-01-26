package com.ivblanc.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeStyleDetailReqDTO;
import com.ivblanc.api.service.ClothesSerivce;
import com.ivblanc.api.service.StyleDetailService;
import com.ivblanc.api.service.StyleService;
import com.ivblanc.api.service.common.ListResult;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.Clothes;
import com.ivblanc.core.entity.Style;
import com.ivblanc.core.entity.StyleDetail;
import com.ivblanc.core.exception.ApiMessageException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"STYLE"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/style")
public class StyleController {

	private final ClothesSerivce clothesSerivce;
	private final StyleService styleService;
	private final StyleDetailService styleDetailService;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value = "Style 추가", notes = "여기서 madeby와 userId가 있는데 userId는 실 소유주고 \n"
		+ "madeby는 만약 친구가 만들었다면 여기에 만든사람이름을 넣으면 해결되지않을까 싶습니다. 추후 분리가 필요하면 말해주세요")
	@PostMapping(value = "/add")
	public @ResponseBody
	SingleResult<String> addStyle(@RequestBody List<MakeStyleDetailReqDTO> styleDetails, String madeby,
		int userId) throws Exception {
		Style style = styleDetailService.makeStyleDetailsToReqDTO(styleDetails, styleService.makeStyle(madeby, userId));
		styleService.addStyle(style);
		styleDetailService.addStyleDetails(style.getStyleDetails());
		return responseService.getSingleResult(style.getStyleId() + "번 스타일 추가완료");
	}

	@ApiOperation(value = "style 조회(userId로) 개개의 옷정보까지 한방에 다줌", notes = "userId로 자신의 스타일 전부를 볼 수있습니다.\n"
		+ "여기에는 모든 옷 정보까지 한번에 조회됩니다\n"
		+ "자신의 룩 보기에서 이를 이용해 style을 띄워주고 그 style을 누르면 styledetail들(옷)들이뜨고 개개의 옷을 누르면 옷 정보도 띄울수있게 한번에 정보를 긁어오는것입니다")
	@GetMapping(value = "/finduserstyle")
	public @ResponseBody
	ListResult<Style> findStyle(@RequestHeader(value = "X-AUTH-TOKEN")String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		List<Style> styleList = styleService.findAllByUserId(userId);
		if (styleList.size() == 0) {
			throw new ApiMessageException("없는 userId 입니다.");
		}
		//page 적용하고싶은데 mysql에서는 좀 어려워보임
		return responseService.getListResult(styleList);
	}

	@ApiOperation(value = "style 삭제")
	@DeleteMapping(value = "/delete")
	public @ResponseBody
	SingleResult<String> deleteStyle(@RequestParam int styleId) throws Exception {
		styleService.findByStyleId(styleId).orElseThrow(() -> new ApiMessageException("없는 styld_id 입니다"));
		styleService.deleteStyleById(styleId);
		return responseService.getSingleResult(styleId + "번 Style 삭제");
	}

	@ApiOperation(value = "style detail 삭제", notes = "style을 누르면 여러개의 포함된 styledetail들(옷) 이 보일텐데 이것을 하나만 삭제하는것입니다")
	@DeleteMapping(value = "/detail/delete")
	public @ResponseBody
	SingleResult<String> deleteStyleDetail(@RequestParam int sdId) throws Exception {
		styleDetailService.findBySdId(sdId).orElseThrow(() -> new ApiMessageException("없는 sd_id 입니다"));
		styleDetailService.deleteDetail(sdId);
		return responseService.getSingleResult(sdId + "번 Style Detail 삭제");
	}

	@ApiOperation(value = "style detail 수정", notes = "style을 누르면 여러개의 포함된 styledetail들(옷) 이 보일텐데 이것을 하나만 수정하는것입니다")
	@PutMapping(value = "/detail/update")
	public @ResponseBody
	SingleResult<String> updateStyleDetail(@RequestParam int sdId, @RequestParam int clothesId) throws Exception {
		StyleDetail styleDetail = styleDetailService.findBySdId(sdId)
			.orElseThrow(() -> new ApiMessageException("없는 sd_id 입니다"));
		Clothes clothes = clothesSerivce.findByClothesId(clothesId)
			.orElseThrow(() -> new ApiMessageException("없는 옷 번호입니다"));
		styleDetailService.addStyleDetail(styleDetailService.updateClothes(styleDetail, clothes));
		return responseService.getSingleResult(sdId + "번 Style Detail 수정");
	}

	@ApiOperation(value = "style favorite 추가")
	@GetMapping(value = "/addfavorite")
	public @ResponseBody
	SingleResult<String> addFavorite(@RequestParam int styleId) throws Exception {
		Style style = styleService.findByStyleId(styleId).orElseThrow(() -> new ApiMessageException("없는 styld_id 입니다"));
		styleService.addStyle(styleService.updateFavorite(style, 1));
		return responseService.getSingleResult(styleId + "번 Favorite");
	}

	@ApiOperation(value = "style favorite 추가")
	@GetMapping(value = "/deletefavorite")
	public @ResponseBody
	SingleResult<String> deleteFavorite(@RequestParam int styleId) throws Exception {
		Style style = styleService.findByStyleId(styleId).orElseThrow(() -> new ApiMessageException("없는 styld_id 입니다"));
		styleService.addStyle(styleService.updateFavorite(style, 0));
		return responseService.getSingleResult(styleId + "번 Favorite");
	}

}

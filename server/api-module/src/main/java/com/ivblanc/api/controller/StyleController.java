package com.ivblanc.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeStyleDetailReqDTO;
import com.ivblanc.api.service.StyleDetailService;
import com.ivblanc.api.service.StyleService;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.Style;
import com.ivblanc.core.entity.StyleDetail;

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

	private final StyleService styleService;
	private final StyleDetailService styleDetailService;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value = "addtest")
	@PostMapping(value = "/add")
	public @ResponseBody
	SingleResult<String> addStyle(@RequestBody List<MakeStyleDetailReqDTO> styleDetails, String madeby, String url,
		String photoname,int userId) throws Exception {
		List<StyleDetail> styleDetailList = new ArrayList<>();
		Style style = Style.builder()
			.madeby(madeby)
			.url(url)
			.photoName(photoname)
			.userId(userId)
			.build();
		styleService.addStyle(style);
		for (MakeStyleDetailReqDTO m : styleDetails) {
			StyleDetail styleDetail = StyleDetail.builder().clothesId(m.getClothes_id()).build();
			style.add(styleDetail);
			styleDetailService.addStyle(styleDetail);
		}

		return responseService.getSingleResult("aa");
	}

	@ApiOperation(value = "selecttest")
	@PostMapping(value = "/selete")
	public List<Style> findStyle(int styleId,int page) throws Exception {
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("styleId").descending());

		return styleService.findAllByUserId(styleId,pageRequest);
	}

}

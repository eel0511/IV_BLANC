package com.ivblanc.api.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeClothesReqDTO;
import com.ivblanc.api.dto.res.ClothesIdResDTO;
import com.ivblanc.api.service.ClothesSerivce;
import com.ivblanc.api.service.common.ListResult;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.Clothes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"CLOTHES"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clothes")
public class ClothesController {
	private final ClothesSerivce clothesSerivce;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value = "최근순으로 옷 조회")
	@GetMapping(value = "/date")
	public @ResponseBody
	ListResult<Clothes> findOrderByDate(int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByDate(userId));
	}

	@ApiOperation(value = "좋아요순로 옷 조회")
	@GetMapping(value = "/favorite")
	public @ResponseBody
	ListResult<Clothes> findOrderByFavorite(int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByFavorite(userId));
	}

	@ApiOperation(value = "옷 추가")
	@PostMapping(value = "/add")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> addClothes(@RequestBody MakeClothesReqDTO req) throws Exception {

		Clothes clothes = Clothes.builder()
			.category(req.getCategory())
			.color(req.getColor())
			.material(req.getMaterial())
			.date(new Timestamp(new Date().getTime()))
			.size(req.getSize())
			.season(req.getSeason())
			.userId(req.getUserId())
			.build();
		clothesSerivce.addClothes(clothes);
		return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothes_id()));
	}

	@ApiOperation(value = "옷 삭제 By Clothes_id")
	@DeleteMapping(value = "/deleteById")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> deleteClothes(int clothes_id) throws Exception {
		clothesSerivce.deleteClothesById(clothes_id);
		return responseService.getSingleResult(new ClothesIdResDTO(clothes_id));
	}

	@ApiOperation(value = "url 수정")
	@PutMapping(value = "/updateurl")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> updateUrl(int clothes_id, String url) throws Exception {
		Clothes clothes = clothesSerivce.findByClothesId(clothes_id).get();
		clothes.setUrl(url);
		clothesSerivce.addClothes(clothes);
		return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothes_id()));

	}
}
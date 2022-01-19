package com.ivblanc.api.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@ApiOperation(value = "최근순으로 자기 옷 조회")
	@GetMapping(value = "/date")
	public @ResponseBody
	ListResult<Clothes> findOrderByDate(int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByDate(userId));
	}

	@ApiOperation(value = "좋아요순로 자기 옷 조회")
	@GetMapping(value = "/like")
	public @ResponseBody
	ListResult<Clothes> findOrderByLike(int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByLike(userId));
	}

	@ApiOperation(value = "싫어요순로 자기 옷 조회")
	@GetMapping(value = "/dislike")
	public @ResponseBody
	ListResult<Clothes> findOrderByDisLike(int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByDisLike(userId));
	}

	@ApiOperation(value = "카테고리로 자기 옷 조회")
	@GetMapping(value = "/category")
	public @ResponseBody
	ListResult<Clothes> findClothesCategory(int page, int category, int userId) throws Exception {
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("clothesId").descending());
		return responseService.getListResult(clothesSerivce.findByCategory(pageRequest, category, userId));
	}

	@ApiOperation(value = "색깔로 자기 옷 조회")
	@GetMapping(value = "/color")
	public @ResponseBody
	ListResult<Clothes> findClothesColor(String color, int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findByColor(color, userId));
	}

	@ApiOperation(value = "소재로 자기 옷 조회")
	@GetMapping(value = "/material")
	public @ResponseBody
	ListResult<Clothes> findClothesMaterial(String material, int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findByMaterial(material, userId));
	}

	@ApiOperation(value = "계절별 자기 옷 조회")
	@GetMapping(value = "/season")
	public @ResponseBody
	ListResult<Clothes> findClothesSeason(int season, int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findBySeason(season, userId));
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
		return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothesId()));
	}

	@ApiOperation(value = "옷 삭제 By Clothes_id")
	@DeleteMapping(value = "/deleteById")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> deleteClothes(int clothesId) throws Exception {
		clothesSerivce.deleteClothesById(clothesId);
		return responseService.getSingleResult(new ClothesIdResDTO(clothesId));
	}

	@ApiOperation(value = "url 수정")
	@PutMapping(value = "/updateurl")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> updateUrl(int clothesId, String url) throws Exception {
		Clothes clothes = clothesSerivce.findByClothesId(clothesId).get();
		clothes.setUrl(url);
		clothesSerivce.addClothes(clothes);
		return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothesId()));

	}

}

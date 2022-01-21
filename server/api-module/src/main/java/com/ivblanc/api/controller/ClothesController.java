package com.ivblanc.api.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.rpc.ApiException;
import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeClothesReqDTO;
import com.ivblanc.api.dto.res.ClothesIdResDTO;
import com.ivblanc.api.service.ClothesSerivce;
import com.ivblanc.api.service.FileService;
import com.ivblanc.api.service.StyleDetailService;
import com.ivblanc.api.service.common.ListResult;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.Clothes;
import com.ivblanc.core.entity.StyleDetail;
import com.ivblanc.core.exception.ApiMessageException;

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
	private final StyleDetailService styleDetailService;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;
	private final FileService fileService;
	@ApiOperation(value = "최근순으로 자기 옷 조회(생성일기준)",notes = "옷 생성일 기준으로 빠른순 조회입니다.\n"
		+ " 정렬 등에 사용가능할거같습니다 back단에서")
	@GetMapping(value = "/createdate")
	public @ResponseBody
	ListResult<Clothes> findOrderByCreateDate(@RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByCreateDate(userId));
	}

	@ApiOperation(value = "최근순으로 자기 옷 조회(수정일기준)",notes = "옷이 style에 포함되거나 하면 updatedate가 갱신됩니다. \n"
		+ "이것으로 오랫동안 안입은옷 등의 판별을 할수있을것입니다")
	@GetMapping(value = "/updatedate")
	public @ResponseBody
	ListResult<Clothes> findOrderByDate(@RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByUpdateDate(userId));
	}

	@ApiOperation(value = "좋아요순로 자기 옷 조회")
	@GetMapping(value = "/like")
	public @ResponseBody
	ListResult<Clothes> findOrderByLike(@RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByLike(userId));
	}

	@ApiOperation(value = "싫어요순로 자기 옷 조회")
	@GetMapping(value = "/dislike")
	public @ResponseBody
	ListResult<Clothes> findOrderByDisLike(@RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByDisLike(userId));
	}

	@ApiOperation(value = "카테고리로 자기 옷 조회")
	@GetMapping(value = "/category")
	public @ResponseBody
	ListResult<Clothes> findClothesCategory(@RequestParam int page, @RequestParam int category,
		@RequestParam int userId) throws Exception {
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("clothesId").descending());
		return responseService.getListResult(clothesSerivce.findByCategory(pageRequest, category, userId));
	}

	@ApiOperation(value = "자기 옷 전체조회")
	@GetMapping(value = "/all")
	public @ResponseBody
	ListResult<Clothes> findAllClothes(@RequestParam int page,
		@RequestParam int userId) throws Exception {
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("clothesId").descending());
		return responseService.getListResult(clothesSerivce.findAll(userId, pageRequest));
	}

	@ApiOperation(value = "색깔로 자기 옷 조회")
	@GetMapping(value = "/color")
	public @ResponseBody
	ListResult<Clothes> findClothesColor(@RequestParam String color, @RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findByColor(color, userId));
	}

	@ApiOperation(value = "소재로 자기 옷 조회")
	@GetMapping(value = "/material")
	public @ResponseBody
	ListResult<Clothes> findClothesMaterial(@RequestParam int material, @RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findByMaterial(material, userId));
	}

	@ApiOperation(value = "계절별 자기 옷 조회")
	@GetMapping(value = "/season")
	public @ResponseBody
	ListResult<Clothes> findClothesSeason(@RequestParam int season, @RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findBySeason(season, userId));
	}

	@ApiOperation(value = "Count순으로 자기 옷 조회")
	@GetMapping(value = "/count")
	public @ResponseBody
	ListResult<Clothes> findOrderByCount(@RequestParam int userId) throws Exception {
		return responseService.getListResult(clothesSerivce.findOrderByCount(userId));
	}

	@ApiOperation(value = "옷 추가",notes = "MakeClothesReqDTO 와 함께 file도 같이 줘야합니다")
	@PostMapping(value = "/add")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> addClothes(@RequestBody MakeClothesReqDTO req)  throws Exception {
		if(req.getFile().isEmpty()){
			throw new ApiMessageException("사진이 없습니다");
		}
		String url = fileService.upload(req.getFile());
		if(url.equals("error")){
			throw new ApiMessageException("사진 저장 error");
		}
		Clothes clothes = Clothes.builder()
			.category(req.getCategory())
			.color(req.getColor())
			.material(req.getMaterial())
			.size(req.getSize())
			.season(req.getSeason())
			.userId(req.getUserId())
			.url(url)
			.build();
		clothesSerivce.addClothes(clothes);
		return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothesId()));
	}

	@ApiOperation(value = "옷 삭제 By Clothes_id",notes = "일반적인 옷 삭제이나 , 이 옷이 style에 활용되고있으면 삭제가 되지않습니다 Exception을 발생시킵니다")
	@DeleteMapping(value = "/deleteById")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> deleteClothes(@RequestParam int clothesId) throws Exception {

		//clothes 에서 style에 이용되고있으면 삭제안되게 추가 - 22.01.20 suhyeong
		List<StyleDetail> styleDetailList =styleDetailService.findAllByclothesId(clothesId);
		if(styleDetailList.size()!=0){
			throw new ApiMessageException("이 옷은 스타일에 이용되고있습니다");
		}
		clothesSerivce.deleteClothesById(clothesId);
		return responseService.getSingleResult(new ClothesIdResDTO(clothesId));
	}

	@ApiOperation(value = "url 수정",notes = "파이어베이스 스토리지에서 갱신된 url을 넣어주는건데 back에서 한번에 처리할수있을거같습니다. \n"
		+ "일단 삭제는 없이 유지시키겠습니다")
	@PutMapping(value = "/updateurl")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> updateUrl(@RequestParam int clothesId, @RequestParam String url) throws Exception {
		Clothes clothes = clothesSerivce.findByClothesId(clothesId).get();
		clothes.setUrl(url);
		clothesSerivce.addClothes(clothes);
		return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothesId()));

	}

	@ApiOperation(value = "N일 동안 update되지않은 옷 조회",notes = "특정 일수만큼 update되지않은 옷입니다 \n"
		+ "update는 옷으로 style을 만들거나 하면 갱신됩니다. \n"
		+ "특정일수를 넣어 ex) 2년동안 추가만되어있고 update되지않은옷 찾기 등에 활용할수있습니다")
	@GetMapping(value = "/notweardays")
	public @ResponseBody
	ListResult<Clothes> findNotWear(@RequestParam int days) throws Exception {
		return responseService.getListResult(clothesSerivce.findAllByDate(days));
	}

	@ApiOperation(value = "1년 동안 update되지않은 옷 조회",notes = "N일동안 update되지않은 옷 조회의 default 1년 버전입니다")
	@GetMapping(value = "/notwearyear")
	public @ResponseBody
	ListResult<Clothes> findNotWear() throws Exception {
		return responseService.getListResult(clothesSerivce.findAllByDate(365));
	}

	@ApiOperation(value = "즐겨찾기추가",notes = "즐겨찾기는 favorite 1로 바꾸는것으로 표현됩니다")
	@PutMapping(value = "/addfavorite")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> addFavorite(@RequestParam int clothesId) throws Exception {
		Optional<Clothes> clothes = clothesSerivce.findByClothesId(clothesId);
		if (!clothes.isPresent()) {
			throw new ApiMessageException("없는 옷 번호입니다");
		}
		clothes.get().setFavorite(1);
		clothesSerivce.addFavorite(clothes.get());
		return responseService.getSingleResult(new ClothesIdResDTO(clothesId));
	}

	@ApiOperation(value = "즐겨찾기삭제",notes = "즐겨찾기삭제는 favorite 0으로 만들어줍니다")
	@PutMapping(value = "/deletefavorite")
	public @ResponseBody
	SingleResult<ClothesIdResDTO> deleteFavorite(@RequestParam int clothesId) throws Exception {
		Optional<Clothes> clothes = clothesSerivce.findByClothesId(clothesId);
		if (!clothes.isPresent()) {
			throw new ApiMessageException("없는 옷 번호입니다");
		}
		clothes.get().setFavorite(0);
		clothesSerivce.addFavorite(clothes.get());
		return responseService.getSingleResult(new ClothesIdResDTO(clothesId));
	}
}

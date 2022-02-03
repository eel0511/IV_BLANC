package com.ivblanc.api.controller;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeClothesReqDTO;
import com.ivblanc.api.dto.res.ClothesIdResDTO;
import com.ivblanc.api.service.*;
import com.ivblanc.api.service.common.ListResult;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.Clothes;
import com.ivblanc.core.entity.StyleDetail;
import com.ivblanc.core.entity.User;
import com.ivblanc.core.exception.ApiMessageException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(tags = {"CLOTHES"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clothes")
public class ClothesController {
    private final ClothesSerivce clothesSerivce;
    private final UserService userService;
    private final FriendService friendService;
    private final StyleDetailService styleDetailService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileService fileService;
    private final int PAGE_SIZE = 30;

    @ApiOperation(value = "친구옷 조회하기",notes = "친구가 아니면 500")
    @GetMapping(value = "/friendclothes")
    public @ResponseBody
    ListResult<Clothes> findFriendClothes(@RequestHeader(value = "X-AUTH-TOKEN") String token, @RequestParam String email) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        User me = userService.findById(userId);
        User friend = userService.findByEmail(email);
        if (friendService.isRealFriend(me.getEmail(), friend.getEmail())) {
            return responseService.getListResult(clothesSerivce.findAll(friend.getUserId()));
        }
        return responseService.getFailResult(500,"친구가아닙니다",new ArrayList<>());
    }

    @ApiOperation(value = "최근순으로 자기 옷 조회(생성일기준)", notes = "옷 생성일 기준으로 빠른순 조회입니다.\n"
            + " 정렬 등에 사용가능할거같습니다 back단에서")
    @GetMapping(value = "/createdate")
    public @ResponseBody
    ListResult<Clothes> findOrderByCreateDate(@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findOrderByCreateDate(userId));
    }

    @ApiOperation(value = "최근순으로 자기 옷 조회(수정일기준)", notes = "옷이 style에 포함되거나 하면 updatedate가 갱신됩니다. \n"
            + "이것으로 오랫동안 안입은옷 등의 판별을 할수있을것입니다")
    @GetMapping(value = "/updatedate")
    public @ResponseBody
    ListResult<Clothes> findOrderByDate(@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findOrderByUpdateDate(userId));
    }

    @ApiOperation(value = "좋아요순로 자기 옷 조회")
    @GetMapping(value = "/like")
    public @ResponseBody
    ListResult<Clothes> findOrderByLike(@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findOrderByLike(userId));
    }

    @ApiOperation(value = "싫어요순로 자기 옷 조회")
    @GetMapping(value = "/dislike")
    public @ResponseBody
    ListResult<Clothes> findOrderByDisLike(@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findOrderByDisLike(userId));
    }

    @ApiOperation(value = "카테고리로 자기 옷 조회")
    @GetMapping(value = "/category")
    public @ResponseBody
    ListResult<Clothes> findClothesCategory(@RequestParam int page, @RequestParam int category,
                                            @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by("clothesId").descending());
        return responseService.getListResult(clothesSerivce.findByCategory(pageRequest, category, userId));
    }

    @ApiOperation(value = "자기 옷 전체조회", notes = "기본 30개씩 잘라서 page단위로 있습니다."
            + "0page 부터 시작합니다")
    @GetMapping(value = "/all")
    public @ResponseBody
    ListResult<Clothes> findAllClothes(
            @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findAll(userId));
    }

    @ApiOperation(value = "색깔로 자기 옷 조회")
    @GetMapping(value = "/color")
    public @ResponseBody
    ListResult<Clothes> findClothesColor(@RequestParam String color,
                                         @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findByColor(color, userId));
    }

    @ApiOperation(value = "소재로 자기 옷 조회")
    @GetMapping(value = "/material")
    public @ResponseBody
    ListResult<Clothes> findClothesMaterial(@RequestParam int material,
                                            @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findByMaterial(material, userId));
    }

    @ApiOperation(value = "계절별 자기 옷 조회")
    @GetMapping(value = "/season")
    public @ResponseBody
    ListResult<Clothes> findClothesSeason(@RequestParam int season,
                                          @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findBySeason(season, userId));
    }

    @ApiOperation(value = "Count순으로 자기 옷 조회")
    @GetMapping(value = "/count")
    public @ResponseBody
    ListResult<Clothes> findOrderByCount(@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        return responseService.getListResult(clothesSerivce.findOrderByCount(userId));
    }

    @ApiOperation(value = "firebase Storage에 업로드", notes = "정상업로드되면 url 반환")
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.upload(multipartFile);
    }

    @ApiOperation(value = "옷 추가 firebase Storage에 업로드 후 db에 저장까지 한번에", notes = "swagger는 안되는데 postman은 정상적입니다")
    @PostMapping(value = "/add")
    public @ResponseBody
    SingleResult<ClothesIdResDTO> addClothes(@Valid MakeClothesReqDTO req,
                                             @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        String url = fileService.upload(req.getFile());
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        if (url.equals("error")) {
            throw new ApiMessageException("파일 올리기 실패");
        }
        Clothes clothes = clothesSerivce.MakeClotehsByReqDToAndUrl(req, url, userId);
        clothesSerivce.addClothes(clothes);
        // flask 연동
        RestTemplate restTemplate = new RestTemplate();
        String sendurl ="http://i6d104.p.ssafy.io:5000/";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file",req.getFile());
        body.add("clothId",clothes.getClothesId()+"");
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);
        //
        return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothesId()));
    }

    @ApiOperation(value = "사진없이 그냥 test용입니다")
    @PostMapping(value = "/addtest")
    public @ResponseBody
    SingleResult<ClothesIdResDTO> addtestClothes(@Valid @RequestBody MakeClothesReqDTO req,
                                                 @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
        int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
        Clothes clothes = clothesSerivce.MakeClothesByReqDTO(req, userId);
        clothesSerivce.addClothes(clothes);
        return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothesId()));
    }

    @ApiOperation(value = "옷 삭제 By Clothes_id", notes = "일반적인 옷 삭제이나 , 이 옷이 style에 활용되고있으면 삭제가 되지않습니다 Exception을 발생시킵니다")
    @DeleteMapping(value = "/deleteById")
    public @ResponseBody
    SingleResult<ClothesIdResDTO> deleteClothes(@RequestParam int clothesId) throws Exception {

        //clothes 에서 style에 이용되고있으면 삭제안되게 추가 - 22.01.20 suhyeong
        List<StyleDetail> styleDetailList = styleDetailService.findAllByclothesId(clothesId);
        if (styleDetailList.size() != 0) {
            throw new ApiMessageException("이 옷은 스타일에 이용되고있습니다");
        }
        clothesSerivce.deleteClothesById(clothesId);
        return responseService.getSingleResult(new ClothesIdResDTO(clothesId));
    }

    @ApiOperation(value = "N일 동안 update되지않은 옷 조회", notes = "특정 일수만큼 update되지않은 옷입니다 \n"
            + "update는 옷으로 style을 만들거나 하면 갱신됩니다. \n"
            + "특정일수를 넣어 ex) 2년동안 추가만되어있고 update되지않은옷 찾기 등에 활용할수있습니다")
    @GetMapping(value = "/notweardays")
    public @ResponseBody
    ListResult<Clothes> findNotWear(@RequestParam int days) throws Exception {
        return responseService.getListResult(clothesSerivce.findAllByDate(days));
    }

    @ApiOperation(value = "1년 동안 update되지않은 옷 조회", notes = "N일동안 update되지않은 옷 조회의 default 1년 버전입니다")
    @GetMapping(value = "/notwearyear")
    public @ResponseBody
    ListResult<Clothes> findNotWear() throws Exception {
        return responseService.getListResult(clothesSerivce.findAllByDate(365));
    }

    @ApiOperation(value = "즐겨찾기추가", notes = "즐겨찾기는 favorite 1로 바꾸는것으로 표현됩니다")
    @PutMapping(value = "/addfavorite")
    public @ResponseBody
    SingleResult<ClothesIdResDTO> addFavorite(@RequestParam int clothesId) throws Exception {
        Optional<Clothes> clothes = clothesSerivce.findByClothesId(clothesId);
        if (!clothes.isPresent()) {
            throw new ApiMessageException("없는 옷 번호입니다");
        }
        clothesSerivce.addFavorite(clothesSerivce.updateFavorite(clothes.get(), 1));
        return responseService.getSingleResult(new ClothesIdResDTO(clothesId));
    }

    @ApiOperation(value = "즐겨찾기삭제", notes = "즐겨찾기삭제는 favorite 0으로 만들어줍니다")
    @PutMapping(value = "/deletefavorite")
    public @ResponseBody
    SingleResult<ClothesIdResDTO> deleteFavorite(@RequestParam int clothesId) throws Exception {
        Optional<Clothes> clothes = clothesSerivce.findByClothesId(clothesId);
        if (!clothes.isPresent()) {
            throw new ApiMessageException("없는 옷 번호입니다");
        }
        clothesSerivce.addFavorite(clothesSerivce.updateFavorite(clothes.get(), 0));
        return responseService.getSingleResult(new ClothesIdResDTO(clothesId));
    }

    // 일단 놔둔것
    @ApiOperation(value = "url 수정", notes = "파이어베이스 스토리지에서 갱신된 url을 넣어주는건데 back에서 한번에 처리할수있을거같습니다. \n"
            + "일단 삭제는 없이 유지시키겠습니다")
    @PutMapping(value = "/updateurl")
    public @ResponseBody
    SingleResult<ClothesIdResDTO> updateUrl(@RequestParam int clothesId, @RequestParam String url) throws Exception {
        Clothes clothes = clothesSerivce.findByClothesId(clothesId).get();
        clothes.setUrl(url);
        clothesSerivce.addClothes(clothes);
        return responseService.getSingleResult(new ClothesIdResDTO(clothes.getClothesId()));

    }
}

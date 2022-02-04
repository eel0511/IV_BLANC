package com.ivblanc.api.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.multipart.MultipartFile;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.HistoryReqDTO;
import com.ivblanc.api.dto.req.PhotoReqDTO;
import com.ivblanc.api.service.HistoryService;
import com.ivblanc.api.service.PhotoService;
import com.ivblanc.api.service.UserService;
import com.ivblanc.api.service.common.ListResult;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.History;
import com.ivblanc.core.entity.Photo;
import com.ivblanc.core.exception.ApiMessageException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"HISTORY"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/history")
public class HistoryController {

	private final PhotoService photoService;
	private final HistoryService historyService;

	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@ApiOperation(value = "History 추가", notes = "사진도 동시에 추가됨. Weather은 (맑음|흐림|비|눈) 중 하나로 입력")
	@PostMapping(value = "/add")
	public @ResponseBody
	SingleResult<String> addHistory(@Valid HistoryReqDTO historyReqDTO,
		@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		if(userService.findById(userId) == null){
			throw new ApiMessageException("존재하지 않는 userId 입니다.");
		}
		History history = historyService.makeHistory(historyReqDTO, userId);
		if(historyReqDTO.getPhotoList() != null && historyReqDTO.getPhotoList().length > 0) {
			history = photoService.MakePhoto(Arrays.asList(historyReqDTO.getPhotoList()), history);
		}
		historyService.addHistory(history);
		photoService.addPhotos(history.getPhotos());

		return responseService.getSingleResult(history.getHistoryId() + "번 히스토리 추가완료");
	}

	@ApiOperation(value = "전체 히스토리 조회", notes = "userId로 전체 히스토리 조회")
	@GetMapping(value = "/find/all")
	public @ResponseBody
	ListResult<History> findAllHistory(@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		List<History> historyList = historyService.findAll(userId);
		if (historyList.size() == 0) {
			throw new ApiMessageException("등록된 히스토리가 없습니다.");
		}

		return responseService.getListResult(historyList);
	}

	@ApiOperation(value = "해당 월 히스토리 조회", notes = "userId로 해당 월 히스토리 조회")
	@GetMapping(value = "/find/thisMonth")
	public @ResponseBody
	ListResult<History> findHistoryThisMonth(@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		List<History> historyList = historyService.findByYearAndMonth(LocalDateTime.now(), userId);
		if (historyList.size() == 0) {
			throw new ApiMessageException("이번 달에 등록된 히스토리가 없습니다.");
		}

		return responseService.getListResult(historyList);
	}

	@ApiOperation(value = "특정 년월 히스토리 조회", notes = "userId로 특정 년월 히스토리 조회")
	@GetMapping(value = "/find/month")
	public @ResponseBody
	ListResult<History> findHistorySelectedMonth(@RequestParam int year, @RequestParam int month, @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		List<History> historyList = historyService.findByYearAndMonth(year, month, userId);
		if (historyList.size() == 0) {
			throw new ApiMessageException("해당 기간에 등록된 히스토리가 없습니다.");
		}

		return responseService.getListResult(historyList);
	}

	@ApiOperation(value = "특정일 히스토리 조회", notes = "userId로 특정일 히스토리 조회")
	@GetMapping(value = "/find/date")
	public @ResponseBody
	SingleResult<History> findHistorySelectedDate(@RequestParam String date, @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		History history = historyService.findByDate(date, userId);
		if (history == null) {
			throw new ApiMessageException("해당 일에 등록된 히스토리가 없습니다.");
		}

		return responseService.getSingleResult(history);
	}

	@ApiOperation(value = "날씨별 히스토리 조회", notes = "userId로 날씨별 히스토리 조회")
	@GetMapping(value = "/find/weather")
	public @ResponseBody
	ListResult<History> findHistoryByWeather(@RequestParam String weather, @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		List<History> historyList = historyService.findByWeather(weather, userId);
		if (historyList.size() == 0) {
			throw new ApiMessageException("존재하지 않는 userId 입니다.");
		}

		return responseService.getListResult(historyList);
	}

	@ApiOperation(value = "기온별 히스토리 조회", notes = "userId로 기온별 히스토리 조회.\n 최저기온, 최고기온 입력하면 그 범위 내에 있는 날의 히스토리를 보여준다.")
	@GetMapping(value = "/find/temperature")
	public @ResponseBody
	ListResult<History> findHistoryByTemperature(@RequestParam(value = "최저기온") int temp_low, @RequestParam(value = "최고기온") int temp_high, @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		List<History> historyList = historyService.findByTemperature(temp_low, temp_high, userId);
		if (historyList.size() == 0) {
			throw new ApiMessageException("존재하지 않는 userId 입니다.");
		}

		return responseService.getListResult(historyList);
	}

	@ApiOperation(value = "제목으로 히스토리 조회", notes = "userId, 제목으로 히스토리 조회\n 지금은 입력한 제목이랑 정확히 일치하는 히스토리만 반환하는데 필요시 입력한 제목을 포함하는 히스트리를 반환하게끔 수정하겠습니다.")
	@GetMapping(value = "/find/subject")
	public @ResponseBody
	ListResult<History> findHistoryBySubject(@RequestParam String subject, @RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		List<History> historyList = historyService.findBySubject(subject, userId);
		if (historyList.size() == 0) {
			throw new ApiMessageException("존재하지 않는 userId 입니다.");
		}

		return responseService.getListResult(historyList);
	}

	@ApiOperation(value = "히스토리 삭제")
	@DeleteMapping(value = "/delete")
	public @ResponseBody
	SingleResult<String> deleteHistory(@RequestParam int historyId) throws Exception {
		historyService.findByHistoryId(historyId).orElseThrow(() -> new ApiMessageException("존재하지 않는 history_id 입니다"));
		historyService.deleteHistoryById(historyId); // 히스토리 삭제 시 해당 히스토리의 포토 튜플도 삭제되는지 확인 필요 - 02.04 확인완료
		return responseService.getSingleResult(historyId + "번 히스토리가 삭제 되었습니다.");
	}

	@ApiOperation(value = "히스토리 수정")
	@PutMapping(value = "/update")
	public @ResponseBody
	SingleResult<String> updateHistory(@RequestParam int historyId, @Valid @RequestBody HistoryReqDTO historyReqDTO,
		@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {

		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		if(userService.findById(userId) == null){
			throw new ApiMessageException("존재하지 않는 userId 입니다.");
		}
		Optional<History> history = historyService.findByHistoryId(historyId);
		if(!history.isPresent()){
			throw new ApiMessageException("존재하지 않는 historyId 입니다.");
		}
		historyService.updateHistory(history.get(), historyReqDTO);

		return responseService.getSingleResult(historyId + "번 히스토리 수정 완료");
	}

	@ApiOperation(value = "히스토리 사진 추가", notes = "history에 사진 추가")
	@PostMapping(value = "/photo/add")
	public @ResponseBody
	SingleResult<String> addHistoryPhotos(@RequestParam int historyId, @RequestParam MultipartFile[] photoList,
		@RequestHeader(value = "X-AUTH-TOKEN") String token) throws Exception {
		int userId = Integer.parseInt(jwtTokenProvider.getUserPk(token));
		Optional<History> op = historyService.findByHistoryId(historyId);
		if(userService.findById(userId) == null){
			throw new ApiMessageException("존재하지 않는 userId 입니다.");
		} else if (!op.isPresent()) {
			throw new ApiMessageException("존재하지 않는 historyId 입니다.");
		}

		History history = op.get();
		if (history.getUserId() != userId) {
			throw new ApiMessageException("해당 회원의 history가 아닙니다.");
		}

		history = photoService.MakePhoto(Arrays.asList(photoList), history);
		historyService.addHistory(history);

		return responseService.getSingleResult(historyId + "번 히스토리에 " + photoList.length + "개의 사진이 추가되었습니다.");
	}

	@ApiOperation(value = "히스토리 사진 삭제", notes = "history에 포함된 사진들 중 하나 삭제")
	@DeleteMapping(value = "/photo/delete")
	public @ResponseBody
	SingleResult<String> deleteHistoryPhoto(@RequestParam int photoId) throws Exception {
		photoService.findByPhotoId(photoId).orElseThrow(() -> new ApiMessageException("존재하지 않는 photo_id 입니다"));
		photoService.deletePhoto(photoId);
		return responseService.getSingleResult(photoId + "번 사진이 삭제 되었습니다.");
	}

	@ApiOperation(value = "히스토리 사진 수정", notes = "history에 포함된 사진들 중 하나 수정")
	@PutMapping(value = "/photo/update")
	public @ResponseBody
	SingleResult<String> updateHistoryPhoto(@RequestParam int photoId, @RequestBody PhotoReqDTO newPhoto) throws Exception {
		Photo photo = photoService.findByPhotoId(photoId)
			.orElseThrow(() -> new ApiMessageException("없는 photo_id 입니다"));
		photoService.addPhoto(photoService.updatePhoto(photo, newPhoto.getFile()));
		return responseService.getSingleResult(photoId + "번 photo 수정");
	}

}

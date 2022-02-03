package com.ivblanc.api.controller;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeFriendReqDTO;
import com.ivblanc.api.dto.res.FriendResDTO;
import com.ivblanc.api.service.FcmService;
import com.ivblanc.api.service.FriendService;
import com.ivblanc.api.service.UserService;
import com.ivblanc.api.service.common.ListResult;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"FRIEND"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/friend")
public class FriendController {
	private final FriendService friendService;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;
	private final FcmService fcmService;
	private final UserService userService;

	@ApiOperation(value = "전체친구조회(수락여부 상관없이)", notes = "수락여부 상관없이 자신이 신청한 모든 목록 return")
	@GetMapping(value = "/all")
	public ListResult<FriendResDTO>
	findAllFriend(@RequestParam String applicant) throws Exception {
		return responseService.getListResult(
			friendService.MakeFriendToResDTO(friendService.findUserFriends(applicant)));
	}

	@ApiOperation(value = "친구수락한 친구조회", notes = "수락한 친구(상태 Y)에 대해 볼 수있습니다.\n "
		+ "친구옷장가기 등에서 이것을 이용해 수락된 친구 리스트를 뽑아낼 수 있을거같습니다.")
	@GetMapping(value = "/isaccept")
	public ListResult<FriendResDTO>
	findAllAcceptFriend(@RequestParam String applicant) throws Exception {
		return responseService.getListResult(
			friendService.MakeFriendToResDTO(friendService.findUserFriendsIsAccept(applicant)));
	}

	@ApiOperation(value = "친구수락하지 않은 친구조회", notes = "아직 상태가 N인 친구에대해 볼 수 있습니다.\n "
		+ "이 리스트를 이용해 fcm으로 독촉을 보내던지 아니면 친구요청 취소를 할수있는 페이지에 활용할수있을거같습니다")
	@GetMapping(value = "/isnotaccept")
	public ListResult<FriendResDTO>
	findAllNotAcceptFriend(@RequestParam String applicant) throws Exception {
		return responseService.getListResult(
			friendService.MakeFriendToResDTO(friendService.findUserFriendsIsNotAccept(applicant)));
	}

	@ApiOperation(value = "친구수락", notes = "자신과 친구의 상태를 서로 양방향 Y로 연결합니다\n"
		+ "applicant, friend를 받는데 요청당한 입장에서는 applicant가 friend고 friend가 applicant로 넣어주셔야할거같습니다.\n"
		+ "요청한 입장에서는 수락창이 뜨지않게 해야합니다. 요청하고 혼자 수락할수없게")
	@PostMapping(value = "/isaccept")
	public @ResponseBody
	SingleResult<FriendResDTO> acceptFriend(@Valid @RequestBody MakeFriendReqDTO req) throws Exception {
		friendService.makeFriend(friendService.findUserFreind(req.getFriendName(), req.getApplicant()));
		return responseService.getSingleResult(new FriendResDTO(req.getFriendName()));

	}

	@ApiOperation(value = "친구삭제", notes = "친구가 수락된 상태에서 양방향 연결 둘다 삭제합니다.")
	@DeleteMapping(value = "/delete")
	public @ResponseBody
	SingleResult<FriendResDTO> deleteFriend(@Valid @RequestBody MakeFriendReqDTO req) throws Exception {

		friendService.deleteFriend(friendService.findUserFreind(req.getApplicant(), req.getFriendName()));
		friendService.deleteFriend(friendService.findUserFreind(req.getFriendName(), req.getApplicant()));
		return responseService.getSingleResult(new FriendResDTO(req.getFriendName()));
	}

	@ApiOperation(value = "친구 요청 취소", notes = "N인 친구신청 목록에 대해 취소를 할 수 있습니다.\n"
		+ "단방향으로 연결된 상태이므로 요청자의 입장에서만 사용가능하게 해야합니다")
	@DeleteMapping(value = "/cancel")
	@ResponseBody
	SingleResult<FriendResDTO> cancelFriend(@Valid @RequestBody MakeFriendReqDTO req) throws Exception {
		friendService.deleteFriend(friendService.findUserFreind(req.getApplicant(), req.getFriendName()));
		return responseService.getSingleResult(new FriendResDTO(req.getFriendName()));
	}

	@ApiOperation(value = "친구추가", notes = "친구 email을 등록하여 상태 N으로 단방향 연결합니다.\n"
		+ "이때 api 요청과함께 fcm등을 보낼수 있을거같습니다.")
	@PostMapping(value = "/request")
	public @ResponseBody
	SingleResult<FriendResDTO> addFriend(@Valid @RequestBody MakeFriendReqDTO req) throws Exception {

		friendService.addFriend(req);
		User friend = userService.findByEmail(req.getFriendName());
		//fcm 없을시 추가만됨
		if(friend.getToken_fcm().equals("string")){
			return responseService.getSingleResult(new FriendResDTO(req.getFriendName()));
		}
		fcmService.sendMessageTo(userService.findByEmail(req.getFriendName()).getToken_fcm(), "친구요청 알림",
			userService.findByEmail(req.getApplicant()).getName() + "님이 친구요청을 보냈습니다.");
		System.out.println(req.getFriendName());
		return responseService.getSingleResult(new FriendResDTO(req.getFriendName()));
	}

	@ApiOperation(value = "친구요청 보기", notes = "자신이 받은 상태 N인 요청을 모두 볼수있습니다.\n"
		+ "여기서 수락버튼을 만들어 수락시 아래의 친구수락 api로 쏘면될거같습니다")
	@GetMapping(value = "/friendrequest")
	public ListResult<FriendResDTO>
	findRequest(@RequestParam String applicant) throws Exception {
		return responseService.getListResult(friendService.MakeFriendToResDTO(friendService.findRequest(applicant)));
	}
}

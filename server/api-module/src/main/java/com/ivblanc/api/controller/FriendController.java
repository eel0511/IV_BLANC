package com.ivblanc.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.dto.req.MakeFriendReqDTO;
import com.ivblanc.api.dto.res.FriendResDTO;
import com.ivblanc.api.service.FriendService;
import com.ivblanc.api.service.common.ListResult;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.api.service.common.SingleResult;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.Friend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"FRIEND"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/friend")
public class FriendController {
	private final FriendService friendService;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value = "전체친구조회(수락여부 상관없이)", notes = "전체친구조회")
	@GetMapping(value = "/all")
	public ListResult<FriendResDTO>
	findAllFriend(String applicant) throws Exception {
		List<Friend> friendList = friendService.findUserFriends(applicant);
		List<FriendResDTO> friendResDTOList = new ArrayList<>();
		for (Friend f : friendList) {
			friendResDTOList.add(new FriendResDTO(f.getFriendName()));
		}
		return responseService.getListResult(friendResDTOList);
	}

	@ApiOperation(value = "친구수락한 친구조회")
	@GetMapping(value = "/isaccept")
	public ListResult<FriendResDTO>
	findAllAcceptFriend(String applicant) throws Exception {
		List<Friend> friendList = friendService.findUserFriendsIsAccept(applicant);
		List<FriendResDTO> friendResDTOList = new ArrayList<>();
		for (Friend f : friendList) {
			friendResDTOList.add(new FriendResDTO(f.getFriendName()));
		}
		return responseService.getListResult(friendResDTOList);
	}

	@ApiOperation(value = "친구수락하지 않은 친구조회")
	@GetMapping(value = "/isnotaccept")
	public ListResult<FriendResDTO>
	findAllNotAcceptFriend(String applicant) throws Exception {
		List<Friend> friendList = friendService.findUserFriendsIsNotAccept(applicant);
		List<FriendResDTO> friendResDTOList = new ArrayList<>();
		for (Friend f : friendList) {
			friendResDTOList.add(new FriendResDTO(f.getFriendName()));
		}
		return responseService.getListResult(friendResDTOList);
	}

	@ApiOperation(value = "친구수락")
	@PostMapping(value = "/isaccept")
	public @ResponseBody
	SingleResult<FriendResDTO> acceptFriend(@RequestBody MakeFriendReqDTO req) throws Exception {
		Friend me = friendService.findUserFreind(req.getApplicant(), req.getFriend_name());
		me.setIsaccept(YNCode.Y);
		friendService.makeFriend(me);
		Friend friend = Friend.builder()
			.applicant(req.getFriend_name())
			.friendName(req.getApplicant())
			.isaccept(YNCode.Y)
			.build();
		friendService.makeFriend(friend);
		return responseService.getSingleResult(new FriendResDTO(me.getFriendName()));

	}

	@ApiOperation(value = "친구삭제")
	@DeleteMapping(value = "/delete")
	public @ResponseBody
	SingleResult<FriendResDTO> deleteFriend(@Valid MakeFriendReqDTO req) throws Exception {
		Friend me = friendService.findUserFreind(req.getApplicant(), req.getFriend_name());
		Friend friend = friendService.findUserFreind(req.getFriend_name(), req.getApplicant());
		friendService.deleteFriend(me);
		friendService.deleteFriend(friend);
		return responseService.getSingleResult(new FriendResDTO(me.getFriendName()));
	}

	@ApiOperation(value = "친구 요청 취소")
	@DeleteMapping(value = "/cancel")
	@ResponseBody
	SingleResult<FriendResDTO> cancelFriend(@Valid MakeFriendReqDTO req) throws Exception {
		Friend me = friendService.findUserFreind(req.getApplicant(), req.getFriend_name());
		friendService.deleteFriend(me);
		return responseService.getSingleResult(new FriendResDTO(me.getFriendName()));
	}

	@ApiOperation(value = "친구추가")
	@PostMapping(value = "/request")
	public @ResponseBody
	SingleResult<FriendResDTO> addFriend(@RequestBody MakeFriendReqDTO req) throws Exception {
		Friend friend = Friend.builder()
			.applicant(req.getApplicant())
			.friendName(req.getFriend_name())
			.isaccept(YNCode.N)
			.build();
		friendService.addFriend(friend);
		return responseService.getSingleResult(new FriendResDTO(friend.getFriendName()));
	}
}

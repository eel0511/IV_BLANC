package com.ivblanc.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ivblanc.api.dto.req.MakeFriendReqDTO;
import com.ivblanc.api.dto.res.FriendResDTO;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.Friend;
import com.ivblanc.core.repository.FriendRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {
	private final FriendRepository friendRepository;

	public List<Friend> findUserFriends(String applicant) throws Exception {
		return friendRepository.findAllByApplicant(applicant);
	}

	public List<Friend> findUserFriendsIsAccept(String applicant) throws Exception {
		return friendRepository.findAllByApplicantAndIsaccept(applicant, YNCode.Y);
	}

	public List<Friend> findUserFriendsIsNotAccept(String applicant) throws Exception {
		return friendRepository.findAllByApplicantAndIsaccept(applicant, YNCode.N);
	}

	public Friend findUserFreind(String applicant, String friendName) throws Exception {
		return friendRepository.findByApplicantAndFriendName(applicant, friendName);
	}

	public List<Friend> findRequest(String applicant)throws Exception{
		return friendRepository.findAllByFriendNameAndIsaccept(applicant, YNCode.N);
	}
	public void makeFriend(Friend me) {
		me.setIsaccept(YNCode.Y);
		friendRepository.save(me);
		Friend friend = Friend.builder()
			.applicant(me.getFriendName())
			.friendName(me.getApplicant())
			.isaccept(YNCode.Y)
			.build();
		friendRepository.save(friend);
	}

	public void deleteFriend(Friend friend) {
		friendRepository.delete(friend);
	}

	public void addFriend(MakeFriendReqDTO req) {
		Friend friend = Friend.builder()
			.applicant(req.getApplicant())
			.friendName(req.getFriendName())
			.isaccept(YNCode.N)
			.build();
		friendRepository.save(friend);
	}

	public List<FriendResDTO> MakeFriendToResDTO(List<Friend> friendList){
		List<FriendResDTO> friendResDTOList = new ArrayList<>();
		for (Friend f : friendList) {
			friendResDTOList.add(new FriendResDTO(f.getFriendName()));
		}
		return friendResDTOList;
	}
}

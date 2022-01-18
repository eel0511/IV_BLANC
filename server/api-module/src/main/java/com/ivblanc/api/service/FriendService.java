package com.ivblanc.api.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivblanc.api.dto.res.FriendResDTO;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.converter.YNCodeConverter;
import com.ivblanc.core.entity.Friend;
import com.ivblanc.core.repository.FriendRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {
	private final FriendRepository friendRepository;


	public List<Friend> findUserFriends(String applicant) throws Exception{
		return friendRepository.findAllByApplicant(applicant);
	}
	public List<Friend> findUserFriendsIsAccept(String applicant) throws Exception{
		return friendRepository.findAllByApplicantAndIsaccept(applicant,YNCode.Y);
	}
	public List<Friend> findUserFriendsIsNotAccept(String applicant) throws Exception{
		return friendRepository.findAllByApplicantAndIsaccept(applicant,YNCode.N);
	}
	public Friend findUserFreind(String applicant, String friendName) throws Exception{
		return friendRepository.findByApplicantAndFriendName(applicant, friendName);
	}
	public int makeFriend(Friend friend){
		Friend newFriend = friendRepository.save(friend);
		return newFriend.getFriend_id();
	}
	public void deleteFriend(Friend friend){
		friendRepository.delete(friend);
	}
	public void addFriend(Friend friend){
		friendRepository.save(friend);
	}


}

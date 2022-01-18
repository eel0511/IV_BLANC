package com.ivblanc.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.converter.YNCodeConverter;
import com.ivblanc.core.entity.Friend;
import com.ivblanc.core.repository.FriendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {
	private final FriendRepository friendRepository;


	public List<String> findUserFriends(String applicant) throws Exception{
		return friendRepository.findUserFriends(applicant);
	}
	public List<String> findUserFriendsIsAccept(String applicant) throws Exception{
		return friendRepository.findUserFriendsIsAccept(applicant);
	}
	public List<String> findUserFriendsIsNotAccept(String applicant) throws Exception{
		return friendRepository.findUserFriendsIsNotAccept(applicant);
	}
	public Friend saveFriend(Friend friend) throws Exception{
		return friendRepository.save(friend);
	}
	public Friend updateAccept(Friend friend) throws  Exception{
		Friend friend1 = friendRepository.findUserOneFriend(friend.getApplicant(),friend.getFriend());
		friend1.setIsaccept(YNCode.N);
		return friendRepository.save(friend1);
	}
}

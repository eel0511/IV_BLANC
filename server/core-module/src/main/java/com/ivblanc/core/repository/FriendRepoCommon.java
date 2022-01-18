package com.ivblanc.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.Friend;

@Repository
public interface FriendRepoCommon {

	@Query("select friend from Friend where applicant like :applicant")
	List<String> findUserFriends(String applicant);

	@Query("select friend from Friend where applicant like :applicant and isaccept = 'Y'")
	List<String> findUserFriendsIsAccept(String applicant);

	@Query("select friend from Friend where applicant like :applicant and isaccept = 'N'")
	List<String> findUserFriendsIsNotAccept(String applicant);

	@Query("select friend_id,applicant,friend,isaccept from Friend where applicant like :applicant and friend like :friend")
	Friend findUserOneFriend(String applicant, String friend);
}

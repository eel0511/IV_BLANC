package com.ivblanc.core.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.Friend;

@Repository
public interface FriendRepoCommon {

	Friend findByApplicantAndFriendName(String applicant, String friendName);

	List<Friend> findAllByApplicant(String applicant);

	List<Friend> findAllByApplicantAndIsaccept(String applicant, YNCode isaccept);
}

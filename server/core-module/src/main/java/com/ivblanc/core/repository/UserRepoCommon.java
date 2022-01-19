package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.code.JoinCode;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.User;

@Repository
public interface UserRepoCommon {

	User findUserLogin(String uid, int type);

	User findByUid(String uid);


	User updatePassword(String uid, String password);

	User updatePersonalInfo(String uid, int age, int gender, String phone);



}







































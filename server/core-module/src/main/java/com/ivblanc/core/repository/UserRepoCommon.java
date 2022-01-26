package com.ivblanc.core.repository;

import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.User;

@Repository
public interface UserRepoCommon {

	User findUserLogin(String email, int type);

	User findByEmail(String email);

	User findByUserId(int userId);
}







































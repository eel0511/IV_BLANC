package com.ivblanc.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivblanc.core.entity.User;
import com.ivblanc.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;


	public User findByUid(String uid){
		return userRepository.findByUid(uid);
	}

	public User updatePassword(String uid, String password){
		return userRepository.updatePassword(uid, password);
	}

	public User updatePersonal(String uid, int age, int gender, String phone){
		return userRepository.updatePersonalInfo(uid, age, gender, phone);
	}

	public void deleteUser(long id){
		userRepository.deleteById(id);
	}
}

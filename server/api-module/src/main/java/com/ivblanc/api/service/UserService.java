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

	public User insertUser(String email, String password, String name, int age, int gender, String phone, int social){
		return userRepository.insertUser(email, password, name, age, gender, phone, social);
	}

	public User getUserDetailById(int user_id){
		return userRepository.getUserDetailById(user_id);
	}

	public User getUserDetailByEmail(String email){
		return userRepository.getUserDetailByEmail(email);
	}

	public User updatePassword(int user_id, String password){
		return userRepository.updatePasswordById(user_id, password);
	}

	public User updatePersonal(int user_id, int age, int gender, String phone){
		return userRepository.updatePersonalInfo(user_id, age, gender, phone);
	}

	public int deleteUser(int user_id){
		return userRepository.deleteUser(user_id);
	}
}

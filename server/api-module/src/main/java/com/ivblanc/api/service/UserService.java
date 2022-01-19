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

	public User updatePasswordById(int user_id, String password){
		return userRepository.updatePasswordById(user_id, password);
	}

	public User updatePasswordByEmail(int email, String password){
		return userRepository.updatePasswordByEmail(email, password);
	}

	public User updateAge(int user_id, int age){
		return userRepository.updateAge(user_id, age);
	}

	public User updateGender(int user_id, int gender){
		return userRepository.updateGender(user_id, gender);
	}

	public User updatePhone(int user_id, String phone){
		return userRepository.updatePhone(user_id, phone);
	}

	public int deleteUser(int user_id){
		return userRepository.deleteUser(user_id);
	}
}

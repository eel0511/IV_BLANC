package com.ivblanc.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivblanc.api.dto.req.SignOutReqDTO;
import com.ivblanc.api.dto.req.UpdatePersonalReqDTO;
import com.ivblanc.api.dto.req.UpdatePwReqDTO;
import com.ivblanc.core.entity.User;
import com.ivblanc.core.exception.ApiMessageException;
import com.ivblanc.core.repository.UserRepository;
import com.ivblanc.core.utils.PasswordValidate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User findById(int userId){
		return userRepository.findByUserId(userId);
	}

	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}

	public User validateUser(String email, String pw, int userId){
		User user = findById(userId);
		if(findByEmail(email) == null){
			throw new ApiMessageException("등록되지 않은 이메일입니다.");
		}

		if(!user.getEmail().equals(email)){
			throw new ApiMessageException("잘못된 접근입니다.");
		}

		if(!passwordEncoder.matches(pw, user.getPassword())){
			throw new ApiMessageException("비밀번호가 일치하지 않습니다.");
		}

		return user;
	}

	@Transactional
	public void updatePw(UpdatePwReqDTO req, int userId){
		User user = validateUser(req.getEmail(), req.getPw(), userId);
		if(user == null){
			throw new ApiMessageException("잘못된 회원정보입니다.");
		}

		if(!PasswordValidate.checkPwForm(req.getPw_new()) ){
			throw new ApiMessageException("비밀번호는 영문,숫자,특수문자 중 2가지 이상을 포함하며 8자리 이상, 14자리 이하입니다");
		}

		if(!PasswordValidate.checkPwMatch(req.getPw_new(), req.getPw_check())){
			throw new ApiMessageException("비밀번호를 확인해주세요.");
		}

		user.updatePassword(passwordEncoder.encode(req.getPw_new()));
		userRepository.save(user);
	}

	@Transactional
	public void updatePersonal(UpdatePersonalReqDTO req, int userId){
		User user = validateUser(req.getEmail(), req.getPw(), userId);
		if(user == null){
			throw new ApiMessageException("잘못된 회원정보입니다.");
		}

		user.setAge(req.getAge());
		user.setGender(req.getGender());
		user.setPhone(req.getPhone());
		user.setName(req.getName());
		System.out.println("userId = " + user.getUserId());

		userRepository.save(user);
	}

	@Transactional
	public void deleteUser(SignOutReqDTO req, int userId) {
		User user = validateUser(req.getEmail(), req.getPw(), userId);
		if(user == null){
			throw new ApiMessageException("잘못된 회원정보입니다.");
		}

		userRepository.deleteById(user.getUserId());
	}
}

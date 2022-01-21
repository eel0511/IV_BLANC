package com.ivblanc.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivblanc.core.entity.User;
import com.ivblanc.core.exception.ApiMessageException;
import com.ivblanc.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {
    private final UserRepository userRepository;

    /**
     * id로 회원정보 조회
     * @param id
     * @return
     * @throws Exception
     */
    public User findUserById(int id) throws Exception{
        User user = userRepository.findById(id).orElseThrow( () -> new ApiMessageException("존재하지 않는 회원정보입니다.") );
        return user;
    }

    /**
     * email로 user 조회
     * @param email
     * @return
     * @throws Exception
     */
    public User findByEmail(String email) throws Exception{
        return userRepository.findByEmail(email);
    }


    /**
     * 회원가입 후 userId 리턴
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public long userSignUp(User user){
        User signUpUser = userRepository.save(user);
        return signUpUser.getUserId();
    }

    /**
     * email, type으로 회원정보 조회
     * @param email
     * @param social
     * @return
     */
    public User findUserByEmailType(String email, int social){
        return userRepository.findUserLogin(email, social);
    }

    /**
     * 회원의 디바이스 정보 업데이트
     * @param user
     */
    @Transactional(readOnly = false)
    public void saveUser(User user){
        userRepository.save(user);
    }


}



















































package com.ivblanc.api.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ivblanc.api.service.SignService;
import com.ivblanc.api.service.UserService;
import com.ivblanc.core.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final SignService signService;

    @Override
    public UserDetails loadUserByUsername(String userPk){
        User user = null;
        try {
            user =signService.findUserById(Long.valueOf(userPk));
        } catch (Exception e){
            e.printStackTrace();
        }

        return (UserDetails)user;
    }

}

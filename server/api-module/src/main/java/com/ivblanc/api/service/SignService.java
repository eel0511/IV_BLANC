package com.ivblanc.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.User;
import com.ivblanc.core.exception.ApiMessageException;
import com.ivblanc.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {
    private final UserRepository userRepository;

}



















































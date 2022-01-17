package com.ivblanc.core.repository;

import com.ivblanc.core.code.JoinCode;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoCommon {
    User findUserLogin(String uid, JoinCode type);

    User findByUid(String uid, YNCode isBind);

}







































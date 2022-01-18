package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivblanc.core.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend,Integer> ,FriendRepoCommon{
}

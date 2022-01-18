package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer>, FriendRepoCommon {
}

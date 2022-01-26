package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepoCommon{

}

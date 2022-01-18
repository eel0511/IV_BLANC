package com.ivblanc.core.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ivblanc.core.code.JoinCode;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.QUser;
import com.ivblanc.core.entity.User;

@Repository
@Transactional
public class UserRepoCommonImpl implements UserRepoCommon {
	private final JPAQueryFactory queryFactory;
	private EntityManager em;

	public UserRepoCommonImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
		this.em = em;
	}

}






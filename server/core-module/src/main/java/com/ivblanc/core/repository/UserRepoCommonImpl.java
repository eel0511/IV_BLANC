package com.ivblanc.core.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.QUser;
import com.ivblanc.core.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional
public class UserRepoCommonImpl implements UserRepoCommon{
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public UserRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }


    @Override
    public User findUserLogin(String email, int type){
        User result = queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.email.eq(email), QUser.user.social.eq(type))
                .fetchFirst();

        return result;
    }

    @Override
    public User findByEmail(String email){
        User result = queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.email.eq(email))
                .fetchOne();

        return result;
    }

    @Override
    public User findByUserId(int userId) {
        User result = queryFactory
            .select(QUser.user)
            .from(QUser.user)
            .where(QUser.user.userId.eq(userId))
            .fetchOne();

        return result;
    }

}






package com.ivblanc.core.repository;

import com.ivblanc.core.code.JoinCode;
import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.entity.User;
import com.ivblanc.core.repository.UserRepoCommon;
import com.ivblanc.core.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepoCommonImpl implements UserRepoCommon {
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public UserRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }


    @Override
    public User findUserLogin(String uid, JoinCode type){
        User result = queryFactory
            .select(QUser.user)
            .from(QUser.user)
            .where(QUser.user.uid.eq(uid), QUser.user.joinType.eq(type), QUser.user.isBind.eq(YNCode.Y))
            .fetchFirst();

        return result;
    }


    @Override
    public User findByUid(String uid, YNCode isBind){
        User result = queryFactory
            .select(QUser.user)
            .from(QUser.user)
            .where(QUser.user.uid.eq(uid), checkUserIsBind(isBind))
            .fetchOne();

        return result;
    }



    // isBind 조건만 체크
    public BooleanExpression checkUserIsBind(YNCode isBind){
        if(isBind == null)
            return null;

        return QUser.user.isBind.eq(isBind);
    }
}






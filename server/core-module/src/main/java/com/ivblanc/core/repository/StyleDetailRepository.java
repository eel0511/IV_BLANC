package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivblanc.core.entity.StyleDetail;

public interface StyleDetailRepository extends JpaRepository<StyleDetail,Integer>,StyleDetailRepoCommon {
}

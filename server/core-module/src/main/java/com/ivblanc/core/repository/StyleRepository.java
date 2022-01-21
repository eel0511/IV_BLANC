package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.BaseEntity;
import com.ivblanc.core.entity.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style,Integer>,StyleRepoCommon {
}

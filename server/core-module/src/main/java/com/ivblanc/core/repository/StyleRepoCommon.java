package com.ivblanc.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.Style;

@Repository
public interface StyleRepoCommon {
	@Query("select distinct s from Style s left join fetch  s.styleDetails")
	List<Style> findAllByUserId(int userId);
}

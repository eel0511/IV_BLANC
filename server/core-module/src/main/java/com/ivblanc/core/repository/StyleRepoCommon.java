package com.ivblanc.core.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.Style;

@Repository
public interface StyleRepoCommon {
	List<Style> findAllByUserId(int userId, Pageable pageable);
}

package com.ivblanc.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ivblanc.core.entity.StyleDetail;

public interface StyleDetailRepoCommon {

	@Query(value = "select sd_id,clothes_id,style_id from style_detail where clothes_id = :clothesId", nativeQuery = true)
	List<StyleDetail> findClothesId(int clothesId);
}

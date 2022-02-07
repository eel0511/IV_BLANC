package com.ivblanc.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ivblanc.core.entity.Photo;

public interface PhotoRepoCommon {

	@Query(value = "select p from Photo p where p.history = :historyId")
	List<Photo> findPhotoByHistoryId(int historyId);
}

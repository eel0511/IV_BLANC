package com.ivblanc.core.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.Clothes;

@Repository
public interface ClothesRepoCommon {

	List<Clothes> findAllByCategoryAndUserId(int category, int userId);

	List<Clothes> findAllByColorAndUserId(String color, int userId);

	List<Clothes> findAllByMaterialAndUserId(String material, int userId);

	List<Clothes> findALLByUserIdOrderByFavoriteDesc(int userId);

	List<Clothes> findAllBySeasonAndUserId(int season, int userId);

	List<Clothes> findAllByLikePointAndUserId(int likePoint, int userId);

	List<Clothes> findAllByDislikePointAndUserId(int dislikePoint, int userId);

	List<Clothes> findAllByUserIdOrderByDateDesc(int userId);
}

package com.ivblanc.core.repository;

import com.ivblanc.core.entity.Clothes;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClothesRepoCommon {

	List<Clothes> findAllByCategoryAndUserId(int category, int userId, Pageable pageable);

	List<Clothes> findAllByColorAndUserId(String color, int userId);

	List<Clothes> findAllByMaterialAndUserId(int material, int userId);

	List<Clothes> findALLByUserIdOrderByFavoriteDesc(int userId);

	List<Clothes> findAllBySeasonAndUserId(int season, int userId);

	List<Clothes> findAllByUserIdOrderByLikePointDesc(int userId);

	List<Clothes> findAllByUserIdOrderByDislikePointDesc(int userId);

	List<Clothes> findAllByUserIdOrderByCountDesc(int userId);

	List<Clothes> findAllByUserIdOrderByCreateDateDesc(int userId);

	List<Clothes> findAllByUserIdOrderByUpdateDateDesc(int userId);

	List<Clothes> findAllByUserId(int userId);

	List<Clothes> findAllByUpdateDateBetween(LocalDateTime start, LocalDateTime end);

}

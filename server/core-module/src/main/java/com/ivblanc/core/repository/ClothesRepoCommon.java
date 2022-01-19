package com.ivblanc.core.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.Clothes;

@Repository
public interface ClothesRepoCommon {

	List<Clothes> findAllByCategory(int category);

	List<Clothes> findAllByColor(String color);

	List<Clothes> findAllByMaterial(String material);

	List<Clothes> findALLByOrderByFavoriteDesc();


}

package com.ivblanc.api.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ivblanc.api.dto.req.MakeClothesReqDTO;
import com.ivblanc.core.entity.Clothes;
import com.ivblanc.core.repository.ClothesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClothesSerivce {

	private final ClothesRepository clothesRepository;


	public Optional<Clothes> findByClothesId(int clothes_id) {
		return clothesRepository.findById(clothes_id);
	}

	public List<Clothes> findOrderByCreateDate(int userId) {
		return clothesRepository.findAllByUserIdOrderByCreateDateDesc(userId);
	}

	public List<Clothes> findOrderByUpdateDate(int userId) {
		return clothesRepository.findAllByUserIdOrderByUpdateDateDesc(userId);
	}

	public void addClothes(Clothes clothes) {
		clothesRepository.save(clothes);
	}

	public void deleteClothesById(int clothesId) {
		clothesRepository.deleteById(clothesId);
	}

	public List<Clothes> findByCategory(final Pageable pageable, int category, int userId) {
		return clothesRepository.findAllByCategoryAndUserId(category, userId, pageable);
	}

	public List<Clothes> findByColor(String color, int userId) {
		return clothesRepository.findAllByColorAndUserId(color, userId);
	}

	public List<Clothes> findByMaterial(int material, int userId) {
		return clothesRepository.findAllByMaterialAndUserId(material, userId);
	}

	public List<Clothes> findBySeason(int season, int userId) {
		return clothesRepository.findAllBySeasonAndUserId(season, userId);
	}

	public List<Clothes> findOrderByLike(int userId) {
		return clothesRepository.findAllByUserIdOrderByLikePointDesc(userId);
	}

	public List<Clothes> findOrderByDisLike(int userId) {
		return clothesRepository.findAllByUserIdOrderByDislikePointDesc(userId);
	}

	public List<Clothes> findOrderByCount(int userId) {
		return clothesRepository.findAllByUserIdOrderByCountDesc(userId);
	}

	public List<Clothes> findAll(int userId) {
		return clothesRepository.findAllByUserId(userId);
	}

	public List<Clothes> findAllByDate(int days) {
		LocalDateTime start = LocalDateTime.MIN;
		LocalDateTime end = LocalDateTime.now().minusDays(days * 30L);
		return clothesRepository.findAllByUpdateDateBetween(start, end);
	}

	public void addFavorite(Clothes clothes) {
		clothesRepository.save(clothes);
	}

	public Clothes MakeClothesByReqDTO(MakeClothesReqDTO req,int userId) {
		return Clothes.builder()
			.category(req.getCategory())
			.color(req.getColor())
			.material(req.getMaterial())
			.size(req.getSize())
			.season(req.getSeason())
			.userId(userId)
			.build();
	}

	public Clothes MakeClotehsByReqDToAndUrl(MakeClothesReqDTO req, String url,int userId) {
		return Clothes.builder()
			.category(req.getCategory())
			.color(req.getColor())
			.material(req.getMaterial())
			.size(req.getSize())
			.season(req.getSeason())
			.userId(userId)
			.url(url)
			.build();
	}
	public Clothes updateFavorite(Clothes clothes,int favorite){
		clothes.setFavorite(favorite);
		return clothes;
	}
}

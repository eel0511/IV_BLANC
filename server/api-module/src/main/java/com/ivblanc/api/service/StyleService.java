package com.ivblanc.api.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ivblanc.core.entity.Style;
import com.ivblanc.core.repository.StyleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StyleService {
	private final StyleRepository styleRepository;

	public List<Style> findAll(int userId, Pageable pageable){
		return styleRepository.findAllByUserId(userId,pageable);
	}
	public Optional<Style> findByStyleId(int styleId){
		return styleRepository.findById(styleId);
	}

	public void addStyle(Style style){
		styleRepository.save(style);
	}
	public void deleteStyleById(int styleId){
		styleRepository.deleteById(styleId);
	}
}

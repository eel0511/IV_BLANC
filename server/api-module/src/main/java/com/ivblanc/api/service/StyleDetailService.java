package com.ivblanc.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ivblanc.core.entity.Style;
import com.ivblanc.core.entity.StyleDetail;
import com.ivblanc.core.repository.StyleDetailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StyleDetailService {
	private final StyleDetailRepository styleDetailRepository;
	public void addStyleDetails(List<StyleDetail> styleDetails){
		styleDetailRepository.saveAll(styleDetails);
	}
}

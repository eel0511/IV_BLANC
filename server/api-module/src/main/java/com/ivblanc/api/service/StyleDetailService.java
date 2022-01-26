package com.ivblanc.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ivblanc.api.dto.req.MakeStyleDetailReqDTO;
import com.ivblanc.core.entity.Clothes;
import com.ivblanc.core.entity.Style;
import com.ivblanc.core.entity.StyleDetail;
import com.ivblanc.core.exception.ApiMessageException;
import com.ivblanc.core.repository.StyleDetailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StyleDetailService {
	private final StyleDetailRepository styleDetailRepository;
	private final ClothesSerivce clothesSerivce;

	public void addStyleDetails(List<StyleDetail> styleDetails) {
		styleDetailRepository.saveAll(styleDetails);
	}
	public void addStyleDetail(StyleDetail styleDetail){
		styleDetailRepository.save(styleDetail);
	}
	public void deleteDetail(int sdId){
		styleDetailRepository.deleteById(sdId);
	}
	public Optional<StyleDetail> findBySdId(int sdId){
		return styleDetailRepository.findById(sdId);
	}
	public List<StyleDetail> findAllByclothesId(int clothesId){
		return styleDetailRepository.findClothesId(clothesId);
	}
	public StyleDetail makeStyleDetail(Clothes clothes){
		return StyleDetail.builder()
			.clothes(clothes)
			.build();
	}
	public Style makeStyleDetailsToReqDTO(List<MakeStyleDetailReqDTO> reqDTOList,Style style){
		List<StyleDetail> styleDetailList = new ArrayList<>();
		for (MakeStyleDetailReqDTO m : reqDTOList) {
			Clothes clothes = clothesSerivce.findByClothesId(m.getClothesId())
				.orElseThrow(() -> new ApiMessageException("없는 옷 번호입니다"));
			StyleDetail styleDetail = StyleDetail.builder()
				.clothes(clothes)
				.build();
			styleDetailList.add(styleDetail);
			style.add(styleDetail);
		}
		return style;
	}
	public StyleDetail updateClothes(StyleDetail styleDetail,Clothes clothes){
		styleDetail.setClothes(clothes);
		return styleDetail;
	}
}

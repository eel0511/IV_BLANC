package com.ivblanc.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ivblanc.api.dto.req.MakeStyleDetailReqDTO;
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

    public List<Style> findAllByUserId(int userId) {
        return styleRepository.findAllByUserId(userId);
    }

    public Optional<Style> findByStyleId(int styleId) {
        return styleRepository.findById(styleId);
    }

    public void addStyle(Style style) {
        styleRepository.save(style);
    }

    public void deleteStyleById(int styleId) {
        styleRepository.deleteById(styleId);
    }

    public Style makeStyle(String madeby, int userId, String url) {
        return Style.builder()
                .madeby(madeby)
                .userId(userId)
                .url(url)
                .build();
    }

    public Style updateFavorite(Style style, int favorite) {
        style.setFavorite(favorite);
        return style;
    }

    public List<MakeStyleDetailReqDTO> makeStyleDetailReqDTOList(String list){
        List<MakeStyleDetailReqDTO> styleDetails = new ArrayList<>();
        String[] temp = list.split(",");
        for(String s : temp){
            styleDetails.add(new MakeStyleDetailReqDTO(Integer.parseInt(s.trim())));
        }
        return styleDetails;
    }
}

package com.ivblanc.api.service;

import java.util.List;
import java.util.Optional;

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
    public Style updateUrl(Style style, String url) {
        style.setUrl(url);
        return style;
    }

    public Style updateFavorite(Style style, int favorite) {
        style.setFavorite(favorite);
        return style;
    }
}

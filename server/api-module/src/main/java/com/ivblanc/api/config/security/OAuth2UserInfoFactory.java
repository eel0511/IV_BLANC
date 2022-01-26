package com.ivblanc.api.config.security;

import java.util.Map;

import com.ivblanc.api.config.security.dto.GoogleOAuth2UserInfo;
import com.ivblanc.api.config.security.dto.KakaoOAuth2UserInfo;
import com.ivblanc.api.config.security.dto.NaverOAuth2UserInfo;
import com.ivblanc.api.config.security.dto.OAuth2UserInfo;
import com.ivblanc.core.code.ProviderType;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
		switch (providerType) {
			case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
			case NAVER: return new NaverOAuth2UserInfo(attributes);
			case KAKAO: return new KakaoOAuth2UserInfo(attributes);
			default: throw new IllegalArgumentException("Invalid Provider Type.");
		}
	}
}
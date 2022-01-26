package com.ivblanc.api.oauth.info.impl;

import java.util.Map;

import com.ivblanc.api.oauth.info.OAuth2UserInfo;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}
	@Override
	public String getId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getName() {
		Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

		if (properties == null) {
			return null;
		}

		return (String) properties.get("nickname");
	}

	@Override
	public String getEmail() {
		Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");

		if (properties == null) {
			return null;
		}
		return (String) properties.get("email");
	}

	@Override
	public String getPhone() {
		return (String) attributes.get("phone");
	}

	@Override
	public int getAge() {
		return (Integer) attributes.get("age");
	}

	@Override
	public int getGender() {
		return (Integer) attributes.get("gender");
	}
}
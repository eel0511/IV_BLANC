package com.ivblanc.api.oauth.info.impl;

import java.util.Map;

import com.ivblanc.api.oauth.info.OAuth2UserInfo;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

	public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String) attributes.get("sub");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
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
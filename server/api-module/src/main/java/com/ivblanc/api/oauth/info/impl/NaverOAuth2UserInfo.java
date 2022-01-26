package com.ivblanc.api.oauth.info.impl;

import java.util.Map;

import com.ivblanc.api.oauth.info.OAuth2UserInfo;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

	public NaverOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		if (response == null) {
			return null;
		}
		System.out.println(response);

		return (String) response.get("id");
	}

	@Override
	public String getName() {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		if (response == null) {
			return null;
		}

		return (String) response.get("name");
	}

	@Override
	public String getEmail() {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		if (response == null) {
			return null;
		}

		return (String) response.get("email");
	}

	@Override
	public String getPhone() {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		if (response == null) {
			return null;
		}

		return (String) response.get("phone");
	}

	@Override
	public int getAge() {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		if (response == null) {
			return 0;
		}

		return (int) response.get("age");
	}

	@Override
	public int getGender() {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		if (response == null) {
			return 0;
		}

		return (int) response.get("gender");
	}
}
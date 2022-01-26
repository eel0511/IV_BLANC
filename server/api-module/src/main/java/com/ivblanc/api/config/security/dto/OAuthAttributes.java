package com.ivblanc.api.config.security.dto;

import java.util.Collections;
import java.util.Map;

import com.ivblanc.core.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private int social;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, int social) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.social = social;
	}


	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String,Object> attributes) {
		return OAuthAttributes.builder()
			.name((String) attributes.get("name"))
			.email((String) attributes.get("email"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.name((String) attributes.get("name"))
			.email((String) attributes.get("email"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}


	public User toEntity() {
		return User.builder()
			.name(name)
			.email(email)
			.social(2)
			.password("")
			.roles(Collections.singletonList("ROLE_USER"))
			.build();
	}
}
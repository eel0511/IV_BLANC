package com.ivblanc.api.config.security.dto;

import lombok.Getter;

import java.io.Serializable;

import com.ivblanc.core.entity.User;

@Getter
public class SessionUser implements Serializable {

	private String name;
	private String email;

	public SessionUser(User user){
		this.name = user.getName();
		this.email = user.getEmail();
	}
}
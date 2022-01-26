package com.ivblanc.api.oauth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

public class OAuthProviderMissMatchException extends RuntimeException{

	public OAuthProviderMissMatchException(String message) {
		super(message);
	}
}

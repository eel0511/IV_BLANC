package com.ivblanc.api.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonParseException;
import com.ivblanc.api.dto.req.FcmReqDTO;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@Service
@RequiredArgsConstructor
public class FcmService {
	@Value("${app.firebase-config}")
	private String firebaseConfig;
	private final String API_URL = "https://fcm.googleapis.com/v1/projects/iv-blanc/messages:send";
	private final ObjectMapper objectMapper;

	public void sendMessageTo(String targetToken, String title, String body) throws IOException {
		String message = makeMessage(targetToken, title, body);

		OkHttpClient client = new OkHttpClient();
		RequestBody requestBody = RequestBody.create(message,
			MediaType.get("application/json; charset=utf-8"));
		Request request = new Request.Builder()
			.url(API_URL)
			.post(requestBody)
			.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
			.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
			.build();

		Response response = client.newCall(request).execute();

		System.out.println(response.body().string());
	}

	private String makeMessage(String targetToken, String title, String body) throws
		JsonParseException, JsonProcessingException {
		FcmReqDTO fcmMessage = FcmReqDTO.builder()
			.message(FcmReqDTO.Message.builder()
				.token(targetToken)
				.notification(FcmReqDTO.Notification.builder()
					.title(title)
					.body(body)
					.image(null)
					.build()
				).build()).validateOnly(false).build();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String getAccessToken() throws IOException {
		String firebaseConfigPath = firebaseConfig;

		GoogleCredentials googleCredentials = GoogleCredentials
			.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
			.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}
}

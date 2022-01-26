package com.ivblanc.api.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.dto.req.FcmPushReqDTO;
import com.ivblanc.api.dto.req.FcmReqDTO;
import com.ivblanc.api.service.FcmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FcmController {
	private final FcmService fcmService;

	@PostMapping("/api/fcm")
	public ResponseEntity pushMessage(FcmPushReqDTO requestDTO) throws IOException {
		System.out.println(requestDTO.getTargetToken() + " "
			+requestDTO.getTitle() + " " + requestDTO.getBody());

		fcmService.sendMessageTo(
			requestDTO.getTargetToken(),
			requestDTO.getTitle(),
			requestDTO.getBody());
		return ResponseEntity.ok().build();
	}
}

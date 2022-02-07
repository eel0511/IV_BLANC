package com.ivblanc.api.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivblanc.api.dto.req.FcmPushReqDTO;
import com.ivblanc.api.dto.req.FcmReqDTO;
import com.ivblanc.api.service.FcmService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"FCM"})
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(value = "/api/fcm")
public class FcmController {
	private final FcmService fcmService;

	@ApiOperation(value = "FCM 메시지 보내기", notes = "FCM 토큰값 넣어서 메시지 보내기입니다.")
	@PostMapping("/send")
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

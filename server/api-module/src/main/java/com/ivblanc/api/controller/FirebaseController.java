package com.ivblanc.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ivblanc.api.config.security.JwtTokenProvider;
import com.ivblanc.api.service.FileService;
import com.ivblanc.api.service.common.ResponseService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"FIREBASE"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/firebase")
public class FirebaseController {
	@Autowired
	final FileService fileService;

	@PostMapping("/profile/pic")
	public Object upload(@RequestParam("file") MultipartFile multipartFile) {

		return fileService.upload(multipartFile);
	}

	@PostMapping("/profile/pic/{fileName}")
	public Object download(@PathVariable String fileName) throws IOException, IOException {

		return fileService.download(fileName);
	}
}

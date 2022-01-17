package com.ivblanc.core.utils;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {

	String upload(String base64File, String dirName);

	String upload(MultipartFile file, String dirName) throws IOException, Exception;
}

package com.ivblanc.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.ivblanc.api.service.common.ResponseService;
import com.ivblanc.core.exception.ApiMessageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
	// application yml 설정파일에 설정한 값 사용
	@Value("${app.firebase-config}")
	private String firebaseConfig;
	final ResponseService responseService;

	String DOWNLOAD_URL = "https://storage.googleapis.com/iv-blanc.appspot.com";

	private String uploadFile(File file, String fileName) throws IOException {
		BlobId blobId = BlobId.of("iv-blanc.appspot.com", fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream());
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		storage.create(blobInfo, Files.readAllBytes(file.toPath()));
		return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, "UTF-8"));
	}

	private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		}
		return tempFile;
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public String upload(MultipartFile multipartFile) {

		try {
			String fileName = multipartFile.getOriginalFilename();                        // to get original file name
			//저장되는 파일이름 랜덤
			fileName = UUID.randomUUID()
				.toString()
				.concat(this.getExtension(fileName));  // to generated random string values for file name.

			File file = this.convertToFile(multipartFile,
				fileName);                      // to convert multipartFile to File
			String TEMP_URL = this.uploadFile(file,
				fileName);                                   // to get uploaded file link
			file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
			return TEMP_URL + "/" + fileName;                     // Your customized response
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}
}

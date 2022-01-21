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
	public Object upload(MultipartFile multipartFile) {

		try {
			String fileName = multipartFile.getOriginalFilename();                        // to get original file name
			fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

			File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
			String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
			file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
			return responseService.getSingleResult(TEMP_URL+"/"+fileName);                     // Your customized response
		} catch (Exception e) {
			e.printStackTrace();
			return responseService.getFailResult(500, "Unsuccessfully Uploaded!");
		}

	}

	public Object download(String fileName) throws IOException {
		String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
		// to set random strinh for destination file name
		// 여기에 다운받을 경로설정해야하는데 이 값도 param으로 줘서 받을수있죠??
		String destFilePath = "C:\\Users\\eel05\\Downloads\\" + destFileName;                                    // to set destination file path

		////////////////////////////////   Download  ////////////////////////////////////////////////////////////////////////
		Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream());
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		Blob blob = storage.get(BlobId.of("iv-blanc.appspot.com", fileName));
		blob.downloadTo(Paths.get(destFilePath));
		return responseService.getSingleResult( "Successfully Downloaded!");
	}
}

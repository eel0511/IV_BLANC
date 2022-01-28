package com.ivblanc.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ivblanc.api.dto.req.MakePhotoReqDTO;
import com.ivblanc.core.entity.History;
import com.ivblanc.core.entity.Photo;
import com.ivblanc.core.exception.ApiMessageException;
import com.ivblanc.core.repository.PhotoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {
	private final PhotoRepository photoRepository;
	private final FileService fileService;

	public void addPhotos(List<Photo> photos) {
		photoRepository.saveAll(photos);
	}

	public void addPhoto(Photo photo){
		photoRepository.save(photo);
	}

	public void deletePhoto(int photoId){
		photoRepository.deleteById(photoId);
	}

	public Optional<Photo> findByPhotoId(int photoId){
		return photoRepository.findById(photoId);
	}

	public List<Photo> findAllByHistoryId(int historyId){
		return photoRepository.findPhotoByHistoryId(historyId);
	}

	public History MakePhotoByUrl(List<MakePhotoReqDTO> reqDTOList, History history) {
		for (MakePhotoReqDTO m : reqDTOList) {

			String url = fileService.upload(m.getFile());
			if (url.equals("error")) {
				throw new ApiMessageException("파일 올리기 실패");
			}

			Photo photo = Photo.builder()
				.url(url)
				.build();
			history.add(photo);
		}
		return history;
	}
}

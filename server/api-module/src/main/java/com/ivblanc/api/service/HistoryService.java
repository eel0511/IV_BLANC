package com.ivblanc.api.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ivblanc.api.dto.req.MakeHistoryReqDTO;
import com.ivblanc.api.dto.req.UpdateHistoryReqDTO;
import com.ivblanc.core.entity.History;
import com.ivblanc.core.entity.Photo;
import com.ivblanc.core.entity.Style;
import com.ivblanc.core.exception.ApiMessageException;
import com.ivblanc.core.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

	private final HistoryRepository historyRepository;
	private final StyleService styleService;
	private final PhotoService photoService;

	public Optional<History> findByHistoryId(int history_id) {
		return historyRepository.findById(history_id);
	}

	public List<History> findAll(int userId) {
		return historyRepository.findAllByUserId(userId);
	}

	public List<History> findOrderByDateDesc(int userId) {
		return historyRepository.findAllByUserIdOrderByDateDesc(userId);
	}

	public List<History> findOrderByDateAsc(int userId) {
		return historyRepository.findAllByUserIdOrderByDateAsc(userId);
	}

	public History findByDate(String dateStr, int userId) {
		String[] dateArr = dateStr.split("-");
		LocalDateTime date = LocalDateTime.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]), 0, 0, 0);
		return historyRepository.findByDateAndUserId(date, userId);
	}

	public List<History> findAllByDateBetween(LocalDateTime date_start, LocalDateTime date_end, int userId) {
		return historyRepository.findAllByDateBetweenAndUserId(date_start, date_end, userId);
	}

	public List<History> findByWeather(String weather, int userId) {
		return historyRepository.findAllByWeatherAndUserId(weather, userId);
	}

	public List<History> findByTemperature(int temp_low, int temp_high, int userId) {
		return historyRepository.findAllByTemperatureAndUserId(temp_low, temp_high, userId);
	}

	public List<History> findBySubject(String subject, int userId) {
		return historyRepository.findAllBySubjectAndUserId(subject, userId);
	}

	public List<History> findByYearAndMonth(int year, int month, int userId) {
		LocalDateTime date_start = LocalDateTime.of(year, month, 1, 0, 0, 0);
		YearMonth ym = YearMonth.from(date_start);
		LocalDateTime date_end = LocalDateTime.of(year, month, ym.lengthOfMonth(), 0, 0, 0);

		return historyRepository.findAllByDateBetweenAndUserId(date_start, date_end, userId);
	}

	public List<History> findByYearAndMonth(LocalDateTime date, int userId) {

		LocalDateTime date_start = LocalDateTime.of(date.getYear(), date.getMonth(), 1, 0, 0, 0);
		YearMonth ym = YearMonth.from(date_start);
		LocalDateTime date_end = LocalDateTime.of(date.getYear(), date.getMonth(), ym.lengthOfMonth(), 0, 0, 0);

		return historyRepository.findAllByDateBetweenAndUserId(date_start, date_end, userId);
	}

	public History makeHistory(MakeHistoryReqDTO req, int userId){
		String[] date = req.getDate().split("-");
		LocalDateTime dt = LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), 0, 0, 0);

		String url = null;
		Optional<Style> style = styleService.findByStyleId(req.getStyleId());
		if(!style.isPresent()){
			throw new ApiMessageException("존재하지 않는 style id입니다.");
		} else {
			url = style.get().getUrl();
		}

		History history = History.builder()
			.location(req.getLatitude())
			.field(req.getLongitude())
			.date(dt)
			.weather(req.getWeather())
			.temperature_low(req.getTemperature_low())
			.temperature_high(req.getTemperature_high())
			.text(req.getText())
			.subject(req.getSubject())
			.styleUrl(url)
			.userId(userId)
		.build();

		return history;
	}

	public void addHistory(History history) {
		historyRepository.save(history);
	}

	public void deleteHistoryById(int historyId) throws IOException {
		Optional<History> history = findByHistoryId(historyId);
		for(Photo p : history.get().getPhotos()){
			photoService.deletePhoto(p.getPhotoId());
		}

		historyRepository.deleteById(historyId);
	}

	public void updateHistory(History history, UpdateHistoryReqDTO req){
		String[] date = req.getDate().split("-");
		LocalDateTime dt = LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), 0, 0, 0);

		String url = null;
		Optional<Style> style = styleService.findByStyleId(req.getStyleId());
		if(style == null){
			throw new ApiMessageException("존재하지 않는 style id입니다.");
		} else {
			url = style.get().getUrl();
		}

		history.setLocation(req.getLatitude());
		history.setField(req.getLongitude());
		history.setDate(dt);
		history.setWeather(req.getWeather());
		history.setTemperature_low(req.getTemperature_low());
		history.setTemperature_high(req.getTemperature_high());
		history.setText(req.getText());
		history.setSubject(req.getSubject());
		history.setStyleUrl(url);

		historyRepository.save(history);
	}

}

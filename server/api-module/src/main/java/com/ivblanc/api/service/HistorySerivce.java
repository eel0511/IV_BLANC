package com.ivblanc.api.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.jni.Local;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ivblanc.core.entity.History;
import com.ivblanc.core.entity.Style;
import com.ivblanc.core.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistorySerivce {

	private final HistoryRepository historyRepository;

	public Optional<History> findByHistoryId(int history_id) {
		return historyRepository.findById(history_id);
	}

	public List<History> findAll(int userId, Pageable pageable) {
		return historyRepository.findAllByUserId(userId, pageable);
	}

	public List<History> findOrderByDateDesc(int userId, Pageable pageable) {
		return historyRepository.findAllByUserIdOrderByDateDesc(userId, pageable);
	}

	public List<History> findOrderByDateAsc(int userId, Pageable pageable) {
		return historyRepository.findAllByUserIdOrderByDateAsc(userId, pageable);
	}

	public History findByDate(LocalDateTime date, int userId) {
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

	public void addHistory(History history) {
		historyRepository.save(history);
	}

	public void deleteHistoryById(int historyId) {
		historyRepository.deleteById(historyId);
	}

}

package com.ivblanc.core.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.History;

@Repository
public interface HistoryRepoCommon {

	List<History> findAllByUserId(int userId, Pageable pageable);

	List<History> findAllByUserIdOrderByCreateDateDesc(int userId, Pageable pageable);

	List<History> findAllByUserIdOrderByCreateDateAsc(int userId, Pageable pageable);

	History findByDateAndUserId(LocalDateTime date, int userId);

	List<History> findAllByCreateDateBetweenAndUserId(LocalDateTime start, LocalDateTime end, int userId);

	List<History> findAllByWeatherAndUserId(String weather, int userId);

	@Query(value = "select h from History h where h.userId = :id and h.temperature_low <= :templow and h.temperature_high >= :temphigh")
	List<History> findAllByTemperatureAndUserId(@Param("templow") int temperature_low, @Param("temphigh") int temperature_high, @Param("id") int userId);

	List<History> findAllBySubjectAndUserId(String subject, int userId);

}

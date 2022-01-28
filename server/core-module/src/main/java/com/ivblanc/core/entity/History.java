package com.ivblanc.core.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History extends BaseEntity{

	@Id
	@Column(name = "history_id",columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int historyId;

	@Column(precision = 10, scale = 7)
	private BigDecimal location;

	@Column(precision = 11, scale = 7)
	private BigDecimal field;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime date;

	@Column(length = 10)
	private String weather;

	@Column
	private int temperature_low;

	@Column
	private int temperature_high;

	@Column(length = 2000)
	private String text;

	@Column(length = 255)
	private String subject;

	@Column(length = 2000)
	private String styleUrl;

	@Column(name = "user_id")
	private int userId;

	@Builder.Default
	@JsonManagedReference
	@BatchSize(size = 10)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "history", cascade = CascadeType.ALL)
	private List<Photo> photos = new ArrayList<>();

	public void add(Photo photo) {
		photo.setHistory(this);
		this.photos.add(photo);
	}
}

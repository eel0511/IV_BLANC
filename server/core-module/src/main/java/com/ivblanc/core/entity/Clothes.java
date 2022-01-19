package com.ivblanc.core.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Clothes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clothes_id;

	@Column
	private int category;

	@Column(length = 8)
	private String color;

	@Column(length = 10)
	private String material;

	@Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
	private Timestamp date;

	@Column(length = 2000)
	private String url;

	@Column(nullable = false, columnDefinition = "int default 0")
	private int favorite;

	@Column
	private int size;

	@Column
	private int season;

	@Column(nullable = false, columnDefinition = "int default 0")
	private int count;

	@Column(nullable = false, columnDefinition = "int default 0")
	private int likePoint;

	@Column(nullable = false, columnDefinition = "int default 0")
	private int dislikePoint;

	@Column(name = "user_id")
	private int userId;
}

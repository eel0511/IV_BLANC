package com.ivblanc.core.entity;

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
public class Style {
	@Id
	@Column(name = "style_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int style_id;

	@Column(length = 20)
	private String madeby;

	@Column
	private int favorite;

	@Column(length = 2000)
	private String url;

	@Column(name = "photo_name",length = 2000)
	private String photoName;

	@Column(name = "user_id")
	private int userId;
}

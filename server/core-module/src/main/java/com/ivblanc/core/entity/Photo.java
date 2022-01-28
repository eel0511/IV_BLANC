package com.ivblanc.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class Photo extends BaseEntity {
	@Id
	@Column(name = "photo_id",columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int photoId;

	@Column(length = 2000, columnDefinition = "varchar(2000) default 'None'")
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "history_id")
	private History history;

}

package com.ivblanc.core.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ivblanc.core.code.YNCode;
import com.ivblanc.core.converter.YNCodeConverter;

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
public class Friend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int friend_id;

	@Column(length = 255)
	private String applicant;

	@Column(name = "friend_name", length = 255)
	private String friendName;

	@Convert(converter = YNCodeConverter.class)
	@Column(nullable = false, length = 1, columnDefinition = "varchar(1) default 'N'")
	private YNCode isaccept;

}

package com.ivblanc.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
public class StyleDetail {

	@Id
	@Column(name = "sd_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sdId;

	@Column(name="clothes_id")
	private int clothesId;

	@ManyToOne
	@JoinColumn(name = "style_id")
	private Style style;

}

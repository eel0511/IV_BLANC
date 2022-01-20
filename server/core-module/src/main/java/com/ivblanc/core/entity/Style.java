package com.ivblanc.core.entity;

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
public class Style extends BaseEntity {
	@Id
	@Column(name = "style_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int styleId;

	@Column(length = 20)
	private String madeby;

	@Column(nullable = false, columnDefinition = "int default 0")
	private int favorite;

	@Column(length = 2000)
	private String url;

	@Column(name = "photo_name", length = 2000)
	private String photoName;

	@Column(name = "user_id")
	private int userId;

	@Builder.Default
	@JsonManagedReference
	@BatchSize(size = 10)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "style", cascade = CascadeType.ALL)
	private List<StyleDetail> styleDetails = new ArrayList<>();

	public void add(StyleDetail styleDetail) {
		styleDetail.setStyle(this);
		this.styleDetails.add(styleDetail);
	}
}

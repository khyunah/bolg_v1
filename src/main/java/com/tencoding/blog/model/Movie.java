package com.tencoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {

	@Id
	@Column(length = 30)
	private String titile;
	@Column(columnDefinition = "TEXT")
	private String image;
	@Column(columnDefinition = "DATE")
	private String releaseDate;
	private float starScore;
}

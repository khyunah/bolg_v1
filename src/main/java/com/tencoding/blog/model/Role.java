package com.tencoding.blog.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Role {

	@EmbeddedId
	private RoleId roleId;
	@MapsId("title") // RoleId.title
	@ManyToOne
	@JoinColumn(name = "title")
	private Movie movie;
	@Column(length = 2)
	private String category;
	@Column(length = 20)
	private String roleName;
}

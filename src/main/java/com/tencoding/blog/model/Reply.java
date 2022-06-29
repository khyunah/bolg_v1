package com.tencoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 200)
	private String content;

	@ManyToOne // 여러개의 댓글은 하나의 게시글에 존재할 수 있다.
	@JoinColumn(name = "boardId")
	@JsonIgnoreProperties({"replys", "userId"})
	private Board board;

	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnoreProperties({"password", "role", "email", "oauth"})
	private User user;

	@CreationTimestamp
	private Timestamp createDate;
}

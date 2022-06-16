package com.tencoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 마지막에 쓰기
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob // 대용량 데이터를 뜻
	private String content;

	@ColumnDefault("0") // int형이지만 "" 써주기 , String은 " '안녕' "
	private int count;

	// 여러개의 게시글은 하나의 유저를 가진다.
	// 연관관계 이테이블 to 상대테이블
	@ManyToOne(fetch = FetchType.EAGER) // EAGER : 이 테이블을 셀렉트할때 한번에 데이터를 가지고 오는 것
	@JoinColumn(name = "userId")
	private User userId;

	@CreationTimestamp
	private Timestamp createDate;
}

package com.tencoding.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	// 댓글 정보 
	// 하나의 게시글에 여러개의 댓글이 있을 수 있다.
	// mappedBy="board" : 여기서 board는 reply테이블에 필드 이름이다.
	// mappedBy는 연관관계의 주인이 아니다. ( FK가 아니다. )
	// DB에 컬럼을 만들지 마시오.
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
	private List<Reply> replys;

	@CreationTimestamp
	private Timestamp createDate;
}

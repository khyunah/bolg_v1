package com.tencoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 클래스의 멤버변수로 DB에 자동으로 테이블 생성해주는 어노테이션
//@DynamicInsert // @ColumnDefault("'user'") 이렇게만 해놓으면 디폴트일때도 값이 null로 들어간다. @DynamicInsert를 해야 적용됨.
// 인설트할때 제외시켜주는 녀석
public class User {

	@Id // PK가 설정되는 기능.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략(오토인크리먼트)을 따라가겠다는 설정.
	private int id;

	@Column(nullable = false, length = 30) // null 안됨, 길이 30못넘음
	private String username;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(nullable = false, length = 50)
	private String email;

	@CreationTimestamp // 시간이 자동으로 입력된다.
	private Timestamp createDate;

	// domain : 데이터의 범주화 ( 같은 단어라도 잘못들어갈수가 있는데 딱 final 그값을 넣어야할때 )
//	@ColumnDefault("'user'")
	@Enumerated(EnumType.STRING)
	private RollType role; // Enum 타입 사용권장 : admin, user, manager
}

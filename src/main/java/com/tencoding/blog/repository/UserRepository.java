package com.tencoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.User;

// Bean으로 등록 될 수 있나요? ㅡ> 스프링에서 IoC 에서 객체를 가지고 있나요? ( 둘다 같은말임. )
public interface UserRepository extends JpaRepository<User, Integer> { // 테이블, PK

	// spring JPA 네이밍 전략
	// SELECT * FROM user WHERE username = ?1 AND password = ?2;
	
	// 컬럼명 대문자로 시작 엄격, 매개변수 컬럼명이랑 같이
	// 자동으로 쿼리문이 만들어진다.
//	User findByUsernameAndPassword(String username, String password);
	
	// 이런 방식도 있음 
//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2;")
//	User login();
}

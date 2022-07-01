package com.tencoding.blog.test;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {

	//@Test
	public void hashEncode() {
		String encPassword = new BCryptPasswordEncoder().encode("123");
		System.out.println("해시 값 : " + encPassword);
		// 스프링부트는 매번 달라짐 
		// $2a$10$5IwZVJMGn7JsksxPpuqT9uzEDB9QxKNIIbeW5Dq6sbnS2RSTLhuA2
		// $2a$10$nsDC.IHb34qmJ7tfok0fPOjHQWtutuWLSYNXmTpMLp4s5NTe3b7D6
	}
}

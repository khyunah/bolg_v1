package com.tencoding.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 스프링이 com.tencoding.blog 패키지 이하를 컴포넌트스캔해서 모든 자바 파일을 
 * 메모리에 new 하는것은 아니고, 특정 어노테이션이 붙어있는 파일들을 new해서 (IoC) 스프링 컨테이너에서 관리해준다.
 */
@RestController
@RequestMapping("/test")
public class BlogControllerTest {

	@GetMapping("/hello")
	public String hello() {
		return "<h1>hello spring boot<h1>";
	}
}

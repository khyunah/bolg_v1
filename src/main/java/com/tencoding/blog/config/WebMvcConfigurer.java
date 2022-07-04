package com.tencoding.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// new 를 이용해서 메모리 초기화 해주는 역할 
public class WebMvcConfigurer {

	private final ObjectMapper objectMapper;
}

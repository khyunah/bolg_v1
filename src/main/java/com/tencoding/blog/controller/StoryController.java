package com.tencoding.blog.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/story")
public class StoryController {
	
	// 스토리 화면 
	@GetMapping("/home")
	public String storyHome() {
		return "story/home";
	}
	
	// 스토리 업로드 화면 
	@GetMapping("/upload")
	public String storyUpload() {
		return "story/upload";
	}
	
	// 업로드 
	@PostMapping("/image/upload")
	@ResponseBody
	public String storyImageUpload(MultipartFile file, String storyText) {	 // name값과 매개변수명이 같아야한다.
		System.out.println(file.getContentType()); // 이미지 타입
		System.out.println(file.getName()); // form 태그의 name
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize()); // byte 사이즈
		try {
			System.out.println(file.getBytes().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// MultipartFile file 
		// file의 사이즈 byte 단위로 나옴 
		
		// 1byte 
		// -> 1000byte( KB ) 
		// -> 1,000,000byte( MB ) 
		// -> 1,000,000,000byte ( GB )
		// -> 1,000,000,000,000byte ( TB )
		
		// 1bit = 2진수 ( 0과 1을 표현하는 단위 )
		// 1byte = 8bit 
		// 1KB = 2의 10승 byte ( 1024 바이트 )
		// 1byte -> 1024byte ( 1 KB ) 
		
		// 결론 1KB = 1024byte 
		// 왜 컴퓨터는 1byte의 1000배가 아니라 1024배 일까?
		// 컴퓨터가 1000배 보다는 1024배를 훨씬 빨리 계산하기 때문이다.
		// 좀더 빠른 속도를 얻기 위해서 1024개로 약속한 것이다. 
		
		// 컴퓨터는 2진수로 계산하는 것이 가장 편하고 빠르기 때문이다. 
		// 숫자를 2진수 단위로 관리한다. 
		// 그래서 컴퓨터는 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 와 같이 2의 제곱으로 된 단위를 사용한다. 
		
		return "upload test";
	}
}

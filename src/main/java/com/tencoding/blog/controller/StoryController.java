package com.tencoding.blog.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.blog.auth.PrincipalDetail;
import com.tencoding.blog.dto.RequestFileDto;
import com.tencoding.blog.model.Image;
import com.tencoding.blog.service.StoryService;

@Controller
@RequestMapping("/story")
public class StoryController {
	
	@Autowired
	private StoryService storyService;
	
	// 스토리 화면 
	@GetMapping("/home")
	public String storyHome(Model model, @PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<Image> imagePage = storyService.getImageList(pageable);
		System.out.println(imagePage.getContent().toString());
		model.addAttribute("imagePage", imagePage);
		return "story/home";
	}
	
	// 스토리 업로드 화면 
	@GetMapping("/upload")
	public String storyUpload() {
		return "story/upload";
	}
	
	// 업로드 
	@PostMapping("/image/upload")
//	public String storyImageUpload(MultipartFile file, String storyText) { // name값과 매개변수명이 같아야한다.
	public String storyImageUpload(RequestFileDto fileDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {	 
		
		storyService.imageUpload(fileDto, principalDetail.getUser());
		
		// MultipartFile file 
		System.out.println(fileDto.getFile().getContentType()); // 이미지 타입
		System.out.println(fileDto.getFile().getName()); // form 태그의 name
		System.out.println(fileDto.getFile().getOriginalFilename());
		System.out.println(fileDto.getFile().getSize()); // byte 사이즈
		try {
			System.out.println(fileDto.getFile().getBytes().toString());
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
		
		return "redirect:/story/home";
	}
}

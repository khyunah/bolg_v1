package com.tencoding.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import com.tencoding.blog.model.Image;
import com.tencoding.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestFileDto {
//	private MultipartFile[] file; // 여러개 받을때 배열로 
	
	private MultipartFile file; // name값이랑 같아야 함 
	private String uuid;	// 같은 파일이름이 존재할때 구별하기 위한 
							// 고유한 파일 이름을 만들기 위한 속성
	private String storyText;
	
	public Image toEntity(String storyImageUrl, User user) {
		return Image.builder()
				.storyText(storyText)
				.storyImageUrl(storyImageUrl)
				.originFileName(file.getOriginalFilename())
				.user(user)
				.build();
	}
}

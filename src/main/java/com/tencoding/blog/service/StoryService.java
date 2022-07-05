package com.tencoding.blog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.dto.RequestFileDto;
import com.tencoding.blog.model.Image;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.StoryRepository;

@Service
public class StoryService {
	
	@Value("${file.path}")
	private String uploadFolder;

	@Autowired
	private StoryRepository storyRepository;
	
	// 스토리 파일 전부 조회
	@Transactional(readOnly = true)
	public Page<Image> getImageList(Pageable pageable){
		return storyRepository.findAll(pageable);
	}
	
	// 파일 업로드 기능 ( 해당 서버에 바이너리를 받아서 파일을 생성하고 성공하면 DB 저장 )
	@Transactional
	public void imageUpload(RequestFileDto fileDto, User user) {
		
		// 이미지 파일 만들기 
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + "story";	// 한글이 깨지기 때문에 원본 파일이름 대신이 지정해줌
		String newFileName = (imageFileName.trim().replaceAll("\\s", "")); // 정규식에서 \\s 는 공백 을 의미
		// 6a9c14c1-0e79-4615-b4a4-0a009a073f6a_dog
		System.out.println("파일명 : " + newFileName);
		
		// 서버 컴퓨터의 경로를 가지고 와야 한다.
		Path imageFilePath = Paths.get(uploadFolder + newFileName);
		System.out.println("전체 파일 경로 + 파일명 : " + imageFilePath);
		
		// 파일 출력
		try {
			Files.write(imageFilePath, fileDto.getFile().getBytes());
			
			// DB 저장 
			Image imageEntity = fileDto.toEntity(newFileName, user);
			storyRepository.save(imageEntity);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

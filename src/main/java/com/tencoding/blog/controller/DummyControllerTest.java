package com.tencoding.blog.controller;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.RollType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	// UserRepository 메모리에 올라가있는 상태
	// 어떻게 가져오나요? ㅡ> DI 의존성 주입하면 됨
	@Autowired 	// 자동으로 메모리에 올라가게 해서 연결해준다.
	private UserRepository userRepository;
	
	// REST API
	// POST 
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("---------------------");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println("---------------------");
		System.out.println(user.getId());
		System.out.println(user.getCreateDate());
		System.out.println(user.getRole());
		
		// 문제는 디폴트값이 안먹힘 
//		user.setRole("user1");
		// 잘못된 값이 들어갈 수도 있음
		// enum 타입 사용하기를 권장
		user.setRole(RollType.USER);
		// 이렇게만 하면 DB에 index로 들어감
		// @Enumerated(EnumType.STRING) 이거 해주기
		
		userRepository.save(user);	// 추가
//		userRepository.deleteById(2);	// 삭제
		return "회원가입 완료되었습니다.";
	}
}

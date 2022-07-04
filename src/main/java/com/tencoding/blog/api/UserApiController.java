package com.tencoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired // DI
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager; // ㅡ> 미리 메모리에 올라가있는 녀석이 아니라서오류가 난다.
	// 필터단에서 먼저 메모리에 올려주기 위해서 securityConfig에 @Bean으로 등록을 해놔야한다.

	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		
		// 카카오가 수정이 들어오면 무시 
		// 기존 수정 들어오면 처리 
		
		userService.updateUser(user);

		// 회원정보 수정후 principal 바꿔주기 작업

		// 강제로 Authentication 객체를 만들고 securityContext안에 집어넣으면 된다.
		// 1. Authentication 객체를 생성 해야한다.
		// 2. AuthenticationManager를 메모리에 올려서 authenticate 메서드를 사용해서 Authentication 객체를
		// 저장한다.
		// 3. 세션안에 SecurityContextHolder 안에 getContext()안에 setAuthentication() 에다가
		// Authentication를 넣어주면 된다.

		// 1, 2.
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		// 3.
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}

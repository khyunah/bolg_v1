package com.tencoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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

	// 기본 데이터 파싱 전략
	// 'application/x-www-form-urlencoded;charset=UTF-8'  키밸류값구조이기때문에 @RequestBody 사용하면 안됨 @RequestBody는 json형식이라 형식이 맞지않음
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(User user) {
		System.out.println("------------------");
		System.out.println(user.getUsername());
		System.out.println("------------------");
		int result = userService.saveUser(user);
		return new ResponseDto<>(HttpStatus.OK.value(), result);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.updateUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}

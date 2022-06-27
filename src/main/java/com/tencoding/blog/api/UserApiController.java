package com.tencoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired // DI
	private UserService userService;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(User user) {
		int result = userService.saveUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user){
//		System.out.println("login 호출됨");
//		// 로그인 확인 처리
//		// principal 접근 주체 
//		User principal = userService.login(user);
//		if(principal != null) {
//			// 접근 주체가 정상적으로 username, password 확인 되었음. (세션이라는 거대한 메모리에 저장)
//			httpSession.setAttribute("principal", principal);
//			System.out.println("세션 정보가 저장되었습니다.");
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
}

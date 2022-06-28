package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@Controller
public class UserController {
	
//	@Autowired
//	private HttpSession httpSession;
	@Autowired
	private UserService userService;

	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "user/login_form";
	}

	@GetMapping("/auth/join_form")
	public String join() {
		return "user/join_form";
	}
	
	// 기본 데이터 파싱 전략
	// 'application/x-www-form-urlencoded;charset=UTF-8' 키밸류값구조이기때문에 @RequestBody
	// 사용하면 안됨 @RequestBody는 json형식이라 형식이 맞지않음
	@PostMapping("/auth/joinProc")
	public String save(User user) {
		userService.saveUser(user);
		return "redirect:/";
	}

//	@GetMapping("/logout")
//	public String logout() {
//		// 세션 정보 제거 
//		httpSession.invalidate();
//		return "redirect:/";
//	}
	
	@GetMapping("/user/update_form")
	public String updateForm() {
		return "user/update_form";
	}
}

package com.tencoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@Autowired
	private HttpSession httpSession;

	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "user/login_form";
	}

	@GetMapping("/auth/join_form")
	public String join() {
		return "user/join_form";
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

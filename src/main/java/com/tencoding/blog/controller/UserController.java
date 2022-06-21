package com.tencoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	// /blog/user/login_form
	@GetMapping("/login_form")
	public String loginForm() {
		return "user/login_form";
	}
	
	// a 태그는 get만
	@GetMapping("/join_form")
	public String join() {
		return "user/join_form";
	}
}
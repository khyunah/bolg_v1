package com.tencoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {

	@GetMapping({"", "/"})
	public String index() {
		return "home";
	}
	
	@GetMapping("/board/save_form")
	public String saveForm() {
		log.info("saveForm() 호출");
		return "/board/save_form";
	}

}

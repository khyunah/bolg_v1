package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tencoding.blog.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"", "/"})
	public String index(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable, Model model){
		model.addAttribute("pageable", boardService.getBoardList(pageable));
		return "index";
	}
	
	@GetMapping("/board/save_form")
	public String saveForm() {
		log.info("saveForm() 호출");
		return "/board/save_form";
	}
	


}

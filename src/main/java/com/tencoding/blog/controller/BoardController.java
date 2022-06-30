package com.tencoding.blog.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"", "/"})
	public String index(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable, Model model){
		
		// 인덱스에서 페이지버튼을 동적으로 만들기 위해서 여기서 페이지 시작과 끝을 정해서 배열에 담아주기 
		ArrayList<Integer> pageCountList = new ArrayList<>();
		Page<Board> page = boardService.getBoardList(pageable);
		
		int nowPage = page.getNumber() + 1;
		int startPage = Math.max(nowPage - 2 , 1);
		int endPage = Math.min(nowPage + 2, page.getTotalPages());
		
		for (int i = startPage; i <= endPage; i++) {
			pageCountList.add(i);
		}
		
		model.addAttribute("pageable", page);
		model.addAttribute("pageCountList", pageCountList);
		return "index";
	}
	
	@GetMapping("/board/save_form")
	public String saveForm() {
		log.info("saveForm() 호출");
		return "/board/save_form";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.boardDetail(id));
		return "/board/detail";
	}
	
	@GetMapping("/board/{id}/update_form")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.boardDetail(id));
		return "/board/update_form";
	}

}

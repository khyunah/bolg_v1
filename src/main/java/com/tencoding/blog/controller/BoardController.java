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

	@GetMapping({"", "/", "/board/search"})
	public String index(String q, // @RequestParam은 무조건 q가 넘어와야 한다 ㅡ> 없애주면 q없어도 오류 안남 , 이유는 key, value 구조이기때문 
			@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable, Model model){
		
		String searchTitle = q == null ? "" : q;
		
		Page<Board> pageBoards = boardService.searchBoardByTitle(searchTitle, pageable);;

		int nowPage = pageBoards.getPageable().getPageNumber() + 1;	// 0 이 시작
		int startPage = Math.max(nowPage - 2, 1);	// 두 int 값 중에 큰 값을 반환 
		int endPage = Math.min(nowPage + 2, pageBoards.getTotalPages());

		ArrayList<Integer> pageNumbers = new ArrayList<>();
		
		for (int i = startPage; i <= endPage; i++) {
			pageNumbers.add(i);
		}
		
		model.addAttribute("pageable", pageBoards);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageNumbers", pageNumbers);
		model.addAttribute("searchTitle", searchTitle);
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
	
	/*
	 * 이 방법은 결과는 나오나 페이지 처리가 안됨 ( 실패 사례 ) 
	 */
//	@GetMapping("/board/search")
//	public String searchBoard(@RequestParam String q, Model model, 
//			@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable) {
//		Page<Board> pageBoards = boardService.searchBoardByTitle(q, pageable);
//		int nowPage = pageBoards.getPageable().getPageNumber() + 1;	// 0 이 시작
//		int startPage = Math.max(nowPage - 2, 1);	// 두 int 값 중에 큰 값을 반환 
//		int endPage = Math.min(nowPage + 2, pageBoards.getTotalPages());
//		System.out.println("---------------------------------------");
//		log.info("현재 화면의 블록 숫자 ( 현재 페이지 ) : {} ", nowPage);
//		log.info("현재 화면의 보여질 블록의 시작 번호 : {} ", startPage);
//		log.info("현재 화면의 보여질 블록의 마지막 번호 : {} ", endPage);
//		log.info("화면에 보여줄 총 게시글 / 한 화면에 보여진 게시 글( 총 페이지 수 ) : {} ", pageBoards.getTotalPages());
//		System.out.println("---------------------------------------");
//				
//		// 페이지 번호를 배열로 만들어서 던져주기
//		// 뷰 단에서 c:forEach가 배열로 되기 때문
//		ArrayList<Integer> pageNumbers = new ArrayList<>();
//		
//		// 주의 : 마지막 번호까지 저장하기
//		for (int i = startPage; i <= endPage; i++) {
//			pageNumbers.add(i);
//		}
//		
//		model.addAttribute("pageable", pageBoards);
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("pageNumbers", pageNumbers);
//		return "index";
//	}

}

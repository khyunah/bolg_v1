package com.tencoding.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.Reply;
import com.tencoding.blog.repository.BoardRepository;
import com.tencoding.blog.repository.ReplyRepository;

@RestController
public class ReplyControllerTest {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping("/test/board/{boardId}")
	public Board getBoard(@PathVariable int boardId) {
		// jackson lib 실행될때 오브젝트로 파싱을 해준다. json을 파싱할때 오브젝트를 만들때 getter를 호출해서 동작하는데 
		// 이때 무한 참조가 걸려서 엄청난 반복이 일어난다. 
		// pom.xml파일에 test security 주석 풀어주기 
		return boardRepository.findById(boardId).get();
	}
	
	/*
	 * Board를 호출했을때 reply에 포함된 board를 무시하고 
	 * Reply에서 호출했을때는 무시하지 않는다.
	 * 
	 * detail.jsp 에서 reply, board 를 호출하는 순간 무한 참조가 일어난다. ( stack overflow 발생 )
	 * 하지만 호출하지 않았기 때문에 발생하지 않았다.
	 * 
	 * 해결방법은
	 * 1. @JsonIgnoreProperties 사용 하기 
	 *   @JsonIgnoreProperties("board") 이런
	 */
	@GetMapping("/test/reply")
	public List<Reply> getReply(){
		return replyRepository.findAll();
	}
}

package com.tencoding.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.Reply;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.BoardRepository;
import com.tencoding.blog.repository.ReplyRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void write(Board board, User user) { // title, content 넘어옴 
		board.setCount(0);
		board.setUserId(user);
		boardRepository.save(board);
	}
	
	@Transactional
	public Page<Board> getBoardList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public Board boardDetail(int boardId) {
		return boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("해당 글은 찾을 수 없습니다.");
		});
	}
	
	@Transactional
	public void deleteById(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void modifyBoard(int id, Board board) { // title, content
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 글은 찾을 수 없습니다.");
		});
		
		boardEntity.setTitle(board.getTitle());
		boardEntity.setContent(board.getContent());
		
		// 더티 체킹 - 트랜젝셔널
	}
	
	@Transactional
	public Reply writeReply(User user, int boardId, Reply requestReply) {
		requestReply.setUser(user);
		Board board = boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글이 존재하지 않습니다.");
		});
		requestReply.setBoard(board);
		Reply replyEntity = replyRepository.save(requestReply);
		return replyEntity;
	}
	
	@Transactional
	public void deleteReplyById(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
	@Transactional
	public Page<Board> searchBoardByTitle(String title, Pageable pageable) {
		return boardRepository.findByTitleContaining(title, pageable);
	}

}

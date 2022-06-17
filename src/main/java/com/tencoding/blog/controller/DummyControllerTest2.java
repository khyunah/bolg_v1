package com.tencoding.blog.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.RollType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
@RequestMapping("/dummy2")
public class DummyControllerTest2 {

	@Autowired
	private UserRepository userRepository;

	// 가입
	@PostMapping("/join")
	public User join(@RequestBody User user) {
		if (user.getRole() == null) {
			user.setRole(RollType.USER);
		}

		userRepository.save(user);
		return user;
	}
	
	// 조회
	@GetMapping("/select/{id}")
	public User select(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 " + id + "는 없는 사용자 입니다.");
		});
		return user;
	}
	
	// 전체 조회
	@GetMapping("/select/all")
	public List<User> selectAll(){
		return userRepository.findAll();
	}
	
	// 삭제
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(Exception e) {
			return "해당유저가 없습니다";
		}
		return "사용자를 삭제완료 했습니다.";
	}
	
	// 업데이트
	@Transactional
	@PutMapping("/update/{id}")
	public User update(@PathVariable int id, @RequestBody User user) {
		// 검색해서 먼저 사용자가 있는지 확인하기 
		
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("없는 사용자 입니다.");
		});
		
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		
		return userEntity;
	}
	
	// 페이징 처리
	@GetMapping("/paging")
	public List<User> paging(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable){
		List<User> user = userRepository.findAll(pageable).getContent();
		return user;
	}
}

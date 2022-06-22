package com.tencoding.blog.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
@RequestMapping("/crud")
public class CrudControllerTest {

	@Autowired // 자동으로 초기화 시켜준다.
	private UserRepository repository;

	// 가입 하기
	@PostMapping("/join")
	public User join(@RequestBody User user) {
		repository.save(user);
		return user;
	}

	// 조회하기
	@GetMapping("/search/{id}")
	public User search(@PathVariable int id) {
//		User user = repository.findById(id).get();

		// 값이 없을경우 null오류 방지를 위한 방법
		User user = repository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 " + id + " 는 없는 사용자 입니다.");
		});
		return user;
	}

	// 전체 조회하기
	@GetMapping("/search-all")
	public List<User> searchAll() {
		List<User> list = repository.findAll();
		return list;
	}

	// 페이징 처리
	@GetMapping("/limit")
	public List<User> limit(@PageableDefault(size = 2) Pageable pageable) {
		List<User> list = repository.findAll(pageable).getContent();
		return list;
	}

	// 업데이트
	@Transactional
	@PutMapping("/update/{id}")
	public String update(@PathVariable int id, @RequestBody User user) {
		User userEntity = repository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 " + id + " 는 없는 사용자 입니다.");
		});
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());

		return "업데이트가 완료되었습니다.";
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			return "해당 id가 존재하지 않습니다.";
		}
		return "삭제 되었습니다.";
	}
}

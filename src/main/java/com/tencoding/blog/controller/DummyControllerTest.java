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
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.RollType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

//http://localhost:9090/blog/dummy/

@RestController
public class DummyControllerTest {

	// UserRepository 메모리에 올라가있는 상태
	// 어떻게 가져오나요? ㅡ> DI 의존성 주입하면 됨
	@Autowired // 자동으로 메모리에 올라가게 해서 연결해준다.
	private UserRepository userRepository;

	// 가입
	// REST API
	// POST
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("---------------------");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println("---------------------");
		System.out.println(user.getId());
		System.out.println(user.getCreateDate());
		System.out.println(user.getRole());

		// 문제는 디폴트값이 안먹힘
//		user.setRole("user1");
		// 잘못된 값이 들어갈 수도 있음
		// enum 타입 사용하기를 권장
		user.setRole(RollType.USER);
		// 이렇게만 하면 DB에 index로 들어감
		// @Enumerated(EnumType.STRING) 이거 해주기

		userRepository.save(user); // 추가
//		userRepository.deleteById(2);	// 삭제
		return "회원가입 완료되었습니다.";
	}

	// 검색
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// Optional 요즘 생겨나는 언어에서 많이 쓰는 타입이다.
		// null문제를 해결하기 위한 것.
		// 스프링에서는 Optional이라는 데이터타입으로 감싸서 UserEntity를 가지고 오겠다 라는 느낌.
//		User userTemp1 = userRepository.findById(id).get();	// 컴파일 시점에서 UserRepository를 통해서 어떤 타입인지 알고 있다. 
//		// 입력한 id가 없을때 null이 일어나기때문에 방지차원에서 쓰는것.
//		
//		User userTemp2 = userRepository.findById(id).orElseGet(() -> {
//			User user1 = new User();
//			user1.setUsername("tenco100");
//			user1.setPassword("asd123");
//			user1.setEmail("q@naver.com");
//			return user1;
//		}); // get이 없으면 else
//		
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자 입니다." + id);
		}); // null이면 던져라 예외처리 해라

//		User user = userRepository.findById(id);
		return user;
	}

	// 전체조회
	// http://localhost:9090/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> 전체사용자검색() {
		
		return userRepository.findAll();
	}
	
	// 페이징 처리
	// http://localhost:9090/blog/dummy/user?page=0
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable){
//		Page<User> page = userRepository.findAll(pageable);
//		List<User> user = page.getContent();
		
		List<User> user = userRepository.findAll(pageable).getContent();
		// Page 타입으로 리턴할때는 getContent()빼주기.
		return user;
	}
	
	// 업데이트 
	// id값 받고, 
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + reqUser.getPassword());
		System.out.println("email : " + reqUser.getEmail());
		// select 해서 update를 해야한다.
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자 입니다." + id);
		});
		//DB 저장
		userEntity.setPassword(reqUser.getPassword());
		userEntity.setEmail(reqUser.getEmail());
		
		// 업데이트할때 save 지양
		// User user = userRepository.save(userEntity);	// 없으면 insert 
		// username 값이 없다. 
		return null;
	}
	
	// 딜리트
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(Exception e) {
			return "해당 유저는 없습니다.";
		}
		return id + "는 삭제되었습니다.";
	}

}

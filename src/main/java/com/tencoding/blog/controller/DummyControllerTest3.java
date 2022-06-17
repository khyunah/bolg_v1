package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.RollType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
@RequestMapping("/dummy3")
public class DummyControllerTest3 {

	@Autowired
	private UserRepository userRepository;

	// insert
	@PostMapping("/join")
	public User join(@RequestBody User user) {

		if (user.getRole() == null) {
			user.setRole(RollType.USER);
		}
		userRepository.save(user);
		return null;
	}
	
	// select 
	@GetMapping("/select/{id}")
	public User select(@PathVariable int id) {
		User user = userRepository.findById(id).get();
		
//		userRepository.findById(id).orElseGet(() -> {
//			
//		});
		return user;
	}

}

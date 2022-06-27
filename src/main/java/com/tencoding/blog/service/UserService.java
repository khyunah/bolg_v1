package com.tencoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.model.RollType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service	// IoC 등록
public class UserService {

	// DI 해야함 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int saveUser(User user) {
		try {
			// 암호화를 해야 회원가입과 로그인이 가능하다.
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			user.setPassword(encPassword);
			
			user.setRole(RollType.USER);
			userRepository.save(user);			
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	// import org.springframework.transaction.annotation.Transactional;
//	@Transactional(readOnly = true)
//	public User login(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}

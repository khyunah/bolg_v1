package com.tencoding.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.tencoding.blog.model.RollType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service	// IoC 등록
public class UserService {
	
	// DI 해야함 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;	// @Bean 으로 등록했기 때문에 싱글톤 패턴으로 관리, 바로 접근 가능 
	
	@Transactional
	public int saveUser(User user) {
		try {
			// 암호화 처리
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

}

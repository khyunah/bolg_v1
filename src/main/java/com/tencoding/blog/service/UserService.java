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
	
	// 업데이트 더티 체킹해서 저장 @Transactional
	@Transactional
	public void updateUser(User user) {
		User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원정보가 없습니다.");
		});
		
		// 해시 암호화 처리 해야함
		String rawPassword = user.getPassword();
		String hashPassword = encoder.encode(rawPassword);
		userEntity.setPassword(hashPassword);
		userEntity.setEmail(user.getEmail());
	}
	
	// 유저 있나체크
	@Transactional(readOnly = true)
	public User searchUser(String username) {
		User userEntity = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return userEntity;
	}

}

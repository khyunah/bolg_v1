package com.tencoding.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service	// IoC 등록
public class UserService {

	/*
	 * 서비스가 필요한 이유
	 * 
	 * 현실 세계의 모델링 가정하여.
	 * 주문 : 서버 
	 * 음식 : 주방장 
	 * 
	 * 1. 트랜젝션 관리 
	 * 송금 할때 홍길동, 이순신 있다라고 가정. 
	 * 각각 10, 0 만원 있다.
	 * 금액을 조회해서 얼마있나 확인을 하고 요청금액을 확인후 송금한다 
	 * 
	 * 하나의 기능 ( 조회, 송금 ) 을 여러개 묶어서 단위의 기능을 만들어 내기 위해 서비스가 필요하다.
	 * 물론, 하나의 기능도 서비스가 될수 있다.
	 * 
	 */
	
	// DI 해야함 
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public int saveUser(User user) {
		try {
			userRepository.save(user);			
			return 1;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}

	}
}

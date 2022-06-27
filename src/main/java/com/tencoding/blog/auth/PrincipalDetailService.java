package com.tencoding.blog.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	// 유저가 있는지 없는지 확인하는 역할
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
		});	// 사용자가 있다면 시큐리티 전용 세션에 넣어줘야 한다. ( return에 넣어주면 되는데 넣을때 데이터 타입이 정해져 있다 UserDetails ) 
		// 그래서 만들어 뒀던 PrincipalDetails 클래스를 사용한다. ( 생성자로 User를 연결 해놨음. 컴포지션 관계 ) 
		return new PrincipalDetail(principal);	// 시큐리티 세션에 유저 정보가 저장이된다.
	}

}

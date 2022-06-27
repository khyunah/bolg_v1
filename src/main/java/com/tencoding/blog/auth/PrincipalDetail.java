package com.tencoding.blog.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tencoding.blog.model.User;

import lombok.Data;

@Data
public class PrincipalDetail implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 로그인 dto 객체와 컴포지션 관계를 만들어줘야 한다. 
	private User user;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/*
	 * isAccountNonExpired()
	 * 계정이 만료되지 않았는지 리턴 한다. 
	 * ( 리턴값이 
	 * true 면 계정이 만료 안된 상태 
	 * false 면 계정이 만료된 유저 )
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * isAccountNonLocked()
	 * 계정 잠긴 여부 확인 
	 * 예를 들면 부정 행위를 해서 잠시 묶인 상태
	 * true 는 사용가능 
	 * false 는 사용 불가능
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * isCredentialsNonExpired()
	 * 비밀번호 만료 여부를 알려준다. 
	 * true 면 사용가능
	 * false 면 사용 불가능
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * isEnabled()
	 * 계정 활성화 여부 
	 * true 면 사용가능
	 * false 면 로그인 불가 
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/*
	 * getAuthorities()
	 * 계정의 권한을 반환한다. 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				// "ROLE_" 스프링 시큐리티 규칙 ( 꼭 넣어줘야함 )
//				// "ROLE_USER" , "ROLE_ADMIN" 
//				return "ROLE_" + user.getRole();
//			}
//		});
		collectors.add(() -> {return "ROLE_" + user.getRole();});
		return collectors;
	}

}

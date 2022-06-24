package com.tencoding.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

 
@Configuration // 메모리에 빈 등록
@EnableWebSecurity // 시큐리티 필터로 등록하는 어노테이션 ( 필터 커스텀 을 위한 )
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 ( post ) 권한 및 인증 처리를 미리 체크하겠다 라는 의미.
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 상속 
	
	// 1. 비밀번호 해쉬처리 
	@Bean // 메모리에 올려야 한다. IoC가 되어 필요할때 가져와서 사용하기
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 2. 특정 주소 필터를 설정할 예정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()	// csrf 를 껏다 자바스크립트를 이용해서 던질수 있음 
		.authorizeRequests()
		.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/image/**")	// 다 막지만 해당 주소 허용 
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/auth/login_form");	// 인증 안되어 있으면 로그인 페이지로 가라고 지시하는 중 
		
	}
}

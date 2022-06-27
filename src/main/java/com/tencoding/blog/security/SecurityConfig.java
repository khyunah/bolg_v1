package com.tencoding.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 비밀 번호를 해시 처리 해야만 로그인과 회원가입이 가능 하다. 
	@Bean // 싱글톤 패턴으로 IoC 등록하는 것 
	public BCryptPasswordEncoder encoderPwd() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/image/**")	// 시큐리티 설정이 다 막지만 해당 리소스에 대한 권한을 설정하겠다.
		.permitAll() // 위에 설정한 리소스에 접근을 인증절차 없이 허용한다. 
		.anyRequest() // .anyRequest().authenticated()
		.authenticated() // ㅡ> 모든 리소스를 의미, 접근허용 리소스 및 인증 후 특정 레벨의 권한을 가진 사용자만 접근가능한 리소스를 설정한다. 
		// 그외에 나머지 리소스들은 무조건 인증을 완료해야 접근이 가능하다. 
		.and()
		.formLogin() // 일반적인 로그인 방식을 사용하겠다는 의미. 
		.loginPage("/auth/login_form")	// 로그인을 해야하면 해당 주소로 로그인 페이지가 넘어감 ( 설정하지 않으면 시큐리티에서 제공하는 기본 로그인 화면으로 넘어감 )
		.loginProcessingUrl("/auth/loginProc") // 로그인 인증 처리 하는 필터가 호출되어 아이디와 비번을 받아와서 인증처리 수행 된다 ( UsernamePasswordAuthenticationFilter 필터가 수행됨 )
		.defaultSuccessUrl("/") // 로그인 성공시 어디로 보낼지, 설정하지 않은 디폴트 값은 / 이다. 
		;
	}
}

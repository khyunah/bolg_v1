package com.tencoding.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.tencoding.blog.auth.PrincipalDetailService;
 
@Configuration // 메모리에 빈 등록
@EnableWebSecurity // 시큐리티 필터로 등록하는 어노테이션 ( 필터 커스텀 을 위한 )
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 ( post ) 권한 및 인증 처리를 미리 체크하겠다 라는 의미.
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 상속 
	
	// 1. 비밀번호 해쉬처리 
	@Bean // 메모리에 올려야 한다. IoC가 되어 필요할때 가져와서 사용하기
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	} // 시큐리티 필터 단에서 동작하는 미리 떠있어야 하는 녀석이라 @Bean
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	// 필터 역할 
	// 2. 특정 주소 필터를 설정할 예정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable()	// csrf 를 껏다 자바스크립트를 이용해서 던질수 있음 
		http
		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and()
		
		.authorizeRequests()
		.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/image/**", "/test/**")	// 시큐리티 설정이 다 막지만 해당 리소스에 대한 권한을 설정하겠다.
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
		
		// 스프링 시큐리티가 해당 주소로 요청이오면 가로채서 대신 요청을 해준다. 
		// 단 이 동작을 완료하기 위해서는 만들어야 할 클래스가 있다.
		// UserDetails 라는 타입의 오브젝트를 만들어야 한다. 
		
		// 두가지 해야함
		// 디비에 사용자 유무 체크, 비밀번호 해시가 어떤 알고리즘인지 알려줘야함 
	}
	
	// 3. 시큐리티가 대신 로그인을 해주는데 password를 가로채서 해당 패스워드가 무엇으로 해시처리 되었는지 함수를 알려줘야 한다. 
	// 같은 해시로 암호화해서 디비에 있는 해시 값과 비교할 수 있다. 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 1. userDetailService에 들어갈 객체를 만들어 주어야 한다. 데이터베이스에 접근해서 유저가 있는지 없는지 확인
		// 2. passwordEncoder에 우리가 사용하는 해시 함수를 알려주어야 한다.  비밀번호가 맞는지 아닌지 확인 
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
		
	}
}

package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencoding.blog.dto.KakaoProfile;
import com.tencoding.blog.dto.KakaoProfile.KakaoAccount;
import com.tencoding.blog.dto.OAuthToken;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@Controller
public class UserController {
	
//	@Autowired
//	private HttpSession httpSession;
	@Autowired
	private UserService userService;
	
	// 초기 파라미터 들고올 것
	@Value("${tenco.key}")
	private String tencoKey; 
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "user/login_form";
	}

	@GetMapping("/auth/join_form")
	public String join() {
		return "user/join_form";
	}
	
	// 기본 데이터 파싱 전략
	// 'application/x-www-form-urlencoded;charset=UTF-8' 키밸류값구조이기때문에 @RequestBody
	// 사용하면 안됨 @RequestBody는 json형식이라 형식이 맞지않음
	@PostMapping("/auth/joinProc")
	public String save(User user) {
		userService.saveUser(user);
		return "redirect:/";
	}

//	@GetMapping("/logout")
//	public String logout() {
//		// 세션 정보 제거 
//		httpSession.invalidate();
//		return "redirect:/";
//	}
	
	@GetMapping("/user/update_form")
	public String updateForm() {
		return "user/update_form";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(@RequestParam String code) {

		/*
		 * 1.
		 * 카카오 소셜 로그인을 하고 나면 여기로 인가 코드가 넘어온다.
		 * 해당 인가코드를 가지고 토큰을 요청 할 것이다.
		 * 요청하기 위해서 POST 방식으로 보낼것임.
		 */
		
		// 여기서 다시 post 보내기 방법  
		// 1. HttpURLConnect
		// 2. Retrofit2
		// 3. OkHttp
		// 4. RestTemplate
		RestTemplate rt = new RestTemplate();
		
		// http 메세지 만들어 보낼때 ( post ) 
		// header를 만들고 body를 만들어서 통신을 하는것이 http 프로토콜 방식 
		
		// 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 바디 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "0d6bcf296d67c35ad944b2a3d38df9be");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);
		
		// 헤더와 바디를 하나의 오브젝트로 담아야한다. 
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		// http 요청 - post 방식 으로 요청하고 응답 받기 
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", 
				HttpMethod.POST, 
				kakaoTokenRequest, 
				String.class); // 마지막은 응답받을 객체 타입
		// 이렇게 하면 토큰을 요청 한것이고 응답을 받은 것이다. 
		
		/*
		 * 2. 
		 * 토큰으로 액세스 토큰에 접근하여 회원가입할때 필요한 정보를 얻어내기 위해서 
		 * 응답받은 String 타입에서 오브젝트 타입으로 변경을 해준다. 
		 * 변환하는 방법으로 여러가지가 있지만 ( GSon, JSon Simple, Object Mapper ) ObjectMapper 를 이용할 것임.
		 */

		// 오브젝트로 변환하기 위한 클래스가 필요함 
		OAuthToken authToken = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			authToken = objectMapper.readValue(response.getBody(), OAuthToken.class); // 파싱 처리 완료 
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("-------------------------");
		System.out.println(authToken);
		System.out.println("-------------------------");
		// 토큰은 이런식으로 날라온다.
		// OAuthToken(accessToken=UaIw5f_3PByvEz2nl35kL0moH4cKq_cDylLoiHz9CisNHwAAAYGtLW-v, tokenType=bearer, refreshToken=de_lvALt544o2geMmUV9mF7L0vBmfO-6i7gVS1E6CisNHwAAAYGtLW-u, expiresIn=21599, scope=account_email profile_image profile_nickname, refreshTokenExpiresIn=5183999)
		
		/*
		 * 3.
		 * 파싱한 액세스 토큰오브젝트를 사용해서 사용자 정보 가져오기
		 * 액세스 토큰으로 요청을 해야 사용자 정보에 접근이 가능하다. 
		 * get과 post 방식 둘다 가능하다. 
		 */
		// 액세스 토큰 사용해서 사용자 정보 가져오기
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + authToken.getAccessToken()); // 무조건 한칸 띄우기 주의 !!!!
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 바디는 
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		ResponseEntity<KakaoProfile> kakaoProfileResponse = rt2.exchange("https://kapi.kakao.com/v2/user/me", // 여기에 응답이 담긴다.
				HttpMethod.POST, 
				kakaoProfileRequest, 
				KakaoProfile.class);
		// 여기까지 사용자 정보를 들고온 상태 
		
		/*
		 * 4.
		 * 소셜 로그인 한 사용자를 블로그에 가입 시켜줘야 한다. 
		 * 사용자가 로그인 했을 경우 처음 최초 사용자라면 회원가입 처리한다.
		 * 한번이라도 가입 진행이 된 사용자면 로그인 처리를 해주면 된다. 
		 * 만약 회원가입시 필요한 정보가 더 있어야 된다면 추가로 사용자한테 정보를 받아서 가입 진행 처리를 해야한다. 
		 */
		
		// 사용자 정보를 응답받은 것 
		KakaoAccount account = kakaoProfileResponse.getBody().getKakaoAccount();
		
		/*
		 * 소셜로 자동으로 회원가입 할때 블로그에서 사용될 username을 넣어줘야 한다. 
		 * 가입 시킬때 username 중복이 되지 않도록 소셜 가입자는 username을 다르게 설정해준다.
		 */
		User kakaoUser = User.builder()
				.username(account.getEmail() + "_" + kakaoProfileResponse.getBody().getId())
				.password(tencoKey)
				.email(account.getEmail())
				.oauth("kakao")
				.build();
		System.out.println(kakaoUser);
		
		// UserService 호출해서 저장 진행 		
		// 단, 소셜 로그인 요청자가 이미 가입된 유저라면 저장하면 안됨 
		User originUser = userService.searchUser(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("신규 회원이 아니기때문에 회원가입 진행");
			userService.saveUser(kakaoUser);
		}
		
		// 자동 로그인 처리 ㅡ> 시큐리티 세션에 강제 저장 
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), tencoKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	
}

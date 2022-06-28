package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencoding.blog.dto.KakaoProfile;
import com.tencoding.blog.dto.OAuthToken;
import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@Controller
public class UserController {
	
//	@Autowired
//	private HttpSession httpSession;
	@Autowired
	private UserService userService;

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
	@ResponseBody
	public String kakaoCallback(@RequestParam String code) {
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
		// 어떤값을 넣어야 할까 ?
		params.add("grant_type", "authorization_code");
		params.add("client_id", "0d6bcf296d67c35ad944b2a3d38df9be");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);
		
		// 헤더와 바디를 하나의 오브젝트로 담아야한다. 
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		// http 요청 - post 방식 으로 해야한다. ㅡ> 응답 받기 
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", 
				HttpMethod.POST, 
				kakaoTokenRequest, 
				String.class); // 마지막은 응답받을 객체 타입
		
		// 액세스토큰만 자르기 위해서 오브젝트 타입으로 변환 ㅡ> gson, Json Simple, Object Mapper
		// 클래스가 필요함 ( 오브젝트 )
		OAuthToken authToken = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			authToken = objectMapper.readValue(response.getBody(), OAuthToken.class); // 파싱 처리 완료 
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// 액세스 토큰 사용
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + authToken.getAccessToken()); // 무조건 한칸 띄우기 주의 !!!!
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 바디는 
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);

		// 파싱 하기 KakaoProfile.class 만들기
		KakaoProfile kakaoProfile = null;
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(response2.getBody());
		try {
			kakaoProfile = mapper.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
//		System.out.println(kakaoProfile.getId() + "");
//		System.out.println(kakaoProfile.getProperties());
		
		return "카카오 프로필 정보 요청 : " + response2;
	}
	
}

package com.tencoding.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration // IoC 등록
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		// 가상 경로를 만들어서 실제 경로에 접근할수 있는 
		registry.addResourceHandler("/upload/**")	// url 패턴을 만들어준다. /upload라는 주소가 있으면 낚아챈다.
		.addResourceLocations("file:///" + uploadFolder)	// 슬러시 /// 세개 , 실제 물리적인 경로 파일이 들어오면 이 주소로 변환 시켜준다.
		.setCachePeriod(60 * 10 * 6) // 한번 만들어둔 이미지의 캐시 지속 시간 
		.resourceChain(true) // 리소스 찾는것 최적화 ( 성능 )
		.addResolver(new PathResourceResolver());

	}

	@Bean // 메서드를 수행 시킬때 메모리에 올라가야 하는데 @Bean으로 관리 @Autowired는 사용할때에
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean(){ // 필터를 등록하게 해주는 클래스 FilterRegistrationBean
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean = new FilterRegistrationBean<XssEscapeServletFilter>();
		
		// 어떤 필터를 등록할지 
		filterRegistrationBean.setFilter(new XssEscapeServletFilter());
		filterRegistrationBean.setOrder(1); // 필터 순서 지정
		filterRegistrationBean.addUrlPatterns("/*"); // 어떤 url에 지정할 것이냐 "/*" ㅡ> 모든 url에서 동작 시킬것 
		return filterRegistrationBean;
	}

}

package com.tencoding.blog.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration // IoC 등록
public class WebConfig {

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

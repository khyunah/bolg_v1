<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ include file="../layout/header.jsp" %>
<div class="container">
<!-- loginProc를 만들지 않음 ( 스프링 시큐리티가 가로채서 진행을 해줄 것임 )-->
	<form action="/auth/loginProc" method="post">
	  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	  <div class="form-group">
	    <label for="username">user name:</label>
	    <input type="text" class="form-control" value="tenco" placeholder="Enter username" id="username" name="username"/>
	  </div>
	  <div class="form-group">
	    <label for="password">Password:</label>
	    <input type="password" class="form-control" value="asd123" placeholder="Enter password" id="password" name="password"/>
	  </div>
	  <div class="form-group form-check">
	    <label class="form-check-label"> 
	    <input class="form-check-input" type="checkbox" /> Remember me </label>
	  </div>  
	  
	  <button id="btn-login" type="submit" class="btn btn-primary">로그인</button>
	  <a href="https://kauth.kakao.com/oauth/authorize?client_id=0d6bcf296d67c35ad944b2a3d38df9be&redirect_uri=http://localhost:9090/auth/kakao/callback&response_type=code">
	  	<img src="/image/kakao_login.png" width="74" height="38">
	  </a>
	</form>
		
</div>
<br/>
<!-- 
<script src="/js/user.js"></script>
 -->
<%@ include file="../layout/footer.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container">

	<form action="#" method="post">
		<input type="hidden" id="id" value="${principal.user.id}">
		<div class="form-group">
			<label for="username">user name: </label>
			<input type="text" value="${principal.user.username}" id="username" name="username" class="form-control" readonly="readonly">
		</div>
		
		<div class="form-group">
			<label for="password">password: </label>
			<input type="text" value="" id="password" name="password" class="form-control">
		</div>
		
		<div class="form-group">
			<label for="email">email: </label>
			<input type="text" value="${principal.user.email}" id="email" name="eamil" class="form-control" >
		</div>
		
		<button type="button" id="btn-update" class="btn btn-primary">회원수정완료</button>
	</form>

</div>
<br/><br/>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>

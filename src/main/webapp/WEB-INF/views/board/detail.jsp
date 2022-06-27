<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container">
	<button class="btn bg-secondary" onclick="history.back();">돌아가기</button>
	<button class="btn btn-warning" id="btn-update">수정</button>
	<button class="btn btn-danger" id="btn-delete">삭제</button>
	<br/>
	<br/>
	<div class="form-group m-2">
		<h3>${board.title}</h3>
	</div>
	<hr/>
	<div class="form-group m-2">
		<h3>${board.content}</h3>
	</div>
	<br/>
	<br/>
	<hr/>
</div>

<%@ include file="../layout/footer.jsp" %>
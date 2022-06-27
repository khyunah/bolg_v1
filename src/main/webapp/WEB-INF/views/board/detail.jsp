<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container">
	<button class="btn bg-secondary" onclick="history.back();">돌아가기</button>
	
	<c:if test="${board.userId.id == principal.user.id}">
		<a href="/board/${board.id}/update_form" class="btn btn-warning">수정</a>
		<button class="btn btn-danger" id="btn-delete">삭제</button>
	</c:if>
	
	<br/><br/>
	<div>
		글 번호 : <span id="board-id"><i>${board.id}</i></span><br/>
		글 작성자 : <span id=""><i>${board.userId.username}</i></span>
	</div>
	<br/><br/>
	<div class="form-group m-2">
		<h3>${board.title}</h3>
	</div>
	<hr/>
	<div class="form-group m-2">
		<h3>${board.content}</h3>
	</div>
	<br/><br/>
	<hr/>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>
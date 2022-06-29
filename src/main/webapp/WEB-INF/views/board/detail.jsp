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
	
	<div class="card">
		<div>
			<div class="card-body"><textarea rows="1" class="form-control" id="reply-content"></textarea></div>
			<div class="card-footer"><button type="button" id="reply-save" class="btn btn-primary">등록</button></div>
		</div>
	</div>
	<br/>
	
	<div class="card">
		<div class="card-header">댓글 목록</div>
	</div>
	<ul class="list-group" id="reply-box">
	<!-- 반복 예정, id값 내가 만든 id면 하이픈 두개쓰기 reply--hyunah 이런식  -->
		<li class="list-group-item d-flex justify-content-between" id="reply-1">
			<div>댓글 내용</div>
			<div class="d-flex">
				<div>작성자 : 유저이름&nbsp;&nbsp;</div>
				<button class="badge badge-danger">삭제</button>
			</div>
		</li>
		<li class="list-group-item d-flex justify-content-between">
			<div>댓글 내용</div>
			<div class="d-flex">
				<div>작성자 : 유저이름&nbsp;&nbsp;</div>
				<button class="badge badge-danger">삭제</button>
			</div>
		</li>
	</ul>
	<br/><br/>
	
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>
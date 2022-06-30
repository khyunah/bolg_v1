<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp" %>

<div class="container">
	<c:forEach var="board" items="${pageable.content}">
		<div class="card m-2">
			<div class="card body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>
</div>
<br/>

<ul class="pagination justify-content-center">
	<c:set var="isDisabled" value="disabled"></c:set> 
	<c:set var="isNotDisabled" value=""></c:set>
		
  	<li class="page-item ${pageable.first ? isDisabled : isNotDisabled}">
  		<a class="page-link" href="/?page=${pageable.number - 1}">Previous</a>
  	</li>
  	
  	<!-- 동적으로 생성되는 페이지 번호 버튼 만들어주기  -->
  	<c:forEach var="page" items="${pageCountList}">
  	
  		<!-- 동적으로 active 처리 해주기 -->
  		<c:choose>
			<c:when test="${pageable.number + 1 == page}">
				<li class="page-item active"><a class="page-link" href="/?page=${page-1}">${page}</a></li>
			</c:when>  		
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/?page=${page-1}">${page}</a></li>
			</c:otherwise>
  		</c:choose>
  	</c:forEach>
  	
  	<li class="page-item ${pageable.last ? isDisabled : isNotDisabled}">
  		<a class="page-link" href="/?page=${pageable.number + 1}">Next</a>
  	</li>
</ul>

<br/>
<%@ include file="layout/footer.jsp" %>

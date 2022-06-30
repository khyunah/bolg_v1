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
	<c:set var="isNowPage" value="active"></c:set>
		
  	<li class="page-item ${pageable.first ? isDisabled : isNotDisabled}">
  		<a class="page-link" href="/?page=${pageable.number - 1}">Previous</a>
  	</li>
  	
  	<c:forEach var="num" items="${pageNumbers}">
  		<c:choose>
  			<c:when test="${pageable.number + 1 eq num}">
				<li class="page-item active"><a class="page-link" href="/?page=${num-1}">${num}</a></li>  			
  			</c:when>
  			<c:otherwise>
  				<li class="page-item"><a class="page-link" href="/?page=${num-1}">${num}</a></li>
  			</c:otherwise>
  		</c:choose>
  		
  	</c:forEach>
	
  	<li class="page-item ${pageable.last ? isDisabled : isNotDisabled}">
  		<a class="page-link" href="/?page=${pageable.number + 1}">Next</a>
  	</li>
</ul>

<br/>
<%@ include file="layout/footer.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="container py-5"> <!-- py 패딩의 탑 -->
	<div>
		<!-- 반드시 포스트로 넘겨야한다. -->
		<!--  enctype="multipart/form-data" 마임 타입 변경 가능 문자열로 던짐  -->
		<form action="/story/image/upload" enctype="multipart/form-data" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<div class="input-group mt-3">
				<!-- 파일은 바이너리로 날라간다.  -->
				<input type="file" name="file" class="custom-file-input" id="customFile" required="required">
				<label class="custom-file-label" for="customFile"></label>
			</div>
			
			  <div class="input-group mt-3">
			    <div class="input-group-prepend">
			      <span class="input-group-text">스토리 설명</span>
			    </div>
			    <input type="text" class="form-control" name="storyText" required="required">
			  </div>
			  
			  <div class="input-group mt-3">
			  	<button type="submit" class="btn btn-info">스토리 등록</button>
			  </div>
		</form>
	</div>
</main>

<script>
	// Add the following code if you want the name of the file appear on select 
	$(".custom-file-input").bind("change", function() {	// 변화가 있다면
		console.log($(this).val());
	  let fileName = $(this).val().split("\\").pop(); // 파일찾는 창 실행한다. 파일이름을 나눠서 변수에 담는다
	  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
	});
	// siblings 형제태그
</script>

<%@ include file="../layout/footer.jsp" %>

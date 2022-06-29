let index = {
	init: function(){
		$("#btn-save").bind("click", () => {
			this.save();
		});
		
		$("#btn-delete").bind("click", () => {
			this.deleteById();
		});
		
		$("#btn-update").bind("click", () => {
			this.update();
		});
		
		$("#btn-reply-save").bind("click", () => {
			this.replySave();
		});
	}, 
	
	save: function(){
		// 데이터 가져오기 
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}
		console.log("데이터확인")
		console.log(data)
		
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			// 응답 받는 데이터 타입 
			dataType: "json"
		}).done(function(data, textStatus, xhr){
			if(data.status){
				alert("글쓰기가 완료 되었습니다.");
				location.href = "/"
			}
		}).fail(function(error){
			alert("글쓰기에 실패 하였습니다.");
		});
	}, 
	
	deleteById: function(){
		let id = $("#board-id").text()	// 컨텍스트를 들고올때는 .text()
		
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id
		}).done(function(data){
			if(data.status){
				alert("삭제가 완료되었습니다.");
				location.href = "/";
			}
		}).fail(function(){
			alert("삭제 실패");
		});
	},
	
	update: function(){
		let boardId = $("#id").val();
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}
		
		$.ajax({
			type: "PUT",
			url: "/api/board/" + boardId,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: false // 비동기 처리 ( 기본값 )
		}).done(function(data){
			if(data.status){
				alert("글 수정이 완료되었습니다.");
				location.href = "/";
			}
		}).fail(function(){
			alert("글 쓰기에 실패하였습니다.");
		});
	},
	
	// 댓글 등록
	replySave: function(){
		// 데이터 가져오기 ( boardId 해당 게시글의 id )
		let data = {
			boardId: $("#board-id").text(),
			content: $("#reply-content").val()
		}
		
		console.log("데이터확인")
		console.log(data)

		// 백틱 `` ㅡ> 자바스크립트 변수를 문자열 안에 넣어서 사용할 수 있다. 
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			// 응답 받는 데이터 타입 
			dataType: "json"
		}).done(function(response){
			// 자바스크립트에서 숫자 1은 true이다 
			if(response.status){
				console.log(response.data);
				addReplyElement(response.data);
			}
		}).fail(function(error){
			alert("댓글 작성에 실패 하였습니다.");
		});
	}
}

function addReplyElement(reply){
	let childElement = `
		<li class="list-group-item d-flex justify-content-between" id="reply-${reply.id}">
			<div>${reply.content}</div>
			<div class="d-flex">
				<div>작성자 : ${reply.user.username}&nbsp;&nbsp;</div>
				<button class="badge badge-danger">삭제</button>
			</div>
		</li>
	`;
	
	$("#reply-box").prepend(childElement);
	$("#reply-content").val("");
}

index.init();

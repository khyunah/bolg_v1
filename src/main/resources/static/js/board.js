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
		/*
		$("#btn-reply-save").bind("click", () => {
			this.replySave();
		});
		*/
	}, 
	
	save: function(){
		// 데이터 가져오기 
		let data = {
			title: xSSCheck($("#title").val(), 1),
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
	replySave: function(id){
		
		// csrf 활성화 후에는 헤더에 token 값을 넣어야 정상 동작 된다.
		let token = $("meta[name='_csrf']").attr("content");	// 속성 attr 
		let header = $("meta[name='_csrf_header']").attr("content");
		
		console.log("token : " + token);
		console.log("header : " + header);
		
		// 데이터 가져오기 ( boardId 해당 게시글의 id )
		let data = {
			boardId: $("#board-id").text(),
			content: $("#reply-content").val()
		}
		
		console.log("데이터확인")
		console.log(data)

		// 백틱 `` ㅡ> 자바스크립트 변수를 문자열 안에 넣어서 사용할 수 있다. 
		$.ajax({
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token)				
			},
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
				addReplyElement(response.data, id);
			}
		}).fail(function(error){
			alert("댓글 작성에 실패 하였습니다.");
		});
	},
	
	replyDelete: function(boardId, replyId){
		
		let token = $("meta[name='_csrf']").attr("content");	// 속성 attr 
		let header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token)				
			},
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
		}).done(function(response){
			console.log(response);
			alert("댓글 삭제 성공");
			location.href = `/board/${boardId}`;
		}).fail(function(error){
			console.log(error);
			alert("댓글 삭제 실패");
		});
	}
}

function addReplyElement(reply, id){
	let childElement = `
		<li class="list-group-item d-flex justify-content-between" id="reply-${reply.id}">
			<div>${reply.content}</div>
			<div class="d-flex">
				<div>작성자 : ${reply.user.username}&nbsp;&nbsp;</div>
				<!-- eq : 같은가? -->
				<c:if test="${reply.user.id == id}">
					<button onclick="index.replyDelete(${reply.board.id}, ${reply.id})" class="badge badge-danger">삭제</button>
				</c:if>
			</div>
		</li>
	`;
	
	$("#reply-box").prepend(childElement);
	$("#reply-content").val("");
}

// xss 막기
function xSSCheck(str, level) {
    if (level == undefined || level == 0) {
        str = str.replace(/\<|\>|\"|\'|\%|\;|\(|\)|\&|\+|\-/g,"");
    } else if (level != undefined && level == 1) {
        str = str.replace(/\</g, "&lt;");
        str = str.replace(/\>/g, "&gt;");
    }
    return str;
}

index.init();

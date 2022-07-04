let index = {
	
	init: function() {
		
		$("#btn-save").bind("click", () => {
			this.save();
		});
		
		$("#btn-update").bind("click", () => {
			this.update();
		});
		
	},
	
	save: function() {
		let data = {
			username: $("#username").val(), 
			password: $("#password").val(), 
			email: $("#email").val()
			
		}
		// console.log(data);
		
		// ajax 호출 
		$.ajax({
			// 서버측에 회원가입 요청 	
			type: "POST", 
			url: "/auth/joinProc",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" // 응답이 왔을 때 기본 데이터 타입(Buffered 문자열) => js object 자동 변환
		
		}).done(function(data, textStatus, xhr){
			// 통신 성공시
			console.log("xhr :" + xhr); 
			console.log(xhr); 
			console.log("textStatus :" + textStatus);
			console.log("data  : " + data);
			alert("회원가입이 완료 되었습니다.");
			location.href = "/";  
		}).fail(function(error){
			console.log(error)
			alert("회원가입에 실패 하였습니다.");
		});
	}, 
	
	update: function() {
		
		// csrf 활성화 후에는 헤더에 token 값을 넣어야 정상 동작 된다. 
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		
		let data = {
			username: $("#username").val(),
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		$.ajax({
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token)				
			},
			type: "PUT", 
			url: "/user",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(data) {
			if(data.status) {
				alert("회원정보 수정이 완료 되었습니다.")
				location.href = "/";
			}
						
		}).fail(function(error) {
			alert("회원정보 수정이 실패하였습니다.")
		});
		
	} // end of update 
	
}

index.init(); 

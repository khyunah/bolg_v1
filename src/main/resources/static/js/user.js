let index = {
	
	init: function(){
		$("#btn-save").bind("click", () => {
			this.save();
		});
		
		//전통적인 로그인 방식 때 사용한 부분
		/*
		$("#btn-login").bind("click", () => {
			this.login();
		});
		*/
		
		$("#btn-update").bind("click", () => {
			this.update();
		});
	},
	
	save: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		// console.log(data);
		
		// ajax() 호출 
		$.ajax({
			// 서버측에 회원가입 요청 (로직)
			type: "POST",
			url: "/auth/joinProc",
			// 자바와 js는 오브젝트 타입이 다른데 밑의 코드가 자동으로 파싱해준다.
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json" // 작성하지않아도 기본값 json임.
			// 응답이 왔을때 기본 데이터 타입 버퍼드 문자열로 받아서 js object로 자동으로 변환
			
		}).done(function(data, textStatus, xhr){
			// 통신 성공시
			console.log("xhr : " + xhr);
			console.log(xhr);
			console.log("textStatus : " + textStatus);
			console.log("data : " + data);
			
			//alert("회원가입이 완료 되었습니다.");
			
			// 주소 요청해서 이동
			//location.href = "/";
			
		}).fail(function(error){
			// 통신 실패시
			console.log(error);
			
			alert("회원가입에 실패했습니다.");
		});
	},
	
	/*
	login: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		}
		
		$.ajax({
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(data, textStatus, xhr){
			alert("로그인이 완료되었습니다.");
			location.href = "/";
			console.log(data);
		}).fail(function(error){
			alert("로그인에 실패했습니다.");
			console.log(error);
		});
	}
	*/
	
	update: function(){
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(data){
			if(data.status){
				alert("회원 정보 수정이 완료되었습니다.");
				location.href = "/";
			}
		}).fail(function(error){
			alert("회원 정보 수정이 실패하였습니다.");
		});
		
		console.log(data);
	}
	
}

index.init();
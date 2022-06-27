let index = {
	init: function(){
		$("#btn-save").bind("click", () => {
			this.save();
		})
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
	}
}

index.init();

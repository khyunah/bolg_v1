<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/test/xss" method="get">
		<input name="title" value="">
		<input name="content" value='<img src="test" onmouseover="alert(\'xss 공격\');" />'>
		<button type="submit">서버로 전송</button>
	</form>
</body>
</html>
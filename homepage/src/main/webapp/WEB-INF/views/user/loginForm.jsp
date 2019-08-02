<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title>
<script>
	function login(){		
		if(document.loginForm.id.value=="") alert("아이디를 입력하세요");
		else if(document.loginForm.password.value=="") alert("패스워드를 입력하세요");
		else document.loginForm.submit();		
	}
	
</script>
</head>
<body>
	${errorMessage}<br><br>

	<form name="loginForm" method="post" action="login">
		아이디 : <input type="text" id="id" name="id">
		패스워드 : <input type="password" name="password" ><br>
			
		<input type="hidden" name="save" value="n">
		<input type="button" value="로그인" onclick="login()">
		<input type="reset" value="리셋">
	</form>
</body>					
</html>
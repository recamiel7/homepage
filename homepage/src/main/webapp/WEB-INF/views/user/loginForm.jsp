<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title>
<link href="assets/css/user.css" rel="stylesheet" />
<script>
	function login(){		
		if(document.loginForm.id.value=="") alert("아이디를 입력하세요");
		else if(document.loginForm.password.value=="") alert("패스워드를 입력하세요");
		else document.loginForm.submit();		
	}
	
</script>
</head>
<body>
	<form id="loginForm" name="loginForm" method="post" action="login">
		<label>아이디</label> <input type="text" id="id" name="id"><br>
		<label>패스워드</label> <input type="password" name="password" 
									onkeypress="if(event.keyCode == 13){ login(); return; }"><br><br>
			
		<input type="hidden" name="save" value="n">
		<input type="button" value="로그인" onclick="login()">
		<input type="reset" value="리셋">
	</form>
	${errorMessage}<br>
		
</body>					
</html>
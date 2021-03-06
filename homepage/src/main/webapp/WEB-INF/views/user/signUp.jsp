<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<link href="assets/css/user.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.4.1.min.js">
	
</script>
<script type="text/javascript">
	function checkId() {

		if ($("#id").val() == "") {
			alert("아이디를 입력하세요");
		} else {
			$.get("checkId",

			{
				"id" : $('#id').val()
			},

			function(data, status) {
				if (status == "success") {
					if (data == "n") {
						alert("중복되는 아이디가 있습니다.")
						$('#checkId').val("n");
					} else {
						alert("중복되는 아이디가 없습니다.")
						$('#checkId').val("y");
					}
				} else {
					alert("아이디 체크 실패")
				}
			}

			);
		}

	}

	function checking() {
		if (document.registForm.id.value == "")
			alert("아이디를 입력하세요");
		else if (document.registForm.password.value == "")
			alert("패스워드를 입력하세요");
		else if (document.registForm.passwordCheck.value == "")
			alert("패스워드 확인을 입력하세요");
		else if (document.registForm.password.value != document.registForm.passwordCheck.value)
			alert("패스워드가 일치하지 않습니다.");
		else if (document.registForm.checkId.value == "n")
			alert("아이디 중복 확인을 하세요.");
		else
			document.registForm.submit();
	}
</script>
</head>
<body>
	<div id="registForm" >
		<form name="registForm" method="post" action="regist" enctype="multipart/form-data">
			
			<label>아이디</label> <input type="text" id="id" name="id"><br>
			<label>패스워드</label> <input type="text" name="password"><br>
			<label>패스워드 확인 </label> <input type="text" name="passwordCheck"><br>
			<label>프로필사진</label>  <input type="file" id="reviewImageFileOpenInput" name="image" accept="image/*"> <br>			
			<input id="checkId" type="hidden" value="n"> 
			<input type="button" value="회원 가입" onclick="checking()">
		</form>
		<input id="checkButton" type="button" value="중복 확인" onclick="checkId()">
	</div>

</body>
</html>
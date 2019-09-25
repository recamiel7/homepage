<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>일정 작성</title>
<link href="assets/css/sub.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
	function scheduleInsert() {

		if ($("#title").val() == "") {
			alert("제목을 입력하세요")
		} else if ($("#content").val() == "") {
			alert("내용을 입력하세요")
		} else if ($(".startYear").val() == "") {
			alert("시작연도를 입력하세요")
		} else if ($(".startMonth").val() == "") {
			alert("시작월을 입력하세요")
		} else if ($(".startDay").val() == "") {
			alert("시작일을 입력하세요")
		} else {
			$("#scheduleInsert").submit();
		}
	}
</script>
</head>
<body>
	<div>
		<form action="scheduleInsert" method="post" id="scheduleInsert">

			일정 제목<br><input type="text" id="title" name="title"><br> <br>
			
			일정 내용<br><textarea rows="20" cols="50" name="content"></textarea><br><br>
			<div id="date">
			시작일자<br> 
			<label>연도</label><input type="text" name="startYear">
			<label>월</label><input type="text" name="startMonth"> 
			<label>일</label><input type="text" name="startDay"> 
			<label>시</label><input type="text"name="startHours"> 
			<label>분</label><input type="text" name="startMinutes"><br><br>
			
			종료일자<br> 
			<label>연도</label><input type="text" name="endYear"> 
			<label>월</label><input type="text" name="endMonth"> 
			<label>일</label><input type="text" name="endDay">
			<label>시</label><input type="text" name="endHours"> 
			<label>분</label><input type="text" name="endMinutes"><br> 
			</div>
			<input type="button" value="작성 완료" onclick="scheduleInsert()">
		</form>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>일정 작성</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
	function scheduleInsert(){

	if($("#title").val() == ""){
		alert("제목을 입력하세요")	
	}else if($("#content").val() == ""){
		alert("내용을 입력하세요")
	}else if($(".startYear").val() == ""){
		alert("시작연도를 입력하세요")
	}else if($(".startMonth").val() == ""){
		alert("시작월을 입력하세요")
	}else if($(".startDay").val() == ""){
		alert("시작일을 입력하세요")
	}else{
		$("#scheduleInsert").submit();
	} 
}
</script>
</head>
<body>
	<div>
		<form action="scheduleInsert" method="post" id="scheduleInsert"> 
			<table>
				<tr>
					<td>제목</td>
					<td><input type="text" id="title" name="title"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea rows="20" cols="50" name="content"></textarea></td>
				</tr>
				<tr>
					<td>시작일자</td>
					<td>연도 : <input type="text" name="startYear"></td>
					<td>월 : <input type="text" name="startMonth"></td>
					<td>일 : <input type="text" name="startDay"></td>
					<td>시 : <input type="text" name="startHours"></td>
					<td>분 : <input type="text" name="startMinutes"></td>
				</tr>
				<tr>
					<td>종료일자</td>
					<td>연도 : <input type="text" name="endYear"></td>
					<td>월 : <input type="text" name="endMonth"></td>
					<td>일 : <input type="text" name="endDay"></td>
					<td>시 : <input type="text" name="endHours"></td>
					<td>분 : <input type="text" name="endMinutes"></td>
				</tr>
			</table>
			<input type="button" value="작성 완료" onclick="scheduleInsert()">
		</form>	
	</div>
</body>
</html>
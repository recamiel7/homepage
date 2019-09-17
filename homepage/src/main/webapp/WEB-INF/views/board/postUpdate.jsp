<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 수정</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" > </script>
<script type="text/javascript">
	function bulletinPost(){

		if($("#titleB").val() == ""){
			alert("제목을 입력하세요")
		}else{
			$("#bulletinPost").submit();
		} 
	}
	
	function storagePost(){

		if($("#titleS").val() == ""){
			alert("제목을 입력하세요")
		}else{
			$("#storagePost").submit();
		} 
	}
	
</script>
</head>
<body>
	<c:if test="${bulletinContent != null }">
		<form action="update" method="post" enctype="multipart/form-data" id="bulletinPost" >
			<input type="hidden" name="boardName" value="${boardName }">
			<input type="hidden" name="userId" value="${loginUser.userId }">
			<input type="hidden" name="contentNo" value="${bulletinContent.no }">

			<table>	
				<tr>
					<td>게시판 선택</td>
					<td>
						<select name="menuName" id="menuNameB">
							<option value="${bulletinContent.menu }" >${bulletinContent.menu }</option>
							<c:forEach  var="menu" items="${menuList }">
								<option value="${menu.menuName }">${menu.menuName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>			
				<tr>
					<td>제목</td>
					<td><input type="text" id="titleB" name="title" value="${bulletinContent.title }"></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input type="text" value="${bulletinContent.userId }"
						disabled="disabled">
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea rows="20" cols="80" name="content">${bulletinContent.content }</textarea></td>
				</tr>
			</table>
			<c:if test="${bulletinContent.fileName != null }">
				저장된 파일 <a name="fileName">${bulletinContent.fileName }</a><br>
			</c:if>			
			저장할 파일 <input type="file" name="file"><br>
			<input type="button" value="작성 완료" onclick="bulletinPost()">
		</form>
	</c:if>

	<c:if test="${storageContent != null }">
		<form action="update" method="post" enctype="multipart/form-data" id="storagePost" >
			<input type="hidden" name="boardName" value="${boardName }">
			<input type="hidden" name="contentNo" value="${storageContent.no }">

			<table>	
				<tr>
					<td>게시판 선택</td>
					<td>
						<select name="menuName" id="menuNameS">
							<option value="${storageContent.menu }" >${storageContent.menu }</option>
							<c:forEach  var="menu" items="${menuList }">
								<option value="${menu.menuName }">${menu.menuName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>			
				<tr>
					<td>제목</td>
					<td><input type="text" id="titleS" name="title" value="${storageContent.title }"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea rows="20" cols="80" name="content">${storageContent.content }</textarea></td>
				</tr>
			</table>
			<c:if test="${storageContent.fileName != null }">
				저장된 파일 <a name="fileName">${storageContent.fileName }</a><br>
			</c:if>			
			저장할 파일 <input type="file" name="file"><br>
			<input type="button" value="작성 완료" onclick="storagePost()">
		</form>
	</c:if>
	
	<input type="button" value="작성 취소" onclick="javascript:location.href='${boardName}'">
</body>
</html>
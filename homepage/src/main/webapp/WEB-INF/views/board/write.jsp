
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 작성</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" > </script>
<script type="text/javascript">
	function bulletinPost(){

		if($("#menuName option:selected").val() == ""){
			alert("게시판을 선택해 주세요.")
		}else if($("#title").val() == ""){
			alert("제목을 입력하세요")
		}else{
			$("#bulletinPost").submit();
		} 
	}
	
</script>
</head>
<body>
	<%
		String boardName = request.getParameter("boardName");
	%>

	
	<c:if test='<%=boardName.equals("bulletinBoard")%>'>
		<form action="write" method="post" id="bulletinPost">
			<input type="hidden" name="boardName" value="bulletinBoard">
			<input type="hidden" name="userId" value="${loginUser.userId }">

			<table>	
				<tr>
					<td>게시판 선택</td>
					<td>
						<select name="menuName" id="menuName">
							<option value="" >게시판을 선택해 주세요.</option>
							<c:forEach  var="menu" items="${menuList }">
								<option value="${menu.menuName }">${menu.menuName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>			
				<tr>
					<td>제목</td>
					<td><input type="text" id="title" name="title"></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input type="text" value="${loginUser.userId }"
						disabled="disabled">
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea rows="20" cols="80" name="content"></textarea></td>
				</tr>
			</table>
			<input type="button" value="작성 완료" onclick="bulletinPost()">
		</form>
	</c:if>

	<c:if test='<%=boardName.equals("storageBoard")%>'>
		storage
	</c:if>
	
	<input type="button" value="작성 취소" onclick="javascript:location.href='bulletinBoard'">
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>자유 게시판</title>
<link href="assets/css/main.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">

	function menuInsert() {
		if ($("#menuName").val() == "") {
			alert("메뉴 이름을 입력하세요");
		} else {
			$.post("menuInsert",

			{
				"boardName" : $('#boardName').val(),
				"menuName" : $('#menuName').val()
			},

			function(data, status) {
				if (status == "success") {
					alert("메뉴 추가 성공");
				} else {
					alert("메뉴 추가 실패");
				}
			}

			);
		}
	}
	
	function commentInsert(){
		if($("#comment").val() == ""){
			alert("댓글을 입력하세요");
		}else{
			$.post("commentInsert",

					{
						"boardName" : $('#boardName').val(),
						"userId" : $('#commentUserId').val(),
						"comment" : $('#comment').val(),
						"contentNo" : $('#no').val(),
					},

					function(data, status) {
						if (status == "success") {
							alert("댓글 추가 성공");
						} else {
							alert("댓글 추가 실패");
						}
					}

			);
		}
	}
	
	function changeBoardList(menuName) {
		var form = document.createElement('form');
		var objs;
		objs = document.createElement('input');
		objs.setAttribute('type', 'text');
		objs.setAttribute('name', 'menuName');
		objs.setAttribute('value', menuName);
		form.appendChild(objs);
		form.setAttribute('method', 'get');
		form.setAttribute('action', $('#boardName').val());
		document.body.appendChild(form);
		form.submit();
	}

</script>
</head>
<body>
	<!-- main_body top -->
	<div id="main_body">
		<!-- container top -->
		<div id="board_container">
			<input type="hidden" id="boardName" value="bulletinBoard">

			<!-- 게시판 종류 -->
			<div id="board_aside">
				<ul>
					<li><a onclick="javascript:location.href='bulletinBoard'">전체글보기</a></li>
					<c:forEach var="boardMenu" items="${menuList }">
						<li><a onclick="changeBoardList('${boardMenu.menuName }')">${boardMenu.menuName }</a></li>
					</c:forEach>
				</ul>
				<c:if test="${sessionScope.checkAdmin }">

					<input type="text" id="menuName">
					<input type="button" value="추가" onclick="menuInsert()">

				</c:if>
			</div>

			<!-- 게시글 -->
			<div id="board_content">
				<c:if test="${type == null }">
					<jsp:include page="/WEB-INF/views/board/bulletinPostList.jsp" flush="false">
						<jsp:param value="" name=""/>
					</jsp:include>
				</c:if>
				<c:if test="${type != null }">
					<jsp:include page="/WEB-INF/views/board/bulletinPost.jsp" flush="false">
						<jsp:param value="" name=""/>
					</jsp:include>
				</c:if>
			</div>
		</div>
		<!-- container end -->
	</div>
	<!-- main_body end -->
</body>
</html>

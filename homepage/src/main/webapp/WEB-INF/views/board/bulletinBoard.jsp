<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>자유 게시판</title>
<link href="assets/css/main.css" rel="stylesheet"/> 
<script src="https://code.jquery.com/jquery-3.4.1.min.js" > </script>
<script type="text/javascript">
	
	$(document).ready(function(){
		$("#postContent").hide();
	})
		
	function menuInsert(){
		if($("#menuName").val()==""){
			alert("메뉴 이름을 입력하세요");
		} else{
			$.post(
					"menuInsert",
					
					{	"boardName" : $('#boardName').val(),
						"menuName" : $('#menuName').val()
					},
					
					function(data,status){
						if(status == "success"){
							alert("메뉴 추가 성공");
						} else{
							alert("메뉴 추가 실패");
						}
					}

				);  
		}
	}
	
	function content(no){
		
		$.post(
			"bulletinContent",
			
			{"no" : no},
			
			function(data,status){
				if(status == "success"){
					console.log("게시글 읽어오기 성공");
					$("#postContent").show();
					$("#postList").hide();
				} else{
					alert("게시글 읽어오기 실패");
				}
			}
		);		
	}
	
	function changeBoardList(menuName){		
		var form = document.createElement('form');
		var objs;
		objs = document.createElement('input'); 
		objs.setAttribute('type', 'text');
		objs.setAttribute('name', 'menuName'); 
		objs.setAttribute('value', menuName); 
		form.appendChild(objs);
		form.setAttribute('method', 'post'); 
		form.setAttribute('action', "bulletinBoard"); 
		document.body.appendChild(form);
		form.submit();		
	}

</script>
</head>
<body>
	<!-- main_body top -->
	<div id="main_body">
				
		<div id="button_box">
				<ul id="member_button">		
					<c:if test="${sessionScope.loginUser !=null }">
						${sessionScope.loginUser.getUserId() } 회원님 환영합니다.
					</c:if>
							
					<li>
						<a href="./home"> 
							<span class="login_txt">홈으로</span>
						</a>
					</li>	
					
					<c:if test="${sessionScope.loginUser ==null }">
						<li>
							<a href="./loginForm"> 
								<span class="login_txt">로그인</span>
							</a>
						</li>
						<li>
							<a href="./signUp"> 
								<span class="sign_up_txt">회원가입</span>
							</a>
						</li>
					</c:if>
						
					<c:if test="${sessionScope.loginUser !=null }">
						<li>
							<a href="./logout"> 
								<span class="logout_txt">로그아웃</span>
							</a>
						</li>   
					</c:if>			
					
				</ul>
		</div>
		
		<!-- header top -->
		<div id="header">
			<h1>리카미엘의 홈페이지</h1>
		</div>
		<!-- header end -->
		
		<!-- nav top -->
		<div id="nav">
			<ul id="main_menu">
				<li>
					<a href="./schedule"> 
						<span class="schedule_txt">일정 관리</span>
					</a>
				</li>
				<li>
					<a href="./storageBoard"> 
						<span class="storage_board_txt">소스 관리 게시판</span>
					</a>
				</li>
				<li>
					<a href="./bulletinBoard"> 
						<span class="bulletin_board_txt">자유게시판</span>
					</a>
				</li>
			</ul>
			
		</div>
		<!-- nav end -->
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
				<!-- 게시글 리스트 -->
				<div id="postList">
					<table id="list_table">
						<thead>
							<tr>
								<th width="5%">No</th>
								<th width="45%">제목</th>
								<th width="15%">작성자</th>
								<th width="25%">올린날</th>
							</tr>
						</thead>
					
						<c:forEach var="board" items="${boardList }">
						<tbody>
							<tr>
								<td align="center">${board.no } </td>
								<td align="center"><a onclick="content('${board.no }')">${board.title }</a></td>
								<td align="center">${board.userId }</td>
								<td align="center">${board.regdate }</td>
							</tr>
						</tbody>
						</c:forEach>
					</table>
					<hr>
					<c:if test="${sessionScope.loginUser !=null }">
						<input type="button" value="글 작성하기" onclick="javascript:location.href='write?boardName=bulletinBoard'">
					</c:if>
				</div>
				
				<!-- 게시글 내용 -->
				<div id="postContent" >
					<c:if test="${boardContent != null }">
						<input type="hidden" id="no" value="${boardContent.no }">
						<div>
							<b>${boardContent.title }</b> | <span>${boardContent.menu }</span>
							<span>${boardContent.regdate }</span>
						</div>
						<hr>
						<div>
							<b>${boardContent.userId }</b>
						</div><br>
						<div>
							<span>${boardContent.content }</span>
						</div><br><hr>
						<input type="button" value="목록으로" onclick="javascript:location.href='bulletinBoard'">
						<c:if test="">
							<input type="button" value="수정" onclick="">
						</c:if>
					</c:if>
					<c:if test="${boardContent == null }">
						<div>
						삭제되었거나 없는 글입니다.
						<input type="button" value="목록으로" onclick="javascript:location.href='bulletinBoard'">
						</div>										
					</c:if>	
				</div>						
			</div>
		</div>
		<!-- container end -->
	</div>
	<!-- main_body end -->
</body>
</html>

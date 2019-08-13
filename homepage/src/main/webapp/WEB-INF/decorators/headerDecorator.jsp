<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title default="JLStory" /></title>
<decorator:head />
</head>
<body>
	<div id="header_deco">
		<div id="button_box">
			<ul id="member_button">
				<c:if test="${sessionScope.loginUser !=null }">
						${sessionScope.loginUser.getUserId() } 회원님 환영합니다.
					</c:if>

				<li><a href="./home"> <span class="login_txt">홈으로</span>
				</a></li>

				<c:if test="${sessionScope.loginUser ==null }">
					<li><a href="./loginForm"> <span class="login_txt">로그인</span>
					</a></li>
					<li><a href="./signUp"> <span class="sign_up_txt">회원가입</span>
					</a></li>
				</c:if>

				<c:if test="${sessionScope.loginUser !=null }">
					<li><a href="./logout"> <span class="logout_txt">로그아웃</span>
					</a></li>
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
	</div>
	<decorator:body>
	</decorator:body>
</body>
</html>
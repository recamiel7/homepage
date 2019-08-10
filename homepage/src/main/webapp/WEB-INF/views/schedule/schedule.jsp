<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>일정 관리</title>
<link href="assets/css/main.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<link href='assets/css/fullcalendar.core.css' rel='stylesheet' />
<link href='assets/css/fullcalendar.daygrid.css' rel='stylesheet' />
<script src='assets/js/fullcalendar.core.js'></script>
<script src='assets/js/fullcalendar.interaction.js'></script>
<script src='assets/js/fullcalendar.daygrid.js'></script>
<script src='assets/js/fullcalendar.locales-all.js'></script>

<script>

  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
  			    
    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction', 'dayGrid' ],
      defaultDate: new Date(),
      editable: true,
      eventLimit: true, // allow "more" link when too many events
      events: dataset
    });

    calendar.render();
    calendar.setOption('locale', "ko");

  });

  var dataset = [
	    <c:forEach var="list" items="${scheduleList}" varStatus="status">
	        <c:if test="${list.startDate != ''}">
	            {"title":'<c:out value="${list.title}" />'
	            ,"start":"<c:out value="${list.startDate}" />"
	            <c:if test="${listview.tsenddate != ''}">
	                ,"end":"<c:out value="${list.endDate}" />"
	            </c:if>
	            } <c:if test="${!status.last}">,</c:if>
	        </c:if>
	    </c:forEach>
	];
  
</script>
<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #calendar {
    max-width: 900px;
    margin: 0 auto;
  }

</style>

</head>
<body>
	<!-- main_body top -->
	<div id="main_body">

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
				<li><a href="./schedule"> <span class="schedule_txt">일정
							관리</span>
				</a></li>
				<li><a href="./storageBoard"> <span
						class="storage_board_txt">소스 관리 게시판</span>
				</a></li>
				<li><a href="./bulletinBoard"> <span
						class="bulletin_board_txt">자유게시판</span>
				</a></li>
			</ul>

		</div>
		<!-- nav end -->
		<!-- container top -->
		<div id="schedule_container">
			<div id="calendar"></div>
			<c:if test="${sessionScope.checkAdmin }">
				<input type="button" value="일정 추가" onclick="javascript:location.href='scheduleInsert'">
			</c:if>
		</div>
		<!-- container end -->
	</div>
	<!-- main_body end -->
</body>
</html>
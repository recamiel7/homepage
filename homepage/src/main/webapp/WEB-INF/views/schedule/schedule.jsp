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
    calendar.setOption('locale', "ko"); //언어 한국어로 설정

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
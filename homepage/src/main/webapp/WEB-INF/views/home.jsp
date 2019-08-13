<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>리카미엘의 홈페이지</title>
<script type="text/javascript"
	src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=1glvhjzvq5">
</script>
<link href="assets/css/main.css" rel="stylesheet"/> 
</head>
<body>
	<!-- main_body top -->
	<div id="main_body">
		
		<!-- container top -->
		<div id="main_container">
			<p>전남과 광주에서 취업하고 싶은 취업준비생입니다.</p> <br>

			<div id="map"></div>
			 
			<p>전남 나주시 죽림길 71 삼성아파트 104동 704호</p>
			<script>
				var mapOptions = {
    				center: new naver.maps.LatLng(35.032130, 126.725731),
  					zoom: 11
				};
	
				var map = new naver.maps.Map('map', mapOptions);
	
				var markerOptions = {
				    position: new naver.maps.LatLng(35.032130, 126.725731),
				    map: map
				};
	
				var marker = new naver.maps.Marker(markerOptions);
			</script>
		</div>
		<!-- container end -->
	</div>
	<!-- main_body end -->
</body>
</html>
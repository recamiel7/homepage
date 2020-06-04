<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 게시글 리스트 -->
<div id="postList">
	<table id="list_table">
		<c:if test="${menuName != null }">
			<h2>${menuName }</h2>
			<hr>
		</c:if>
		<thead>
			<tr>
				<th width="10%">No</th>
				<th width="50%">제목</th>
				<th width="15%">작성자</th>
				<th width="25%">올린날</th>
			</tr>
		</thead>

		<c:forEach var="board" items="${boardList }">
			<tbody>
				<tr>
					<td align="center">${board.no }</td>
					<td align="center"><a
						onclick="javascript:location.href='storageBoard?type=content&contentNo=${board.no}&menuName=${menuName }'">${board.title }</a></td>
					<td align="center">관리자</td>
					<td align="center">${board.regdate }</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
	<div align="center">
		<c:forEach items="${pageStartList}" var="pageIndex" varStatus="status">
			<a href="storageBoard?start=${pageIndex}">${status.index +1 }</a>&nbsp; &nbsp;
		</c:forEach>
	</div>
	<hr>
	<c:if test="${sessionScope.checkAdmin }">
		<input type="button" value="글 작성하기"
			onclick="javascript:location.href='write?boardName=storageBoard'">
	</c:if>
</div>

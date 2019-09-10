<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

				<!-- 게시글 리스트 -->
				<div id="postList">
					<table id="list_table">
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
									<td align="center"><a onclick="javascript:location.href='storageBoard?type=content&contentNo=${board.no}&menuName=${menuName }'">${board.title }</a></td>
									<td align="center">관리자</td>
									<td align="center">${board.regdate }</td>
								</tr>
							</tbody>
						</c:forEach>
					</table>
					<hr>
					<c:if test="${sessionScope.checkAdmin }">
						<input type="button" value="글 작성하기"
							onclick="javascript:location.href='write?boardName=storageBoard'">
					</c:if>
				</div>
    
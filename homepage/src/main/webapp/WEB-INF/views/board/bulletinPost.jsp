<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
				<!-- 게시글 내용 -->
				<div id="postContent">
					<c:if test="${boardContentB != null }">
						<input type="hidden" id="no" value="${boardContentB.no }">
						<div>
							<b>${boardContentB.title }</b> | <span>${boardContentB.menu }</span>
							<span>${boardContentB.regdate }</span>
						</div>
						<hr>
						<div>
							<b>${boardContentB.userId }</b>
						</div>
						<br>
						<div>
							<pre>${boardContentB.content }</pre>
						</div>
						<br>
							<c:if test="${boardContentB.fileName != null }">
								파일 다운로드 <a href="<c:url value='/download?boardName=bulletinBoard&no=${boardContentB.no }'/>">${boardContentB.fileName}</a>
							</c:if>
						<hr>
						<input type="button" value="목록으로"
							onclick="javascript:location.href='bulletinBoard?menuName=${menuName}'">
						<c:if test="${loginUser.userId.equals(boardContentB.userId) }">
							<input type="button" value="수정" onclick="javascript:location.href='update?boardName=bulletinBoard&contentNo=${boardContentB.no }'">
							<input type="button" value="삭제" onclick="javascript:location.href='delete?boardName=bulletinBoard&contentNo=${boardContentB.no }'">
						</c:if>
						<hr>
						
						<!-- 게시글 댓글영역 -->
						<c:if test="${commentListB != null}">
							<c:forEach items="${commentListB }" var="CList">
									<tbody>
									<tr>
										<td><img src="getImage?id=${CList.userId }" width="20" height="20"></td>
										<td align="center">
											<b>${CList.userId }</b>
											<pre>${CList.comment }</pre>
										</td>
									</tr>
								</tbody>						
							</c:forEach>
						</c:if>
						<c:if test="${loginUser != null }">
							<div>
								<input type="hidden" id="commentUserId" value="${loginUser.userId }">
								<textarea rows="5" cols="80" name="comment" id="comment"></textarea>
								<input type="button" value="댓글 등록" onclick="commentInsert()">
							</div>
						</c:if>
					</c:if>
					<c:if test="${boardContentB == null }">
						<div>
							삭제되었거나 없는 글입니다. 
							<input type="button" value="목록으로" onclick="javascript:location.href='bulletinBoard?menuName=${menuName}'">
						</div>
					</c:if>
				</div>
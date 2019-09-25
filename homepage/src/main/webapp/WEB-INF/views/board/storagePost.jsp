<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

				<!-- 게시글 내용 -->
				<div id="postContent">
				
					<!-- 게시글이 있을 경우 -->
					<c:if test="${boardContentS != null }">
					
						<!-- 게시글 본문 -->
						<input type="hidden" id="no" value="${boardContentS.no }">
						<div>
							<b>${boardContentS.title }</b> | <span>${boardContentS.menuName }</span>
							<span>${boardContentS.regdate }</span>
						</div>
						<hr>
						<div>
							<b>관리자</b>
						</div>
						<br>
						<div>
							<pre>${boardContentS.content }</pre>
						</div>
						<br>
							<c:if test="${boardContentS.fileName != null }">
								파일 다운로드 <a href="<c:url value='/download?boardName=storageBoard&no=${boardContentS.no }'/>">${boardContentS.fileName}</a>
							</c:if>
						<hr>
						<input type="button" value="목록으로"
							onclick="javascript:location.href='storageBoard?menuName=${menuName}'">
						<c:if test="${sessionScope.checkAdmin }">
							<input type="button" value="수정" onclick="javascript:location.href='update?boardName=storageBoard&contentNo=${boardContentS.no }'">
							<input type="button" value="삭제" onclick="javascript:location.href='delete?boardName=storageBoard&contentNo=${boardContentS.no }'">
						</c:if>
						<hr>
						
						<!-- 게시글 댓글영역 -->
						<c:if test="${commentListS != null}">
							<c:forEach items="${commentListS }" var="CList">
									<tbody>
									<tr>
										<td><img src="getImage?id=${CList.userId }" width="20" height="20"></td>
										<td align="center">
											<b>${CList.userId }</b>
											<pre>${CList.comment }</pre><br>
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
					
					<!-- 게시글이 존재하지 않을 경우 -->
					<c:if test="${boardContentS == null }">
						<div>
							삭제되었거나 없는 글입니다. 
							<input type="button" value="목록으로" onclick="javascript:location.href='storageBoard?menuName=${menuName}'">
						</div>
					</c:if>
				</div>
package kr.or.connect.homepage.dto;

public class Comment {
	private String boardName, userId, comment;
	private int no, contentNo;
	
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getContentNo() {
		return contentNo;
	}
	public void setContentNo(int contentNo) {
		this.contentNo = contentNo;
	}
	@Override
	public String toString() {
		return "Comment [boardName=" + boardName + ", userId=" + userId + ", contentNo="+ contentNo + "]";
	}		
}

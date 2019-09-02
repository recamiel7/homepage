package kr.or.connect.homepage.dao;

public class CommentDaoSqls {
	public static final String COMMENT_SELECT_ALL = "SELECT * FROM comment order by no";
	public static final String COMMENT_SELECT_BY_CONTENT_NO = "SELECT * FROM comment WHERE board_name =:boardName and content_no = :contentNo order by no";
	public static final String COMMENT_UPDATE = "UPDATE comment SET comment = :comment WHERE no = :no";
	public static final String COMMENT_SELECT_BY_NO = "SELECT * FROM comment WHERE no = :no";
	public static final String COMMENT_DELETE_BY_NO = "DELETE FROM comment WHERE no = :no";
}

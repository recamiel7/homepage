package kr.or.connect.homepage.dao;

public class ScheduleDaoSqls {
	public static final String SCHEDULE_SELECT_ALL = "SELECT * FROM schedule order by no";
	public static final String SCHEDULE_UPDATE = "UPDATE schedule SET password = :password WHERE no = :no";
	public static final String SCHEDULE_SELECT_BY_NO = "SELECT * FROM schedule WHERE no = :no";
	public static final String SCHEDULE_DELETE_BY_NO = "DELETE FROM schedule WHERE no = :no";
}

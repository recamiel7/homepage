package kr.or.connect.homepage.dao;

public class UserDaoSqls {
	public static final String SELECT_ALL = "SELECT * FROM user order by user_id";
	public static final String UPDATE = "UPDATE user SET password = :password WHERE user_id = :userId";
	public static final String SELECT_BY_USER_ID = "SELECT * FROM user where user_id = :userId";
	public static final String DELETE_BY_USER_ID = "DELETE FROM user WHERE user_id = :userId";
}

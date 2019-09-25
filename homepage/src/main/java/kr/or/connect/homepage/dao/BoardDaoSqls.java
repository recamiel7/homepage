package kr.or.connect.homepage.dao;

public class BoardDaoSqls {
	
	//board menu sqls
	public static final String MENU_SELECT_ALL = "SELECT * FROM board_menu order by no";
	public static final String BOARD_MENU_SELECT_ALL = "SELECT * FROM board_menu WHERE board_name = :boardName order by no";
	public static final String MENU_UPDATE = "UPDATE board_menu SET menu_name = :menuName WHERE no = :no";
	public static final String DELETE_MENU_BY_NO = "DELETE FROM board_menu WHERE no = :no";
	
	//bulletin board sqls
	public static final String BULLETIN_SELECT_ALL = "SELECT * FROM bulletin order by no";
	public static final String BULLETIN_SELECT_ALL_PAGING = "SELECT * FROM bulletin order by no DESC limit :start, :limit";
	public static final String BULLETIN_SELECT_BY_MENU_NAME = "SELECT * FROM bulletin WHERE menu_name = :menuName order by no";
	public static final String BULLETIN_SELECT_BY_MENU_NAME_PAGING = "SELECT * FROM bulletin WHERE menu_name = :menuName order by no DESC limit :start, :limit";
	public static final String BULLETIN_UPDATE = "UPDATE bulletin SET title = :title, menu_name = :menuName, content = :content WHERE no = :no";
	public static final String BULLETIN_UPDATE_WITH_FILE = "UPDATE bulletin SET title = :title, menu_name = :menuName,content = :content, "
			                             + "file_name = :fileName, file_path = :filePath, file_type = :fileType WHERE no = :no";
	public static final String BULLETIN_SELECT_BY_NO = "SELECT * FROM bulletin WHERE no = :no";
	public static final String BULLETIN_DELETE_BY_NO = "DELETE FROM bulletin WHERE no = :no";
	public static final String BULLETIN_SELECT_COUNT = "SELECT count(*) FROM bulletin";
	public static final String BULLETIN_SELECT_COUNT_BY_MENU_NAME = "SELECT count(*) FROM bulletin WHERE menu_name = :menuName";
	
	//storage board sqls
	public static final String STORAGE_SELECT_ALL = "SELECT * FROM storage order by no";
	public static final String STORAGE_SELECT_ALL_PAGING = "SELECT * FROM storage order by no DESC limit :start, :limit";
	public static final String STORAGE_SELECT_BY_MENU_NAME = "SELECT * FROM storage WHERE menu_name = :menuName order by no";
	public static final String STORAGE_SELECT_BY_MENU_NAME_PAGING = "SELECT * FROM storage WHERE menu_name = :menuName order by no DESC limit :start, :limit";
	public static final String STORAGE_UPDATE = "UPDATE storage SET title = :title, menu_name = :menuName, content = :content WHERE no = :no";
	public static final String STORAGE_UPDATE_WITH_FILE = "UPDATE storage SET title = :title, menu_name = :menuName, content = :content, "
			                             + "file_name = :fileName, file_path = :filePath, file_type = :fileType WHERE no = :no";
	public static final String STORAGE_SELECT_BY_NO = "SELECT * FROM storage WHERE no = :no";
	public static final String STORAGE_DELETE_BY_NO = "DELETE FROM storage WHERE no = :no";
	public static final String STORAGE_SELECT_COUNT = "SELECT count(*) FROM storage";
	public static final String STORAGE_SELECT_COUNT_BY_MENU_NAME  = "SELECT count(*) FROM storage WHERE menu_name = :menuName";
}

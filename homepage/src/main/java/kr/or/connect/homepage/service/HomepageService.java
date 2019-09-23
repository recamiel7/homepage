package kr.or.connect.homepage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.homepage.dto.BoardMenu;
import kr.or.connect.homepage.dto.Bulletin;
import kr.or.connect.homepage.dto.Comment;
import kr.or.connect.homepage.dto.Schedule;
import kr.or.connect.homepage.dto.Storage;

public interface HomepageService {
	public static final Integer LIMIT = 10;
	
/*---------------- 일정 관리 ---------------------------*/	
	public void insertSchedule(HttpServletRequest request);
	public List<Schedule> getScheduleList();
	
/*---------------- 자유 게시판 ---------------------------*/
	public void insertBulletin(HttpServletRequest request);
	public void insertBulletinWithFile(HttpServletRequest request, String fileName, String filePath, String fileType);
	
	public Bulletin getBulletinContentByNo(int no);
	public List<Bulletin> getBulletinContentList();
	public List<Bulletin> getBulletinContentListByMenuName(String menuName);
	
	public void updateBulletin(Bulletin bulletin);
	public void updateBulletinWithFile(Bulletin bulletin);
	
	public void deleteBulletin(int no);
	
	public int getBulletinCount();
	
/*---------------- 소스관리 게시판 ---------------------------*/
	public void insertStorage(HttpServletRequest request);
	public void insertStorageWithFile(HttpServletRequest request, String fileName, String filePath, String fileType);
	
	public Storage getStorageContentByNo(int no);
	public List<Storage> getStorageContentList();
	public List<Storage> getStorageContentListByMenuName(String menuName);
	
	public void updateStorage(Storage storage);
	public void updateStorageWithFile(Storage storage);
	
	public void deleteStorgae(int no);
	
	public int getStorageCount();
	
/*---------------- 메뉴 관련 ---------------------------*/
	public void insertBoardMenu(BoardMenu boardMenu);
	public List<BoardMenu> getBoardMenuList(String boardName);
	
/*---------------- 댓글 관련 ---------------------------*/	
	public void insertComment(Comment comment);
	public List<Comment> getCommentList(String boardName, String no);
		
/*---------------- 파일 관련 ---------------------------*/	
	public void fileUpload(MultipartFile file, String filePath);
	public void fileDownload(String fileName, String filePath, String fileType, HttpServletResponse response);
}

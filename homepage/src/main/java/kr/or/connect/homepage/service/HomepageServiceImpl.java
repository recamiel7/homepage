package kr.or.connect.homepage.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.homepage.dao.BoardMenuDao;
import kr.or.connect.homepage.dao.BulletinBoardDao;
import kr.or.connect.homepage.dao.CommentDao;
import kr.or.connect.homepage.dao.ScheduleDao;
import kr.or.connect.homepage.dao.StorageBoardDao;
import kr.or.connect.homepage.dto.BoardMenu;
import kr.or.connect.homepage.dto.Bulletin;
import kr.or.connect.homepage.dto.Comment;
import kr.or.connect.homepage.dto.Schedule;
import kr.or.connect.homepage.dto.Storage;

@Service
public class HomepageServiceImpl implements HomepageService {

/*---------------- 일정 관리 ---------------------------*/
	@Autowired
	ScheduleDao scheduleDao;
	
	@Override
	public void insertSchedule(HttpServletRequest request) {
		scheduleDao.requestInsert(request);
	}

	@Override
	public List<Schedule> getScheduleList() {
		return scheduleDao.selectAll();
	}

	/*---------------- 자유 게시판 ---------------------------*/
	@Autowired
	BulletinBoardDao bulletinBoardDao;
		
	@Override
	public void insertBulletin(HttpServletRequest request) {
		bulletinBoardDao.requestInsert(request);
	}	
	@Override
	public void insertBulletinWithFile(HttpServletRequest request, String fileName, String filePath, String fileType) {
		bulletinBoardDao.requestInsert(request, fileName, filePath, fileType);
	}
	
	@Override
	public void updateBulletin(Bulletin bulletin) {
		bulletinBoardDao.update(bulletin);
	}
	@Override
	public void updateBulletinWithFile(Bulletin bulletin) {
		bulletinBoardDao.updateWithFile(bulletin);
	}
		

	@Override
	public void deleteBulletin(int no) {
		bulletinBoardDao.deleteByNo(no);
	}

	@Override
	public Bulletin getBulletinContentByNo(int no){
		return bulletinBoardDao.selectByNo(no);
	}	
	@Override
	public List<Bulletin> getBulletinContentList() {
		return bulletinBoardDao.selectAll();
	}
	@Override
	public List<Bulletin> getBulletinContentListByMenuName(String menuName) {
		return bulletinBoardDao.boardMenuSelectByMenuName(menuName);
	}
	@Override
	public int getBulletinCount() {
		return bulletinBoardDao.selectCount();
	}

/*---------------- 소스관리 게시판 ---------------------------*/
	@Autowired
	StorageBoardDao storageBoardDao;
	
	@Override
	public void insertStorage(HttpServletRequest request) {
		storageBoardDao.requestInsert(request);
	}
	@Override
	public void insertStorageWithFile(HttpServletRequest request, String fileName, String filePath, String fileType) {
		storageBoardDao.requestInsert(request, fileName, filePath, fileType);
	}	
	
	@Override
	public void updateStorage(Storage storage) {
		storageBoardDao.update(storage);
	}
	@Override
	public void updateStorageWithFile(Storage storage) {
		storageBoardDao.updateWithFile(storage);
	}
	
	@Override
	public void deleteStorgae(int no) {
		storageBoardDao.deleteByNo(no);
	}

	@Override
	public Storage getStorageContentByNo(int no) {
		return storageBoardDao.selectByNo(no);
	}		
	@Override
	public List<Storage> getStorageContentList() {
		return storageBoardDao.selectAll();
	}
	@Override
	public List<Storage> getStorageContentListByMenuName(String menuName) {
		return storageBoardDao.boardMenuSelectByMenuName(menuName);
	}
	@Override
	public int getStorageCount(){
		return storageBoardDao.selectCount();
	}

/*---------------- 메뉴 관련 ---------------------------*/
	@Autowired
	BoardMenuDao boardMenuDao;
	
	@Override
	public void insertBoardMenu(BoardMenu boardMenu) {
		boardMenuDao.insert(boardMenu);
	}
	
	@Override
	public List<BoardMenu> getBoardMenuList(String boardName) {		
		return boardMenuDao.boardMenuSelectAll(boardName);
	}

/*---------------- 댓글 관련 ---------------------------*/	
	@Autowired
	CommentDao commentDao;	
	
	@Override
	public void insertComment(Comment comment) {
		commentDao.insert(comment);
	}

	@Override
	public List<Comment> getCommentList(String boardName, String no) {
		return commentDao.selectByContentNo(boardName, no);
	}
		
/*---------------- 댓글 관련 ---------------------------*/
	@Override
	public void fileUpload(MultipartFile file, String filePath) {
		try (FileOutputStream fos = new FileOutputStream(filePath); InputStream is = file.getInputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");
		}	
	}

	@Override
	public void fileDownload(String fileName, String filePath, String fileType, HttpServletResponse response) {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", fileType);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		try (FileInputStream fis = new FileInputStream(filePath); OutputStream out = response.getOutputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = fis.read(buffer)) != -1) {
				out.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Download Error");
		}
	}
	
	
}

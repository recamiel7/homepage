package kr.or.connect.homepage.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import kr.or.connect.homepage.config.ApplicationConfig;

import kr.or.connect.homepage.service.HomepageServiceImpl;

@Controller
public class HomepageController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*@Autowired 
	HomepageService HomepageService;*/


	@GetMapping(path = "/home")
	public String home(HttpSession session) {
		return "home";
	}

	
/*---------------    일정관리      ---------------*/
	
	@GetMapping(path = "/schedule")
	public String schedule(Model model) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		ScheduleDao scheduleDao = ac.getBean(ScheduleDao.class);
		
		List<Schedule> scheduleList = scheduleDao.selectAll();
		
		model.addAttribute("scheduleList",scheduleList);
		
		return "schedule/schedule";
	}

	//일정 작성용 페이지
	@GetMapping(path = "/scheduleInsert")
	public String scheduleInsertPage() {
		return "schedule/scheduleInsert";
	}
	
	@PostMapping(path = "/scheduleInsert")
	public String scheduleInsert(Model model, HttpServletRequest request) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		ScheduleDao scheduleDao = ac.getBean(ScheduleDao.class);
		scheduleDao.requestInsert(request);
		
		List<Schedule> scheduleList = scheduleDao.selectAll();
		
		model.addAttribute("scheduleList",scheduleList);
		
		return "schedule/schedule";
	}
	

/*---------------    소스관리게시판      ---------------*/
	
	@GetMapping(path = "/storageBoard")
	public String storage(@RequestParam(required= false, name="menuName") String menuName, 
			@RequestParam(required= false, name="contentNo") String contentNo, 
			@RequestParam(required= false, name="type") String type,
			Model model, HttpSession session) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);
		StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
		
		try {
			List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll("storageBoard");
			model.addAttribute("menuList", menuList);
			if (menuName != null) {
				List<Storage> changeBoardList = storageBoardDao.boardMenuSelectByMenuName(menuName);
				model.addAttribute("boardList", changeBoardList);
				model.addAttribute("menuName",menuName);
			} else {
				List<Storage> boardLsit = storageBoardDao.selectAll();
				model.addAttribute("boardList", boardLsit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(type!=null){
			model.addAttribute("type", "content");
			getContent("storageBoard",contentNo,session);
		}

		return "board/storageBoard";
	}

/*---------------    자유게시판      ---------------*/
	
	@GetMapping(path = "/bulletinBoard")
	public String bulletin(@RequestParam(required= false, name="menuName") String menuName, 
			@RequestParam(required= false, name="contentNo") String contentNo, 
			@RequestParam(required= false, name="type") String type,
			Model model, HttpSession session) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);
		BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
		try {
			List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll("bulletinBoard");
			model.addAttribute("menuList", menuList);
			if(menuName != null){
				List<Bulletin> changeBoardList = bulletinBoardDao.boardMenuSelectByMenuName(menuName);
				model.addAttribute("boardList",changeBoardList);
				model.addAttribute("menuName",menuName);
			}else{
				List<Bulletin> boardLsit = bulletinBoardDao.selectAll();
				model.addAttribute("boardList", boardLsit);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(type!=null){
			model.addAttribute("type", "content");
			getContent("bulletinBoard",contentNo,session);
		}

		return "board/bulletinBoard";
	}

/*---------------    게시판 공통      ---------------*/
	//각 게시판 메뉴 추가
	@PostMapping(path = "/menuInsert")
	@ResponseBody
	public void menuInsert(@RequestParam("boardName") String boardName, @RequestParam("menuName") String menuName,
			HttpSession session) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);

		BoardMenu boardMenu = new BoardMenu();
		boardMenu.setBoardName(boardName);
		boardMenu.setMenuName(menuName);
		logger.debug("{} 게시판에 추가할 메뉴({})의 boardMenu dto 생성 및 입력 완료",boardName, menuName);

		boardMenuDao.insert(boardMenu);

		logger.debug("{} 게시판에 메뉴({}) 추가 완료",boardName, menuName);

	}
	
	//게시글 읽어오기
	public void getContent(@RequestParam("boardName") String boardName, @RequestParam("no") String no,
			HttpSession session) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		try {
			if (boardName.equals("bulletinBoard")) {
				BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
				Bulletin bulletin = bulletinBoardDao.selectByNo((Integer.valueOf(no)));
				CommentDao commentDao = ac.getBean(CommentDao.class);
				List<Comment> commentList = commentDao.selectByContentNo(boardName, Integer.toString(bulletin.getNo()));

				logger.debug("{} 게시글 읽어오기 성공",boardName);
				logger.debug("{}, 댓글 갯수 {}",boardName,commentList.size());

				session.setAttribute("boardContentB", bulletin);
				session.setAttribute("commentListB", commentList);

			}

			else{
				StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
				Storage storage = storageBoardDao.selectByNo((Integer.valueOf(no)));
				CommentDao commentDao = ac.getBean(CommentDao.class);
				List<Comment> commentList = commentDao.selectByContentNo(boardName, Integer.toString(storage.getNo()));

				logger.debug("{} 게시글 읽어오기 성공",boardName);
				logger.debug("{}, 댓글 갯수 {}",boardName,commentList.size());

				session.setAttribute("boardContentS", storage);
				session.setAttribute("commentListS", commentList);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	//게시글 작성 페이지
	@GetMapping(path = "/write")
	public String write(@RequestParam("boardName") String boardName, Model model) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);

		List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll(boardName);
		model.addAttribute("menuList", menuList);
		
		logger.debug("{} 게시글 작성 페이지", boardName);

		return "board/write";
	}

	//게시글 작성
	@PostMapping(path = "/write")
	public String postWrite(@RequestParam("boardName") String boardName, 
							@RequestParam(required= false, name="file") MultipartFile file, HttpServletRequest request) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		if(file.getSize() != 0){
			
			logger.debug("파일 이름 : {}",file.getOriginalFilename());
			logger.debug("파일 크기 : {}",file.getSize());
			
			String fileName = file.getOriginalFilename();
			String filePath;
			String fileType = file.getContentType();
			
			if (boardName.equals("bulletinBoard")) {
				filePath = "D:/tmp/bulletin/" + file.getOriginalFilename();
				BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
				bulletinBoardDao.requestInsert(request,fileName,filePath,fileType);
			}else{
				filePath = "D:/tmp/storage/" + file.getOriginalFilename();
				StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
				storageBoardDao.requestInsert(request,fileName,filePath,fileType);
			}
			
			upload(file,filePath);
	        
		}else{
			if (boardName.equals("bulletinBoard")) {
				BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
				bulletinBoardDao.requestInsert(request);
			} else {
				StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
				storageBoardDao.requestInsert(request);
			} 
		}		
		return "redirect:"+boardName;
	}
	
	//게시글 수정 페이지
	@GetMapping(path = "/update")
	public String postUpdatePage(@RequestParam("boardName") String boardName, 
			@RequestParam("contentNo") int contentNo, Model model) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);
		
		List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll(boardName);
		model.addAttribute("menuList", menuList);
		
		model.addAttribute("boardName",boardName);
		
		if (boardName.equals("bulletinBoard")) {
			BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
			Bulletin bulletinContent = bulletinBoardDao.selectByNo(contentNo);
			model.addAttribute("bulletinContent", bulletinContent);
		} else {
			StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
			Storage storageContent = storageBoardDao.selectByNo(contentNo);
			model.addAttribute("storageContent",storageContent);
		} 
		
		return "board/postUpdate";
	}
	
	//게시글 수정
	@PostMapping(path="/update")
	public String postUpdate(@RequestParam("boardName") String boardName, 
			@RequestParam("menuName") String menu,
			@RequestParam("contentNo") int contentNo, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam(required= false, name="file") MultipartFile file){
		
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		if(boardName.equals("bulletinBoard")){
			BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
			Bulletin bulletin = bulletinBoardDao.selectByNo(contentNo);
			
			bulletin.setTitle(title);
			bulletin.setContent(content);
			bulletin.setMenu(menu);
			
			if(file.getSize() == 0){
				bulletinBoardDao.update(bulletin);
			}else{
				String fileName = file.getOriginalFilename();
				String filePath = "D:/tmp/bulletin/" + file.getOriginalFilename();
				String fileType = file.getContentType();
				
				bulletin.setFileName(fileName);
				bulletin.setFilePath(filePath);
				bulletin.setFileType(fileType);
				
				upload(file, filePath);
				bulletinBoardDao.updateWithFile(bulletin);
			}
		}else{
			StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
			Storage storage = storageBoardDao.selectByNo(contentNo);
			
			if(file.getSize() == 0){
				storage.setTitle(title);
				storage.setContent(content);
				storage.setMenu(menu);
				storageBoardDao.update(storage);
			}else{
				String fileName = file.getOriginalFilename();
				String filePath = "D:/tmp/storage/" + file.getOriginalFilename();
				String fileType = file.getContentType();
				
				storage.setFileName(fileName);
				storage.setFilePath(filePath);
				storage.setFileType(fileType);
				
				upload(file, filePath);
				storageBoardDao.updateWithFile(storage);
			}
		}
		
		return "redirect:"+boardName;
	}
	
	//게시글 삭제
	@GetMapping(path="/delete")
	public String delete(@RequestParam("boardName") String boardName, 
			@RequestParam("contentNo") int contentNo){
		
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		if (boardName.equals("bulletinBoard")) {
			BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
			bulletinBoardDao.deleteByNo(contentNo);
		} else {
			StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
			storageBoardDao.deleteByNo(contentNo);
		} 
		
		return "redirect:"+boardName;
	}
	
	//파일 저장
	public void upload(MultipartFile file, String filePath){
		try(
                FileOutputStream fos = new FileOutputStream(filePath);
                InputStream is = file.getInputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = is.read(buffer)) != -1){
                fos.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
	}
	
	//파일 다운로드
	@GetMapping("/download")
	public void download(@RequestParam("boardName") String boardName, @RequestParam("no") int no, HttpServletResponse response) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		String fileName,filePath,contentType;
		
		if (boardName.equals("bulletinBoard")) {
			BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
			Bulletin bulletin = bulletinBoardDao.selectByNo(no);
			
			fileName = bulletin.getFileName();
			filePath = bulletin.getFilePath();
			contentType = bulletin.getFileType();
		} else{
			StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
			Storage storage = storageBoardDao.selectByNo(no);
			
			fileName = storage.getFileName();
			filePath = storage.getFilePath();
			contentType = storage.getFileType();
		} 
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", contentType);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		try(
                FileInputStream fis = new FileInputStream(filePath);
                OutputStream out = response.getOutputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
            		out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Download Error");
        }
		
	}
	
	
	//댓글 등록
	@PostMapping(path="/commentInsert")
	public void commentInsert(@RequestParam("boardName") String boardName, @RequestParam("contentNo") int contentNo,
							@RequestParam("userId") String userId, @RequestParam("comment") String comment){
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		CommentDao commentDao = ac.getBean(CommentDao.class);
		Comment boardComment = new Comment();
		boardComment.setBoardName(boardName);
		boardComment.setContentNo(contentNo);
		boardComment.setComment(comment);
		boardComment.setUserId(userId);
		logger.debug("{}, {}, {} 댓글 등록 완료", userId,boardName,contentNo);
		commentDao.insert(boardComment); 
				
	}
}

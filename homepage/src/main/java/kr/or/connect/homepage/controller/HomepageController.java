package kr.or.connect.homepage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.homepage.dto.BoardMenu;
import kr.or.connect.homepage.dto.Bulletin;
import kr.or.connect.homepage.dto.Comment;
import kr.or.connect.homepage.dto.Schedule;
import kr.or.connect.homepage.dto.Storage;

import kr.or.connect.homepage.service.HomepageService;

@Controller
public class HomepageController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HomepageService homepageService;

	@GetMapping(path = "/home")
	public String home(HttpSession session) {
		return "home";
	}

/*---------------    일정관리      ---------------*/

	@GetMapping(path = "/schedule")
	public String schedule(Model model) {

		List<Schedule> scheduleList = homepageService.getScheduleList();

		model.addAttribute("scheduleList", scheduleList);

		return "schedule/schedule";
	}

	// 일정 작성용 페이지
	@GetMapping(path = "/scheduleInsert")
	public String scheduleInsertPage() {
		return "schedule/scheduleInsert";
	}

	//일정 추가 작업
	@PostMapping(path = "/scheduleInsert")
	public String scheduleInsert(Model model, HttpServletRequest request) {
		homepageService.insertSchedule(request);

		return "redirect:" + "schedule";
	}

/*---------------    소스관리게시판      ---------------*/

	//소스 관리 게시판 페이지
	@GetMapping(path = "/storageBoard")
	public String storage(@RequestParam(required = false, name = "menuName") String menuName,
			@RequestParam(required = false, name = "contentNo" , defaultValue = "0") int no,
			@RequestParam(required = false, name = "type") String type, 
			@RequestParam(required = false, name = "start", defaultValue = "0") int start, 
			@RequestParam(required = false, name = "searchText") String searchText,
			Model model, HttpSession session) {

		String boardName = "storageBoard";
		int count;

		try {
			// 게시글 리스트
			List<BoardMenu> menuList = homepageService.getBoardMenuList(boardName);
			model.addAttribute("menuList", menuList);
			if (menuName != null && menuName.length() != 0) {
				List<Storage> changeBoardList = 
						homepageService.getStorageContentListByMenuName(menuName,start);
				
				count = homepageService.getStorageCount(menuName);
								
				model.addAttribute("boardList", changeBoardList);
				model.addAttribute("menuName", menuName);
			} else if(searchText != null && searchText.length() != 0){
				logger.debug("{} 검색어", searchText);
				List<Storage> searchBoardList = homepageService.getStorageContentListBySearchText(searchText,start);
				
				count = homepageService.getStorageCountBySearchText(searchText);
				
				model.addAttribute("boardList", searchBoardList);
				model.addAttribute("searchText", searchText);
				
			} else {
				List<Storage> boardLsit = homepageService.getStorageContentList(start);
				
				count = homepageService.getStorageCount();
				
				model.addAttribute("boardList", boardLsit);
			}
			
			int pageCount = count/HomepageService.LIMIT;
			if(count % HomepageService.LIMIT>0)
				pageCount++;
			
			List<Integer> pageStartList = new ArrayList<>();
			for(int i=0; i<pageCount; i++){
				pageStartList.add(i*HomepageService.LIMIT);
			}
			
			model.addAttribute("pageStartList", pageStartList);

			// 게시글 내용 표시
			if (type != null) {
				model.addAttribute("type", "content");
				Storage storage = homepageService.getStorageContentByNo(no);
				List<Comment> commentList = homepageService.getCommentList(boardName, no);

				logger.debug("{} 게시글 읽어오기 성공", boardName);
				logger.debug("{}, 댓글 갯수 {}", boardName, commentList.size());

				model.addAttribute("boardContentS", storage);
				model.addAttribute("commentListS", commentList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "board/storageBoard";
	}

/*---------------    자유게시판      ---------------*/

	//자유 게시판 페이지
	@GetMapping(path = "/bulletinBoard")
	public String bulletin(@RequestParam(required = false, name = "menuName") String menuName,
			@RequestParam(required = false, name = "contentNo" , defaultValue = "0") int no,
			@RequestParam(required = false, name = "type") String type,
			@RequestParam(required = false, name = "start", defaultValue = "0") int start, 
			@RequestParam(required = false, name = "searchText") String searchText,
			 Model model, HttpSession session) {

		String boardName = "bulletinBoard";
		int count;
	
		try {
			// 게시글 리스트
			List<BoardMenu> menuList = homepageService.getBoardMenuList(boardName);
			model.addAttribute("menuList", menuList);
			
			if (menuName != null && menuName.length() != 0) {
				List<Bulletin> changeBoardList = homepageService.getBulletinContentListByMenuName(menuName,start);
				count = homepageService.getBulletinCount(menuName);

				model.addAttribute("boardList", changeBoardList);
				model.addAttribute("menuName", menuName);
			} else if(searchText != null && searchText.length() != 0){
				logger.debug("{} 검색어", searchText);
				List<Bulletin> searchBoardList = homepageService.getBulletinContentListBySearchText(searchText,start);
				
				count = homepageService.getBulletinCountBySearchText(searchText);
				
				model.addAttribute("boardList", searchBoardList);
				model.addAttribute("searchText", searchText);
				
			} else {
				List<Bulletin> boardList = homepageService.getBulletinContentList(start);
				
				count = homepageService.getBulletinCount();
				
				model.addAttribute("boardList", boardList);
			}
			
			int pageCount = count/HomepageService.LIMIT;
			if(count % HomepageService.LIMIT>0)
				pageCount++;
			
			List<Integer> pageStartList = new ArrayList<>();
			for(int i=0; i<pageCount; i++){
				pageStartList.add(i*HomepageService.LIMIT);
			}
			
			model.addAttribute("pageStartList", pageStartList);

			// 게시글 내용 표시
			if (type != null) {
				model.addAttribute("type", "content");
				Bulletin bulletin = homepageService.getBulletinContentByNo(no);
				List<Comment> commentList = homepageService.getCommentList(boardName, no);

				logger.debug("{} 게시글 읽어오기 성공", boardName);
				logger.debug("{}, 댓글 갯수 {}", boardName, commentList.size());

				model.addAttribute("boardContentB", bulletin);
				model.addAttribute("commentListB", commentList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "board/bulletinBoard";
	}

/*---------------    게시판 공통      ---------------*/
	
	// 각 게시판 메뉴 추가
	@PostMapping(path = "/menuInsert")
	@ResponseBody
	public void menuInsert(@RequestParam("boardName") String boardName, @RequestParam("menuName") String menuName,
			HttpSession session) {

		BoardMenu boardMenu = new BoardMenu();
		boardMenu.setBoardName(boardName);
		boardMenu.setMenuName(menuName);
		logger.debug("{} 게시판에 추가할 메뉴({})의 boardMenu dto 생성 및 입력 완료", boardName, menuName);

		homepageService.insertBoardMenu(boardMenu);

		logger.debug("{} 게시판에 메뉴({}) 추가 완료", boardName, menuName);

	}

	// 게시글 작성 페이지
	@GetMapping(path = "/write")
	public String write(@RequestParam("boardName") String boardName, Model model) {

		List<BoardMenu> menuList = homepageService.getBoardMenuList(boardName);
		model.addAttribute("menuList", menuList);

		logger.debug("{} 게시글 작성 페이지", boardName);

		return "board/write";
	}

	// 게시글 작성
	@PostMapping(path = "/write")
	public String postWrite(@RequestParam("boardName") String boardName,
			@RequestParam(required = false, name = "file") MultipartFile file, HttpServletRequest request) {

		if (file.getSize() != 0) {

			logger.debug("파일 이름 : {}", file.getOriginalFilename());
			logger.debug("파일 크기 : {}", file.getSize());

			String fileName = file.getOriginalFilename();
			String filePath;
			String fileType = file.getContentType();

			if (boardName.equals("bulletinBoard")) {
				filePath = "D:/tmp/bulletin/" + file.getOriginalFilename();
				homepageService.insertBulletinWithFile(request, fileName, filePath, fileType);
			} else {
				filePath = "D:/tmp/storage/" + file.getOriginalFilename();
				homepageService.insertStorageWithFile(request, fileName, filePath, fileType);
			}

			homepageService.fileUpload(file, filePath);

		} else {
			if (boardName.equals("bulletinBoard")) {
				homepageService.insertBulletin(request);
			} else {
				homepageService.insertStorage(request);
			}
		}
		return "redirect:" + boardName;
	}

	// 게시글 수정 페이지
	@GetMapping(path = "/update")
	public String postUpdatePage(@RequestParam("boardName") String boardName, @RequestParam("contentNo") int contentNo,
			Model model) {

		List<BoardMenu> menuList = homepageService.getBoardMenuList(boardName);
		
		model.addAttribute("menuList", menuList);

		model.addAttribute("boardName", boardName);

		if (boardName.equals("bulletinBoard")) {
			Bulletin bulletinContent = homepageService.getBulletinContentByNo(contentNo);
			model.addAttribute("bulletinContent", bulletinContent);
		} else {
			Storage storageContent = homepageService.getStorageContentByNo(contentNo);
			model.addAttribute("storageContent", storageContent);
		}

		return "board/postUpdate";
	}

	// 게시글 수정
	@PostMapping(path = "/update")
	public String postUpdate(@RequestParam("boardName") String boardName, @RequestParam("menuName") String menuName,
			@RequestParam("contentNo") int contentNo, @RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(required = false, name = "file") MultipartFile file) {

		if (boardName.equals("bulletinBoard")) {
			Bulletin bulletin = homepageService.getBulletinContentByNo(contentNo);

			bulletin.setTitle(title);
			bulletin.setContent(content);
			bulletin.setMenuName(menuName);

			if (file.getSize() == 0) {
				homepageService.updateBulletin(bulletin);
			} else {
				String fileName = file.getOriginalFilename();
				String filePath = "D:/tmp/bulletin/" + file.getOriginalFilename();
				String fileType = file.getContentType();

				bulletin.setFileName(fileName);
				bulletin.setFilePath(filePath);
				bulletin.setFileType(fileType);

				homepageService.fileUpload(file, filePath);
				homepageService.updateBulletinWithFile(bulletin);
			}
		} else {
			Storage storage = homepageService.getStorageContentByNo(contentNo);

			storage.setTitle(title);
			storage.setContent(content);
			storage.setMenuName(menuName);
			
			if (file.getSize() == 0) {				
				homepageService.updateStorage(storage);
			} else {
				String fileName = file.getOriginalFilename();
				String filePath = "D:/tmp/storage/" + file.getOriginalFilename();
				String fileType = file.getContentType();

				storage.setFileName(fileName);
				storage.setFilePath(filePath);
				storage.setFileType(fileType);

				homepageService.fileUpload(file, filePath);
				homepageService.updateStorageWithFile(storage);
			}
		}

		return "redirect:" + boardName;
	}

	// 게시글 삭제
	@GetMapping(path = "/delete")
	public String delete(@RequestParam("boardName") String boardName, @RequestParam("contentNo") int contentNo) {

		if (boardName.equals("bulletinBoard")) {
			homepageService.deleteBulletin(contentNo);
		} else {
			homepageService.deleteStorgae(contentNo);
		}

		return "redirect:" + boardName;
	}

	// 파일 다운로드
	@GetMapping("/download")
	public void download(@RequestParam("boardName") String boardName, @RequestParam("no") int no,
			HttpServletResponse response) {

		String fileName, filePath, fileType;

		if (boardName.equals("bulletinBoard")) {
			Bulletin bulletin = homepageService.getBulletinContentByNo(no);

			fileName = bulletin.getFileName();
			filePath = bulletin.getFilePath();
			fileType = bulletin.getFileType();
		} else {
			Storage storage = homepageService.getStorageContentByNo(no);

			fileName = storage.getFileName();
			filePath = storage.getFilePath();
			fileType = storage.getFileType();
		}

		homepageService.fileDownload(fileName, filePath, fileType, response);

	}

	// 댓글 등록
	@PostMapping(path = "/commentInsert")
	public String commentInsert(@RequestParam("boardName") String boardName, @RequestParam("contentNo") int contentNo,
			@RequestParam("userId") String userId, @RequestParam("comment") String comment) {

		Comment boardComment = new Comment();
		boardComment.setBoardName(boardName);
		boardComment.setContentNo(contentNo);
		boardComment.setComment(comment);
		boardComment.setUserId(userId);
		logger.debug("{}, {}, {} 댓글 등록 완료", userId, boardName, contentNo);
		
		homepageService.insertComment(boardComment);
		
		return "redirect:" + boardName ;
	}
}

package kr.or.connect.homepage.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import kr.or.connect.homepage.dao.BoardMenuDao;
import kr.or.connect.homepage.dao.BulletinBoardDao;
import kr.or.connect.homepage.dao.ScheduleDao;
import kr.or.connect.homepage.dao.StorageBoardDao;
import kr.or.connect.homepage.dto.BoardMenu;
import kr.or.connect.homepage.dto.Bulletin;
import kr.or.connect.homepage.dto.Schedule;
import kr.or.connect.homepage.dto.Storage;
import kr.or.connect.homepage.config.ApplicationConfig;

import kr.or.connect.homepage.service.HomepageServiceImpl;

@Controller
public class HomepageController {
	
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
	public String storage(Model model, HttpSession session) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);
		StorageBoardDao boardDao = ac.getBean(StorageBoardDao.class);
		try {
			List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll("storageBoard");
			model.addAttribute("menuList", menuList);
			List<Storage> boardLsit = boardDao.selectAll();
			model.addAttribute("boardList", boardLsit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "board/storageBoard";
	}
	
	@PostMapping(path = "/storageBoard")
	public String storageBoardListChange(@RequestParam("menuName") String menuName, Model model, HttpSession session) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);
		StorageBoardDao boardDao = ac.getBean(StorageBoardDao.class);
		try {
			List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll("storageBoard");
			model.addAttribute("menuList", menuList);
			if (menuName != null) {
				StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
				List<Storage> changeBoardList = storageBoardDao.boardMenuSelectByMenuName(menuName);
				model.addAttribute("boardList", changeBoardList);
			} else {
				List<Storage> boardLsit = boardDao.selectAll();
				model.addAttribute("boardList", boardLsit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "board/storageBoard";
	}

	
/*---------------    자유게시판      ---------------*/
	
	@GetMapping(path = "/bulletinBoard")
	public String bulletin(Model model, HttpSession session) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);
		BulletinBoardDao boardDao = ac.getBean(BulletinBoardDao.class);
		try {
			List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll("bulletinBoard");
			model.addAttribute("menuList", menuList);
			List<Bulletin> boardLsit = boardDao.selectAll();
			model.addAttribute("boardList", boardLsit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "board/bulletinBoard";
	}

	@PostMapping(path = "/bulletinBoard")
	public String bulletinBoardListChange(@RequestParam("menuName") String menuName, Model model, HttpSession session) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);
		BulletinBoardDao boardDao = ac.getBean(BulletinBoardDao.class);
		try {
			List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll("bulletinBoard");
			model.addAttribute("menuList", menuList);
			if (menuName != null) {
				BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
				List<Bulletin> changeBoardList = bulletinBoardDao.boardMenuSelectByMenuName(menuName);
				model.addAttribute("boardList", changeBoardList);
			} else {
				List<Bulletin> boardLsit = boardDao.selectAll();
				model.addAttribute("boardList", boardLsit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "board/bulletinBoard";
	}

/*---------------    게시판 공통      ---------------*/
	
	@PostMapping(path = "/menuInsert")
	@ResponseBody
	public void menuInsert(@RequestParam("boardName") String boardName, @RequestParam("menuName") String menuName,
			HttpSession session) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);

		BoardMenu boardMenu = new BoardMenu();
		boardMenu.setBoardName(boardName);
		boardMenu.setMenuName(menuName);
		System.out.println("입력이 완료되었습니다.");

		boardMenuDao.insert(boardMenu);

		System.out.println("메뉴 추가 완료");
	}

	@PostMapping(path = "/getContent")
	@ResponseBody
	public void getContent(@RequestParam("boardName") String boardName, @RequestParam("no") String no,
			HttpSession session, PrintWriter out) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		if (boardName.equals("bulletinBoard")) {
			BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
			Bulletin bulletin = bulletinBoardDao.selectByNo((Integer.valueOf(no)));

			System.out.println("자유게시판 게시글 읽어오기 성공");

			session.setAttribute("boardContentB", bulletin);
		}

		else if (boardName.equals("storageBoard")) {
			StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
			Storage storage = storageBoardDao.selectByNo((Integer.valueOf(no)));

			System.out.println("소스 관련 게시판 게시글 읽어오기 성공");

			session.setAttribute("boardContentS", storage);
		}
	}

	@GetMapping(path = "/write")
	public String write(@RequestParam("boardName") String boardName, Model model) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		BoardMenuDao boardMenuDao = ac.getBean(BoardMenuDao.class);

		System.out.println(boardName);
		List<BoardMenu> menuList = boardMenuDao.boardMenuSelectAll(boardName);
		model.addAttribute("menuList", menuList);

		return "board/write";
	}

	@PostMapping(path = "/write")
	public String postWrite(@RequestParam("boardName") String boardName, HttpServletRequest request) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		if (boardName.equals("bulletinBoard")) {
			BulletinBoardDao bulletinBoardDao = ac.getBean(BulletinBoardDao.class);
			bulletinBoardDao.requestInsert(request);
		} else if (boardName.equals("storageBoard")) {
			StorageBoardDao storageBoardDao = ac.getBean(StorageBoardDao.class);
			storageBoardDao.requestInsert(request);
		} else {

		}

		return "board/" + boardName;
	}
}

package kr.or.connect.homepage.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.homepage.dto.User;
import kr.or.connect.homepage.service.UserService;

@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	// 회원가입 뷰
	@GetMapping(path = "/signUp")
	public String signUp() {
		return "user/signUp";
	}

	// 회원가입
	@PostMapping(path = "/regist")
	public String regist(@RequestParam(name = "id", required = true) String id,
			@RequestParam(name = "password", required = true) String password, @RequestParam("image") MultipartFile image,
			ModelMap modelMap) {
		
		User user = new User();
		String imageName, imagePath, imageType;
		
		user.setUserId(id);
		user.setPassword(password);
		
		if(image.getSize() != 0){
			imageName = id;
			imagePath = "D:/tmp/user/"+id;
			imageType = image.getContentType();						
		}else{
			imageName = "default.png";
			imagePath = "D:/tmp/user/default/"+imageName;
			imageType = "image/png";
		}
		
		logger.debug("imageName = {}, imageType = {}",imageName, imageType);
		logger.debug("imagePath = {}", imagePath);
		
		userService.imageUpload(image, imagePath);
		
		logger.debug("유저 프로필 이미지 저장 완료");
		logger.debug("userId = {}, imageName = {}",id, imageName);
		logger.debug("imagePath = {}", imagePath);
		
		user.setFileName(imageName);
		user.setFilePath(imagePath);
		user.setFileType(imageType);
		
		userService.insertUser(user);
		logger.debug("{} 유저의 아이디 생성 완료",id);
		
		return "user/signUpResult";
	}

	@GetMapping(path = "/signUpResult")
	public String signUpReuslt() {
		return "user/signUpResult";
	}

	// 중복 아이디 체크
	@GetMapping(path ="/checkId")
	public void checkId(@RequestParam("id") String id, HttpSession session, PrintWriter out) {

		User resultUser = userService.selectById(id);

		if (resultUser == null) {
			out.print("y");
			out.close();
			logger.debug("중복된 아이디 없음");
		} else {
			out.print("n");
			out.close();
			logger.debug("중복된 아이디 체크 = {}", resultUser.getUserId());
		}

		logger.debug("중복 아이디 체크 완료");
	}

	@GetMapping(path = "/loginForm")
	public String loginView() {
		return "user/loginForm";
	}

	// 로그인
	@PostMapping(path = "/login")
	public String login(@RequestParam(name = "id", required = true) String id,
			@RequestParam(name = "password", required = true) String password, RedirectAttributes redirectAttr,
			HttpSession session) {
		
		User user = userService.selectById(id);

		if (user == null){
			logger.debug("{} 유저 로그인 실패(null)",id);
			redirectAttr.addFlashAttribute("errorMessage", "아이디가 존재하지 않습니다.");
			return "redirect:/loginForm";
		}else if (user.getUserId().equals(id) && user.getPassword().equals(password)) {
			logger.debug("{} 유저 로그인 성공",id);
			session.setAttribute("loginUser", user);
			if (user.getUserId().equals("Admin"))
				session.setAttribute("checkAdmin", true);
			return "redirect:/home";
		} else {
			logger.debug("{} 유저 로그인 실패(password)",id);
			redirectAttr.addFlashAttribute("errorMessage", "비밀번호를 확인하세요.");
			return "redirect:/loginForm";
		}
	}

	// 로그아웃
	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("loginUser") != null){
			session.removeAttribute("loginUser");
			if (session.getAttribute("checkAdmin") !=null && (boolean)session.getAttribute("checkAdmin") == true)
				session.removeAttribute("checkAdmin");
		}
		return "redirect:/home";
	}
	
	@GetMapping("/getImage")
	public void getImage(@RequestParam("id") String id, HttpServletResponse response) {
		
		String imageName,imagePath,imageType;

		User user = userService.selectById(id);
		
		imageName = user.getFileName();
		imagePath = user.getFilePath();
		imageType = user.getFileType();
		
		userService.getImage(imageName, imagePath, imageType, response);
		
	}
}

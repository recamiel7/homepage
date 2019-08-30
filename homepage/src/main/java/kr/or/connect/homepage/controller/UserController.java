package kr.or.connect.homepage.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.homepage.config.ApplicationConfig;
import kr.or.connect.homepage.dao.UserDao;
import kr.or.connect.homepage.dto.User;

@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		UserDao userDao = ac.getBean(UserDao.class);
		
		User users = new User();
		String imageName, imagePath, imageType;
		
		users.setUserId(id);
		users.setPassword(password);
		
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
		
		try(
                FileOutputStream fos = new FileOutputStream(imagePath);
                InputStream is = image.getInputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = is.read(buffer)) != -1){
                fos.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
		logger.debug("유저 프로필 이미지 저장 완료");
		logger.debug("userId = {}, imageName = {}",id, imageName);
		logger.debug("imagePath = {}", imagePath);
		
		users.setFileName(imageName);
		users.setFilePath(imagePath);
		users.setFileType(imageType);
		
		userDao.insert(users);
		logger.debug("{} 유저의 아이디 생성 완료",id);
		
		return "user/signUpResult";
	}

	@GetMapping(path = "/signUpResult")
	public String signUpReuslt() {
		return "user/signUpResult";
	}

	// 중복 아이디 체크
	@RequestMapping(value = "checkId", method = RequestMethod.POST)
	public void checkId(@RequestParam("id") String id, HttpSession session, PrintWriter out) {

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		UserDao userDao = ac.getBean(UserDao.class);

		User resultUser = userDao.selectById(id);

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

		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		UserDao userDao = ac.getBean(UserDao.class);

		User user = userDao.selectById(id);

		if (user.getUserId().equals(id) && user.getPassword().equals(password)) {
			logger.debug("{} 유저 로그인 성공",id);
			session.setAttribute("loginUser", user);
			if (user.getUserId().equals("Admin"))
				session.setAttribute("checkAdmin", true);
			return "redirect:/home";
		} else {
			logger.debug("{} 유저 로그인 실패",id);
			redirectAttr.addFlashAttribute("errorMessage", "아이디와 비밀번호를 확인하세요.");
			return "redirect:/user/loginForm";
		}
	}

	// 로그아웃
	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUser");
		if ((boolean) session.getAttribute("checkAdmin") == true)
			session.removeAttribute("checkAdmin");
		return "redirect:/home";
	}

	
	@GetMapping("/getImage")
	public void getImage(@RequestParam("id") String id, HttpServletResponse response) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		String fileName,filePath,contentType;
		
		UserDao userDao = ac.getBean(UserDao.class);
		User user = userDao.selectById(id);
		
		fileName = user.getFileName();
		filePath = user.getFilePath();
		contentType = user.getFileType();
		
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", contentType);
		
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
            throw new RuntimeException("image file load Error");
        }
		
	}
}

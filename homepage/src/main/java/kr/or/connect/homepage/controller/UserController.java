package kr.or.connect.homepage.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.homepage.config.ApplicationConfig;
import kr.or.connect.homepage.dao.UserDao;
import kr.or.connect.homepage.dto.User;

@Controller
public class UserController {

	//회원가입 뷰
	@GetMapping(path="/signUp")
	public String signUp(){
		return "user/signUp";
	}
	
	//회원가입
	@PostMapping(path="/regist")
	public String regist(@RequestParam(name="id",required= true) String id, 
						@RequestParam(name="password",required= true) String password, ModelMap modelMap){
		
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		UserDao userDao = ac.getBean(UserDao.class);
			
		User users = new User();
		users.setUserId(id);
		users.setPassword(password);
		System.out.println("입력이 완료되었습니다.");
		
		userDao.insert(users);
		
		return "user/signUpResult";
	}
	
	
	
	@GetMapping(path="/signUpResult")
	public String signUpReuslt(){
		return "user/signUpResult";
	}
	
	//중복 아이디 체크
	@RequestMapping(value="checkId", method = RequestMethod.POST)	
	public void checkId (@RequestParam("id") String id, HttpSession session, PrintWriter out){
		
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		UserDao userDao = ac.getBean(UserDao.class);
		
		User resultUser = userDao.selectById(id);
			
		if(resultUser==null){
			out.print("y");
			out.close();
			System.out.println("중복된 아이디가 없습니다.");
		}else{
			out.print("n");
			out.close();
			System.out.println(resultUser.getUserId());
		}
		
		System.out.println("아이디 체크 완료");
	}	
	
	@GetMapping(path="/loginForm")
	public String loginView(){
		return "user/loginForm";
	}
	
	//로그인
	@PostMapping(path="/login")
	public String login(@RequestParam(name="id",required= true) String id, 
						@RequestParam(name="password",required= true) String password, 
						RedirectAttributes redirectAttr,
						HttpSession session){
		
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		UserDao userDao = ac.getBean(UserDao.class);
					
		User user = userDao.selectById(id);

		if(user.getUserId().equals(id) && user.getPassword().equals(password)){
			System.out.println("로그인 성공");
			session.setAttribute("loginUser", user);
				if(user.getUserId().equals("Admin"))
					session.setAttribute("checkAdmin", true);
			return "redirect:/home";
		}else{
			System.out.println("로그인 실패");
			redirectAttr.addFlashAttribute("errorMessage", "아이디와 비밀번호를 확인하세요.");
			return "redirect:/user/loginForm";
		}
	}
	
	@GetMapping(path="/logout")
	public String logout(HttpSession session){
		session.removeAttribute("loginUser");
		if((boolean)session.getAttribute("checkAdmin")==true)
			session.removeAttribute("checkAdmin");
		return "redirect:/home";
	}

}

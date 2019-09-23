package kr.or.connect.homepage.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.homepage.dto.User;

public interface UserService {
	public void insertUser(User user);
	
	public User selectById(String id);
	
	public void imageUpload(MultipartFile image, String imagePath);
	public void getImage(String imageName, String imagePath, String imageType, HttpServletResponse response);
}

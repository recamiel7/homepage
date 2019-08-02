package kr.or.connect.homepage.dto;

import java.io.File;

public class User {
	private String userId, password;
	private File image;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "유저의 ID는 "+ userId +"입니다.";
	}
	
	
}

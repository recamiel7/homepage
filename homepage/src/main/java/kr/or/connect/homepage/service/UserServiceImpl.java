package kr.or.connect.homepage.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.homepage.dao.UserDao;
import kr.or.connect.homepage.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public void insertUser(User user) {
		userDao.insert(user);
	}
	
	@Override
	public User selectById(String id) {
		return userDao.selectById(id);
	}

	@Override
	public void imageUpload(MultipartFile image, String imagePath) {
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
	}

	@Override
	public void getImage(String imageName, String imagePath, String imageType, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", imageType);
		
		try(
                FileInputStream fis = new FileInputStream(imagePath);
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

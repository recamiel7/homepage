package kr.or.connect.homepage.test;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.homepage.config.ApplicationConfig;
import kr.or.connect.homepage.dao.UserDao;
import kr.or.connect.homepage.dto.User;

public class DBTest {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		//===연결 테스트===
		
		/*DataSource ds = ac.getBean(DataSource.class);
		Connection conn = null;
		try {
			conn = ds.getConnection();
			if(conn != null)
				System.out.println("접속 성공");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
		
		
		//===SelectAll Test===
		
		/*UserDao userDao = ac.getBean(UserDao.class);

		List<User> list = userDao.selectAll();
		
		for(User user: list) {
			System.out.println(user);
		}*/
		
		
		//===Update Test===
		
		/*UserDao userDao = ac.getBean(UserDao.class);
		
		User user = new User();
		user.setUserId("test1");
		user.setPassword("12355");
		System.out.println("변경이 완료되었습니다.");
		
		userDao.update(user);*/
		
		
		//===Insert Test===
		
		/*UserDao userDao = ac.getBean(UserDao.class);
		
		User users = new User();
		users.setUserId("test1");
		users.setPassword("1234");
		System.out.println("입력이 완료되었습니다.");
		
		userDao.insert(users);*/
		
		
		//===SelectById Test===
		/*UserDao userDao = ac.getBean(UserDao.class);
		
		User resultUser = userDao.selectById("test1");
		System.out.println(resultUser);*/
		
		
		//===DeleteById Test===
		UserDao userDao = ac.getBean(UserDao.class);
		String testId = "test1";
		
		userDao.deleteById(testId);
		System.out.println("삭제가 완료되었습니다.");
		User resultUser = userDao.selectById(testId);
		System.out.println(resultUser);
	}
}

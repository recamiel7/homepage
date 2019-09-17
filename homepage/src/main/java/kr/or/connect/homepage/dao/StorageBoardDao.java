package kr.or.connect.homepage.dao;

import static kr.or.connect.homepage.dao.BoardDaoSqls.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.homepage.dto.Storage;

@Repository
public class StorageBoardDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Storage> storageRowMapper = BeanPropertyRowMapper.newInstance(Storage.class);
	
	public StorageBoardDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("storage");
	}
	
	public List<Storage> selectAll(){
		return jdbc.query(STORAGE_SELECT_ALL, Collections.emptyMap(), storageRowMapper);
	}
	
	public List<Storage> boardMenuSelectByMenuName(String menuName) {
		Map<String, ?> params = Collections.singletonMap("menuName", menuName);
		return jdbc.query(STORAGE_SELECT_BY_MENU_NAME, params, storageRowMapper);
	}
	
	public void requestInsert(HttpServletRequest request){
		Storage storage = new Storage();
		String menu =  request.getParameter("menuName");
		String title =  request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
				
		storage.setMenu(menu);
		storage.setTitle(title);
		storage.setContent(content);
		storage.setRegdate(date);
		
		insert(storage);
	}
	
	public void requestInsert(HttpServletRequest request,String fileName, String filePath, String fileType){
		Storage storage = new Storage();
		String menu =  request.getParameter("menuName");
		String title =  request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		
		storage.setMenu(menu);
		storage.setTitle(title);
		storage.setContent(content);
		storage.setRegdate(date);
		storage.setFileName(fileName);
		storage.setFilePath(filePath);
		storage.setFileType(fileType);
		
		insert(storage);
	}
	
	public int insert(Storage storage){
		SqlParameterSource params = new BeanPropertySqlParameterSource(storage);
		return insertAction.execute(params);
	}
	
	public int update(Storage storage) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(storage);
		return jdbc.update(STORAGE_UPDATE, params);
	}
	
	public int updateWithFile(Storage storage) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(storage);
		return jdbc.update(STORAGE_UPDATE_WITH_FILE, params);
	}
	
	public int deleteByNo(Integer no) {
		Map<String, ?> params = Collections.singletonMap("no", no);
		return jdbc.update(STORAGE_DELETE_BY_NO, params);
	}
	
	public Storage selectByNo(Integer no) {
		try {
			Map<String, ?> params = Collections.singletonMap("no", no);
			return jdbc.queryForObject(STORAGE_SELECT_BY_NO, params, storageRowMapper);		
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
}

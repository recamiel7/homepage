package kr.or.connect.homepage.dao;

import static kr.or.connect.homepage.dao.BoardDaoSqls.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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

import kr.or.connect.homepage.dto.Bulletin;

@Repository
public class BulletinBoardDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Bulletin> bulletinRowMapper = BeanPropertyRowMapper.newInstance(Bulletin.class);

	public BulletinBoardDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("bulletin");
	}
	
	/*public List<Bulletin> selectAll(){
		return jdbc.query(BULLETIN_SELECT_ALL, Collections.emptyMap(), bulletinRowMapper);
	}
	
	public List<Bulletin> selectAllByMenuName(String menuName) {
		Map<String, ?> params = Collections.singletonMap("menuName", menuName);
		return jdbc.query(BULLETIN_SELECT_BY_MENU_NAME, params, bulletinRowMapper);
	}*/
	
	public List<Bulletin> selectAll(Integer start, Integer limit){
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", limit);
		return jdbc.query(BULLETIN_SELECT_ALL_PAGING, params, bulletinRowMapper);
	}	
	
	public List<Bulletin> selectAllByMenuName(String menuName, Integer start, Integer limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("menuName",menuName);
		params.put("start",start);
		params.put("limit",limit);
		
		return jdbc.query(BULLETIN_SELECT_BY_MENU_NAME_PAGING, params, bulletinRowMapper);
	}
	
	public List<Bulletin> selectAllBySearchText(String searchText, Integer start, Integer limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("searchText","%"+searchText+"%");
		params.put("start",start);
		params.put("limit",limit);
		
		return jdbc.query(BULLETIN_SELECT_SEARCH_TEXT_PAGING, params, bulletinRowMapper);
	}
	
	public void requestInsert(HttpServletRequest request){
		Bulletin bulletin = new Bulletin();
		String userId =  request.getParameter("userId");
		String menuName =  request.getParameter("menuName");
		String title =  request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		
		bulletin.setUserId(userId);
		bulletin.setMenuName(menuName);
		bulletin.setTitle(title);
		bulletin.setContent(content);
		bulletin.setRegdate(date);
		
		insert(bulletin);
	}
	
	public void requestInsert(HttpServletRequest request, String fileName, String filePath, String fileType) {
		Bulletin bulletin = new Bulletin();
		String userId =  request.getParameter("userId");
		String menuName =  request.getParameter("menuName");
		String title =  request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		
		bulletin.setUserId(userId);
		bulletin.setMenuName(menuName);
		bulletin.setTitle(title);
		bulletin.setContent(content);
		bulletin.setRegdate(date);
		bulletin.setFileName(fileName);
		bulletin.setFilePath(filePath);
		bulletin.setFileType(fileType);
		
		insert(bulletin);
	}
	
	public int insert(Bulletin bulletin){
		SqlParameterSource params = new BeanPropertySqlParameterSource(bulletin);
		return insertAction.execute(params);
	}
	
	public int update(Bulletin bulletin) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(bulletin);
		return jdbc.update(BULLETIN_UPDATE, params);
	}
	
	public int updateWithFile(Bulletin bulletin) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(bulletin);
		return jdbc.update(BULLETIN_UPDATE_WITH_FILE, params);
	}
	
	public int deleteByNo(Integer no) {
		Map<String, ?> params = Collections.singletonMap("no", no);
		return jdbc.update(BULLETIN_DELETE_BY_NO, params);
	}
	
	public Bulletin selectByNo(Integer no) {
		try {
			Map<String, ?> params = Collections.singletonMap("no", no);
			return jdbc.queryForObject(BULLETIN_SELECT_BY_NO, params, bulletinRowMapper);		
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public int selectCount(){
		return jdbc.queryForObject(BULLETIN_SELECT_COUNT, Collections.emptyMap(), Integer.class);
	}
	
	public int selectCount(String menuName){
		Map<String, ?> params = Collections.singletonMap("menuName", menuName);
		return jdbc.queryForObject(BULLETIN_SELECT_COUNT_BY_MENU_NAME , params, Integer.class);
	}
	
	public int selectCountBySearchText(String searchText){
		Map<String, ?> params = Collections.singletonMap("searchText", "%"+searchText+"%");
		return jdbc.queryForObject(BULLETIN_SELECT_COUNT_BY_SEARCH_TEXT , params, Integer.class);
	}
}

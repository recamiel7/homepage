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
	
	public List<Bulletin> selectAll(){
		return jdbc.query(BULLETIN_SELECT_ALL, Collections.emptyMap(), bulletinRowMapper);
	}
	
	public void requestInsert(HttpServletRequest request){
		Bulletin bulletin = new Bulletin();
		String userId =  request.getParameter("userId");
		String menu =  request.getParameter("menuName");
		String title =  request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		
		bulletin.setUserId(userId);
		bulletin.setMenu(menu);
		bulletin.setTitle(title);
		bulletin.setContent(content);
		bulletin.setRegdate(date);
		
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
}

package kr.or.connect.homepage.dao;

import static kr.or.connect.homepage.dao.UserDaoSqls.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.homepage.dto.User;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

	public UserDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("user");
	}
	
	public List<User> selectAll(){
		return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);
	}
	
	public int insert(User user){
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		return insertAction.execute(params);
	}
	
	public int update(User user) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		return jdbc.update(UPDATE, params);
	}
	
	public int deleteById(String id) {
		Map<String, ?> params = Collections.singletonMap("userId", id);
		return jdbc.update(DELETE_BY_USER_ID, params);
	}
	
	public User selectById(String id) {
		try {
			Map<String, ?> params = Collections.singletonMap("userId", id);
			return jdbc.queryForObject(SELECT_BY_USER_ID, params, rowMapper);		
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
}

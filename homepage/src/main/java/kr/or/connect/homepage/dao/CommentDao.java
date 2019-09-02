package kr.or.connect.homepage.dao;

import static kr.or.connect.homepage.dao.CommentDaoSqls.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.homepage.dto.Comment;

@Repository
public class CommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
	
	public CommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("comment");
	}
	
	public List<Comment> selectAll(){
		return jdbc.query(COMMENT_SELECT_ALL, Collections.emptyMap(), rowMapper);
	}
	
	public List<Comment> selectByContentNo(String boardName, String contentNo){
		Map<String, String> params = new HashMap<String, String>();
		params.put("boardName", boardName);
		params.put("contentNo", contentNo);
		return jdbc.query(COMMENT_SELECT_BY_CONTENT_NO, params, rowMapper);
	}
	
	public int insert(Comment coment){
		SqlParameterSource params = new BeanPropertySqlParameterSource(coment);
		return insertAction.execute(params);
	}
}

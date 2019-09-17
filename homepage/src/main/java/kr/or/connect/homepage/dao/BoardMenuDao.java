package kr.or.connect.homepage.dao;

import static kr.or.connect.homepage.dao.BoardDaoSqls.*;

import java.util.Collections;
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

import kr.or.connect.homepage.dto.BoardMenu;

@Repository
public class BoardMenuDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<BoardMenu> rowMapper = BeanPropertyRowMapper.newInstance(BoardMenu.class);

	public BoardMenuDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("board_menu");
	}
	
	/*public List<BoardMenu> menuSelectAll(){
		return jdbc.query(MENU_SELECT_ALL, Collections.emptyMap(), rowMapper);
	}*/
	
	public List<BoardMenu> boardMenuSelectAll(String boardName){
		Map<String, ?> params = Collections.singletonMap("boardName", boardName);
		return jdbc.query(BOARD_MENU_SELECT_ALL, params, rowMapper);
	}
	
	public int insert(BoardMenu boardMenu){
		SqlParameterSource params = new BeanPropertySqlParameterSource(boardMenu);
		return insertAction.execute(params);
	}
		
	public int update(BoardMenu boardMenu) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(boardMenu);
		return jdbc.update(MENU_UPDATE, params);
	}
	
	public int menuDeleteByNo(Integer no) {
		Map<String, ?> params = Collections.singletonMap("no", no);
		return jdbc.update(DELETE_MENU_BY_NO, params);
	}
	
}

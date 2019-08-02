package kr.or.connect.homepage.dao;

import static kr.or.connect.homepage.dao.BoardDaoSqls.*;

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
	
	public int insert(Storage storage){
		SqlParameterSource params = new BeanPropertySqlParameterSource(storage);
		return insertAction.execute(params);
	}
	
	public int update(Storage storage) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(storage);
		return jdbc.update(BULLETIN_UPDATE, params);
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

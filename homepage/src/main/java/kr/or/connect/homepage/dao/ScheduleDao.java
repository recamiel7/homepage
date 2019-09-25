package kr.or.connect.homepage.dao;

import static kr.or.connect.homepage.dao.ScheduleDaoSqls.*;

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

import kr.or.connect.homepage.dto.Schedule;

@Repository
public class ScheduleDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Schedule> RowMapper = BeanPropertyRowMapper.newInstance(Schedule.class);

	public ScheduleDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("schedule");
	}

	public List<Schedule> selectAll() {
		return jdbc.query(SCHEDULE_SELECT_ALL, Collections.emptyMap(), RowMapper);
	}

	public Schedule selectByNo(Integer no) {
		try {
			Map<String, ?> params = Collections.singletonMap("no", no);
			return jdbc.queryForObject(SCHEDULE_SELECT_BY_NO, params, RowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int update(Schedule schedule) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(schedule);
		return jdbc.update(SCHEDULE_UPDATE, params);
	}

	public int deleteByNo(Integer no) {
		Map<String, ?> params = Collections.singletonMap("no", no);
		return jdbc.update(SCHEDULE_DELETE_BY_NO, params);
	}

	public void requestInsert(HttpServletRequest request) {
		Schedule schedule = new Schedule();
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		int startYear = Integer.parseInt(request.getParameter("startYear"));
		int startMonth = Integer.parseInt(request.getParameter("startMonth"));
		int startDay = Integer.parseInt(request.getParameter("startDay"));
		int startHours;
		int startMinutes;
		if(request.getParameter("startHours") != ""&&request.getParameter("startMinutes") != ""){
			startHours = Integer.parseInt(request.getParameter("startHours"));
			startMinutes = Integer.parseInt(request.getParameter("startMinutes"));
			@SuppressWarnings("deprecation")
			Date startDate = new Date(startYear-1900, startMonth-1, startDay, startHours, startMinutes);
			schedule.setStartDate(startDate);
		}else{
			@SuppressWarnings("deprecation")
			Date startDate = new Date(startYear-1900, startMonth-1, startDay);
			schedule.setStartDate(startDate);
		}
		
		
		if (request.getParameter("endYear") != "") {
			int endYear = Integer.parseInt(request.getParameter("endYear"));
			int endMonth = Integer.parseInt(request.getParameter("endMonth"));
			int endDay = Integer.parseInt(request.getParameter("endDay"));
			int endHours;
			int endMinutes;
			if(request.getParameter("endHours") != ""&&request.getParameter("endMinutes") != ""){
				endHours = Integer.parseInt(request.getParameter("endHours"));
				endMinutes = Integer.parseInt(request.getParameter("endMinutes"));
				@SuppressWarnings("deprecation")
				Date endDate = new Date(endYear-1900, endMonth-1, endDay, endHours, endMinutes);
				schedule.setEndDate(endDate);
			}else{
				@SuppressWarnings("deprecation")
				Date endDate = new Date(endYear-1900, endMonth-1, endDay);
				schedule.setEndDate(endDate);
			}
		}
		
		schedule.setTitle(title);
		schedule.setContent(content);
		
		insert(schedule);
	}
	
	public int insert(Schedule schedule){
		SqlParameterSource params = new BeanPropertySqlParameterSource(schedule);
		return insertAction.execute(params);
	}
	
}

package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.DayTimetableDAO;
import com.foxminded.universitytimetable.dao.impl.rowmappers.DayTimetableMapper;
import com.foxminded.universitytimetable.models.DayTimetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class DayTimetableImpl implements DayTimetableDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String ADD_DAY_TIMETABLE_QUERY = "insert into dayTimetable values (?)";
    private final String GET_ALL_DAY_TIMETABLE_QUERY = "select id, date from dayTimetable";
    private final String GET_BY_ID_DAY_TIMETABLE_QUERY = "select id, date from dayTimetable where id = ?";
    private final String GET_BY_DATE_DAY_TIMETABLE_QUERY = "select id, date from dayTimetable where date = ?";
    private final String UPDATE_DAY_TIMETABLE_QUERY = "update dayTimetable set date = ?1 where id = ?2";
    private final String REMOVE_DAY_TIMETABLE_QUERY = "delete from dayTimetable where id = ?";

    public void add(DayTimetable dayTimetable) {
        jdbcTemplate.update(ADD_DAY_TIMETABLE_QUERY, dayTimetable.getDate());
    }

    public List<DayTimetable> getAll() {
        List<DayTimetable> dayTimetables = null;

        try {
            dayTimetables = jdbcTemplate.query(GET_ALL_DAY_TIMETABLE_QUERY, new DayTimetableMapper());
        } catch (DataAccessException dae) {
            System.err.println(dae.getMessage());
        }

        return dayTimetables;
    }

    public DayTimetable getById(int id) {
        DayTimetable timetableForDay = null;

        try {
            timetableForDay = jdbcTemplate.queryForObject(GET_BY_ID_DAY_TIMETABLE_QUERY, new Object[]{id}, new DayTimetableMapper());
        } catch (DataAccessException dae) {
            System.err.println(dae.getMessage());
        }

        return timetableForDay;
    }

    public DayTimetable getByDate(Date date) {
        DayTimetable timetableForDay = null;

        try {
            timetableForDay = jdbcTemplate.queryForObject(GET_BY_DATE_DAY_TIMETABLE_QUERY, new Object[]{date},
                    new DayTimetableMapper());
        } catch (DataAccessException dae) {
            System.err.println(dae.getMessage());
        }

        return timetableForDay;
    }

    public void update(DayTimetable dayTimetable) {
        try {
            jdbcTemplate.update(UPDATE_DAY_TIMETABLE_QUERY, dayTimetable.getDate(), dayTimetable.getId());
        } catch (DataAccessException dae) {
            System.err.println(dae.getMessage());
        }
    }

    public void remove(DayTimetable dayTimetable) {
        try {
            jdbcTemplate.update(REMOVE_DAY_TIMETABLE_QUERY, dayTimetable.getId());
        } catch (DataAccessException dae) {
            System.err.println(dae.getMessage());
        }
    }
}

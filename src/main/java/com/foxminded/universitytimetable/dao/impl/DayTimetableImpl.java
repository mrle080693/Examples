package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.DayTimetableDAO;
import com.foxminded.universitytimetable.dao.impl.rowmappers.DayTimetableMapper;
import com.foxminded.universitytimetable.models.DayTimetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class DayTimetableImpl implements DayTimetableDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static long objectsCounter = 0;

    private final String QUERY_ADD = "insert into dayTimetable values (?1, ?2)";
    private final String QUERY_GET_ALL = "select * from dayTimetable";
    private final String QUERY_GET_BY_ID = "select * from dayTimetable where id = ?";
    private final String QUERY_GET_BY_DATE = "select * from dayTimetable where date = ?";
    private final String QUERY_UPDATE = "update dayTimetable set date = ?1 where id = ?2";
    private final String QUERY_REMOVE = "delete from dayTimetable where id = ?";

    public void add(DayTimetable dayTimetable) {
        jdbcTemplate.update(QUERY_ADD, objectsCounter, dayTimetable.getDate());
        objectsCounter++;
    }

    public List<DayTimetable> getAll() {
        List<DayTimetable> dayTimetables;
        dayTimetables = jdbcTemplate.query(QUERY_GET_ALL, new DayTimetableMapper());

        return dayTimetables;
    }

    public DayTimetable getById(int id) {
        DayTimetable timetableForDay;
        timetableForDay = jdbcTemplate.queryForObject(QUERY_GET_BY_ID, new Object[]{id}, new DayTimetableMapper());

        return timetableForDay;
    }

    public DayTimetable getByDate(Date date) {
        DayTimetable timetableForDay;
        timetableForDay = jdbcTemplate.queryForObject(QUERY_GET_BY_DATE, new Object[]{date},
                new DayTimetableMapper());

        return timetableForDay;
    }

    public void update(DayTimetable dayTimetable) {
        jdbcTemplate.update(QUERY_UPDATE, dayTimetable.getDate(), dayTimetable.getId());
    }

    public void remove(DayTimetable dayTimetable) {
        jdbcTemplate.update(QUERY_REMOVE, dayTimetable.getId());
    }
}

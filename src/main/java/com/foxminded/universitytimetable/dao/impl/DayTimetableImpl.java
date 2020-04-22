package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.DayTimetableDAO;
import com.foxminded.universitytimetable.models.DayTimetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DayTimetableImpl implements DayTimetableDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DayTimetableImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(DayTimetable dayTimetable) {

    }

    public List<DayTimetable> getAll() {
        return null;
    }

    public DayTimetable getById(int id) {
        return null;
    }

    public DayTimetable getByDate(Date date) {
        return null;
    }

    public void update(DayTimetable dayTimetable) {

    }

    public void remove(DayTimetable dayTimetable) {

    }
}

package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.configurations.SpringJDBCPostgreSQLConfig;
import com.foxminded.universitytimetable.dao.DayTimetableDAO;
import com.foxminded.universitytimetable.models.DayTimetable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class DayTimetableImpl implements DayTimetableDAO {
    private SpringJDBCPostgreSQLConfig config = new SpringJDBCPostgreSQLConfig();
    private JdbcTemplate jdbcTemplate = config.postgreSQLJdbcTemplate();

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

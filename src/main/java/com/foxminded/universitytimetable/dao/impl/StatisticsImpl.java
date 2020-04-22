package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StatisticsImpl implements StatisticsDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public StatisticsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getProfessorEmployment(int professorId, Date from, Date till) {
        return 0;
    }

    public int getGroupEmployment(int groupId, Date from, Date till) {
        return 0;
    }
}

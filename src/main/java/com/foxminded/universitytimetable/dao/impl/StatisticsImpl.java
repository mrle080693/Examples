package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class StatisticsImpl implements StatisticsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getProfessorEmployment(int professorId, Date from, Date till) {
        return 0;
    }

    public int getGroupEmployment(int groupId, Date from, Date till) {
        return 0;
    }
}

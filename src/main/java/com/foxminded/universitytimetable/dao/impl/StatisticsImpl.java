package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.LessonMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class StatisticsImpl implements StatisticsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getProfessorEmployment(int professorId, Date from, Date till) {
        return getEmployment(professorId, from, till, true);
    }

    public int getGroupEmployment(int groupId, Date from, Date till) {
        return getEmployment(groupId, from, till, false);
    }

    private int getEmployment(int professorOrGroupId, Date from, Date till, boolean professorEmployment) {
        int lessonsQuantity = 0;

        try {
            if (professorEmployment) {
                lessonsQuantity = jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_EMPLOYMENT,
                        new Object[]{professorOrGroupId, from, till}, Integer.class);
            } else {
                lessonsQuantity = jdbcTemplate.queryForObject(Queries.GET_GROUP_EMPLOYMENT,
                        new Object[]{from, till, }, Integer.class);
            }
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get employment", dae);
        }

        return lessonsQuantity;
    }
}

package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("statisticsImplBean")
public class StatisticsImpl implements StatisticsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getGroupEmployment(int groupId, Date from, Date till) {
        int lessonsQuantity;

        try {
            lessonsQuantity = jdbcTemplate.queryForObject(Queries.GET_GROUP_EMPLOYMENT,
                    new Object[]{groupId, from, till}, Integer.class);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get group employment", dae);
        }

        return lessonsQuantity;
    }

    @Override
    public int getProfessorEmployment(int professorId, Date from, Date till) {
        int lessonsQuantity;

        try {
            lessonsQuantity = jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_EMPLOYMENT,
                    new Object[]{professorId, from, till}, Integer.class);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get professor employment", dae);
        }

        return lessonsQuantity;
    }
}

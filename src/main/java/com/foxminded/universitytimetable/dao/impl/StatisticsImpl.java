package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.GroupMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
            // For check if group with such id exists
            // Maybe this checking will be in service
            jdbcTemplate.queryForObject(Queries.GET_GROUP_BY_ID_QUERY, new Object[]{groupId},
                    new GroupMapper());

            lessonsQuantity = jdbcTemplate.queryForObject(Queries.GET_GROUP_EMPLOYMENT,
                    new Object[]{groupId, from, till}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Row with input id doesnt exist", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get group employment", ex);
        }

        return lessonsQuantity;
    }

    @Override
    public int getProfessorEmployment(int professorId, Date from, Date till) {
        int lessonsQuantity;

        try {
            // For check if professor with such id exists
            // Maybe this checking will be in service
            jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_BY_ID_QUERY, new Object[]{professorId},
                    new GroupMapper());

            lessonsQuantity = jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_EMPLOYMENT,
                    new Object[]{professorId, from, till}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Row with input id doesnt exist", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get professor employment", ex);
        }

        return lessonsQuantity;
    }
}

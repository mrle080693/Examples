package com.foxminded.universitytimetable.dao.impl.jdbctemplate;

import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.dao.queries.SQLQueries;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.rowmappers.ProfessorMapper;
import com.foxminded.universitytimetable.models.Professor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository("professorImplBean")
public class ProfessorImpl implements ProfessorDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfessorImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(Professor professor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add professor: " + professor);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(SQLQueries.ADD_PROFESSOR, new String[]{"id"});
                    ps.setString(1, professor.getName());
                    ps.setString(2, professor.getSurname());
                    ps.setString(3, professor.getPatronymic());
                    ps.setString(4, professor.getSubject());
                    return ps;
                }
                , keyHolder);

        Number id = keyHolder.getKey();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add professor with id = " + id);
        }

        return (int) id;
    }

    public List<Professor> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table professors");
        }

        List<Professor> professors = jdbcTemplate.query(SQLQueries.GET_ALL_PROFESSORS, new ProfessorMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professors);
        }

        return professors;
    }

    public Professor getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor by id = " + id);
        }

        Professor professor = jdbcTemplate.queryForObject(SQLQueries.GET_PROFESSOR_BY_ID, new Object[]{id},
                new ProfessorMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professor);
        }

        return professor;
    }

    public List<Professor> getBySurname(String surname) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor by surname = " + surname);
        }

        List<Professor> professors = jdbcTemplate.query(SQLQueries.GET_PROFESSORS_BY_SURNAME, new Object[]{surname},
                new ProfessorMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professors);
        }

        return professors;
    }

    public int update(Professor professor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update professor: " + professor);
        }

        int id = professor.getId();
        String name = professor.getName();
        String surName = professor.getSurname();
        String patronymic = professor.getPatronymic();
        String subject = professor.getSubject();

        int status = jdbcTemplate.update(SQLQueries.UPDATE_PROFESSOR, name, surName, patronymic, subject, id);

        if (status != 1) {
            throw new IllegalArgumentException("Row with input id doesnt exist");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update professor with status = " + status);
        }

        return status;
    }

    public int remove(int professorId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove professor with id = " + professorId);
        }

        int status = jdbcTemplate.update(SQLQueries.DELETE_PROFESSOR, professorId);

        if (status != 1) {
            throw new IllegalArgumentException("Row with input id doesnt exist");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove professor with id: " + professorId);
        }

        return status;
    }
}

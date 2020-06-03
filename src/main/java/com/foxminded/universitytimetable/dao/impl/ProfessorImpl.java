package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.ProfessorMapper;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(ProfessorImpl.class);

    public int add(Professor professor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add professor: " + professor);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(Queries.ADD_PROFESSOR_QUERY, new String[]{"id"});
                    ps.setString(1, professor.getName());
                    ps.setString(2, professor.getSurname());
                    ps.setString(3, professor.getPatronymic());
                    ps.setString(4, professor.getSubject());
                    return ps;
                }
                , keyHolder);

        Number id = keyHolder.getKey();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add professor: " + professor);
        }

        return (int) id;
    }

    public List<Professor> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table professors");
        }

        List<Professor> professors;

        professors = jdbcTemplate.query(Queries.GET_ALL_PROFESSORS_QUERY, new ProfessorMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professors);
        }

        return professors;
    }

    public Professor getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor by id = " + id);
        }

        Professor professor;

        professor = jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_BY_ID_QUERY, new Object[]{id},
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

        List<Professor> professors;

        professors = jdbcTemplate.query(Queries.GET_PROFESSOR_BY_SURNAME_QUERY, new Object[]{surname},
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

        int status = jdbcTemplate.update(Queries.UPDATE_PROFESSOR_QUERY, name, surName, patronymic, subject, id);

        if (status != 1) {
            throw new IllegalArgumentException("Row with input id doesnt exist");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update professor: " + professor);
        }

        return status;
    }

    public int remove(int professorId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove professor with id = " + professorId);
        }

        int status = jdbcTemplate.update(Queries.REMOVE_PROFESSOR_QUERY, professorId);

        if (status != 1) {
            throw new IllegalArgumentException("Row with input id doesnt exist");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove professor with id: " + professorId);
        }

        return status;
    }
}

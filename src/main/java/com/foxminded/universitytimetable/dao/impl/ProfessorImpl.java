package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.ProfessorMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ProfessorImpl implements ProfessorDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(Professor professor) {
        try {
            String name = professor.getName();
            String surName = professor.getSurName();
            String patronymic = professor.getPatronymic();
            String subject = professor.getSubject();

            jdbcTemplate.update(Queries.ADD_PROFESSOR_QUERY, name, surName, patronymic, subject);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant add professor", dae);
        }
    }

    public List<Professor> getAll() {
        List<Professor> professors = null;

        try {
            professors = jdbcTemplate.query(Queries.GET_ALL_PROFESSORS_QUERY, new ProfessorMapper());

        } catch (DataAccessException dae) {
            throw new DAOException("Cant get all from table professors", dae);
        }

        return professors;
    }

    public Professor getById(int id) {
        Professor professor = null;

        try {
            professor = jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_BY_ID_QUERY, new Object[]{id},
                    new ProfessorMapper());

        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundEntityException("Wrong id", e);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get by id from table professors", dae);
        }

        return professor;
    }

    public Professor getBySurname(String surname) {
        Professor professor = null;

        try {
            professor = jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_BY_SURNAME_QUERY, new Object[]{surname},
                    new ProfessorMapper());

        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundEntityException("Wrong id", e);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get professor by surname from table professors", dae);
        }

        return professor;
    }

    public void update(Professor professor) {
        try {
            String name = professor.getName();
            String surName = professor.getSurName();
            String patronymic = professor.getPatronymic();
            String subject = professor.getSubject();

            jdbcTemplate.update(Queries.UPDATE_PROFESSOR_QUERY, name, surName, patronymic, subject);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant update table professors", dae);
        }
    }

    public void remove(Professor professor) {
        try {
            jdbcTemplate.update(Queries.REMOVE_PROFESSOR_QUERY, professor.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant remove element of table groups", dae);
        }
    }
}

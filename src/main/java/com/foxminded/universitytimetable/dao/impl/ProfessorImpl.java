package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.ProfessorMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public int add(Professor professor) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(Queries.ADD_PROFESSOR_QUERY, new String[]{"id"});
                        ps.setString(1, professor.getName());
                        ps.setString(2, professor.getSurname());
                        ps.setString(3, professor.getPatronymic());
                        ps.setString(4, professor.getSubject());
                        return ps;
                    }
                    , keyHolder);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant add professor", ex);
        }

        Number id = keyHolder.getKey();
        return (int) id;
    }

    public List<Professor> getAll() {
        List<Professor> professors;

        try {
            professors = jdbcTemplate.query(Queries.GET_ALL_PROFESSORS_QUERY, new ProfessorMapper());

        } catch (DataAccessException ex) {
            throw new DAOException("Cant get all from table professors", ex);
        }

        return professors;
    }

    public Professor getById(int id) {
        Professor professor;

        try {
            professor = jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_BY_ID_QUERY, new Object[]{id},
                    new ProfessorMapper());

        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Row with input id doesnt exist", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get by id from table professors", ex);
        }

        return professor;
    }

    public List<Professor> getBySurname(String surname) {
        List<Professor> professors;

        try {
            professors = jdbcTemplate.query(Queries.GET_PROFESSOR_BY_SURNAME_QUERY, new Object[]{surname},
                    new ProfessorMapper());

        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Row with input id doesnt exist", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get professor by surname from table professors", ex);
        }

        return professors;
    }

    public int update(Professor professor) {
        try {
            int id = professor.getId();
            String name = professor.getName();
            String surName = professor.getSurname();
            String patronymic = professor.getPatronymic();
            String subject = professor.getSubject();

            int status = jdbcTemplate.update(Queries.UPDATE_PROFESSOR_QUERY, name, surName, patronymic, subject, id);

            if (status != 1) {
                throw new IllegalArgumentException("Row with input id doesnt exist");
            }

            return status;
        } catch (DataAccessException ex) {
            throw new DAOException("Cant update table professors", ex);
        }
    }

    public int remove(int professorId) {
        try {
            int status = jdbcTemplate.update(Queries.REMOVE_PROFESSOR_QUERY, professorId);

            if (status != 1) {
                throw new IllegalArgumentException("Row with input id doesnt exist");
            }

            return status;
        } catch (DataAccessException ex) {
            throw new DAOException("Cant remove element of table groups", ex);
        }
    }
}

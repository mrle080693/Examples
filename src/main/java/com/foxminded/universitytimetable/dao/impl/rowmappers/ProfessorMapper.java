package com.foxminded.universitytimetable.dao.impl.rowmappers;

import com.foxminded.universitytimetable.models.Professor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorMapper implements RowMapper<Professor> {
    public Professor mapRow(ResultSet resultSet, int i) throws SQLException {
        Professor professor = new Professor();

        professor.setId(resultSet.getInt("id"));
        professor.setName(resultSet.getString("name"));
        professor.setSurName(resultSet.getString("surName"));
        professor.setPatronymic(resultSet.getString("patronymic"));
        professor.setSubject(resultSet.getString("subject"));

        return professor;
    }
}

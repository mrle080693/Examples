package com.foxminded.universitytimetable.dao.impl.jdbctemplate.rowmappers;

import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonMapper implements RowMapper<Lesson> {
    public Lesson mapRow(ResultSet resultSet, int i) throws SQLException {
        Lesson lesson = new Lesson();

        lesson.setId(resultSet.getInt("id"));
        lesson.setDate(resultSet.getDate("date"));
        lesson.setLessonNumber(resultSet.getInt("lessonNumber"));
        lesson.setGroupId(resultSet.getInt("groupId"));
        lesson.setProfessorId(resultSet.getInt("professorId"));
        lesson.setBuilding(resultSet.getString("building"));
        lesson.setClassroom(resultSet.getString("classroom"));

        return lesson;
    }
}

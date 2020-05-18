package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.LessonDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.LessonMapper;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

@Repository("lessonImplBean")
public class LessonImpl implements LessonDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(Queries.ADD_LESSON_QUERY, new String[]{"id"});
                    ps.setDate(1, lesson.getDate());
                    ps.setInt(2, lesson.getLessonNumber());
                    ps.setInt(3, lesson.getGroupId());
                    ps.setInt(4, lesson.getProfessorId());
                    ps.setString(5, lesson.getBuilding());
                    ps.setString(6, lesson.getClassroom());
                    return ps;
                }
                , keyHolder);

        Number id = keyHolder.getKey();
        return (int) id;
    }

    public List<Lesson> getAll() {
        List<Lesson> lessons;

        lessons = jdbcTemplate.query(Queries.GET_ALL_LESSONS_QUERY, new LessonMapper());

        return lessons;
    }

    public Lesson getById(int id) {
        Lesson lesson;

        lesson = jdbcTemplate.queryForObject(Queries.GET_LESSON_BY_ID_QUERY, new Object[]{id}, new LessonMapper());

        return lesson;
    }

    public int update(Lesson lesson) {
        int id = lesson.getId();
        Date date = lesson.getDate();
        int lessonNumber = lesson.getLessonNumber();
        int groupId = lesson.getGroupId();
        int professorId = lesson.getProfessorId();
        String building = lesson.getBuilding();
        String classroom = lesson.getClassroom();

        int status = jdbcTemplate.update(Queries.UPDATE_LESSON_QUERY, date, lessonNumber, groupId, professorId,
                building, classroom, id);

        if (status != 1) {
            throw new IllegalArgumentException("Row with input id doesnt exist");
        }

        return status;
    }

    public int remove(int lessonId) {
        int status = jdbcTemplate.update(Queries.REMOVE_LESSON_QUERY, lessonId);

        if (status != 1) {
            throw new IllegalArgumentException("Row with input id doesnt exist");
        }

        return status;
    }
}

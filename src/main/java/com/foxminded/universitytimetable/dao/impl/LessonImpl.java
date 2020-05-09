package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.LessonDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.LessonMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("lessonImplBean")
public class LessonImpl implements LessonDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(Lesson lesson) {
        try {
            Date date = lesson.getDate();
            int lessonNumber = lesson.getLessonNumber();
            int groupId = lesson.getGroupId();
            int professorId = lesson.getProfessorId();
            String building = lesson.getBuilding();
            String classroom = lesson.getClassroom();

            jdbcTemplate.update(Queries.ADD_LESSON_QUERY, date, lessonNumber, groupId, professorId, building, classroom);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant add lesson", dae);
        }
    }

    public List<Lesson> getAll() {
        List<Lesson> lessons;

        try {
            lessons = jdbcTemplate.query(Queries.GET_ALL_LESSONS_QUERY, new LessonMapper());

        } catch (DataAccessException dae) {
            throw new DAOException("Cant get all from table lessons", dae);
        }

        return lessons;
    }

    public Lesson getById(int id) {
        Lesson lesson;

        try {
            lesson = jdbcTemplate.queryForObject(Queries.GET_LESSON_BY_ID_QUERY, new Object[]{id},
                    new LessonMapper());

        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundEntityException("Wrong id", e);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get by id from table lessons", dae);
        }

        return lesson;
    }

    public void update(Lesson lesson) {
        try {
            Date date = lesson.getDate();
            int lessonNumber = lesson.getLessonNumber();
            int groupId = lesson.getGroupId();
            int professorId = lesson.getProfessorId();
            String building = lesson.getBuilding();
            String classroom = lesson.getClassroom();

            jdbcTemplate.update(Queries.UPDATE_LESSON_QUERY, date, lessonNumber, groupId, professorId, building,
                    classroom);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant update table lessons", dae);
        }
    }

    public void remove(Lesson lesson) {
        try {
            jdbcTemplate.update(Queries.REMOVE_LESSON_QUERY, lesson.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant remove element of table groups", dae);
        }
    }
}

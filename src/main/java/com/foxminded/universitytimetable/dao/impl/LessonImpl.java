package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.LessonDAO;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LessonImpl implements LessonDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static long objectsCounter = 0;

    public void add(Lesson lesson) {

    }

    public List<Lesson> getAll() {
        return null;
    }

    public Lesson getById(int id) {
        return null;
    }

    public Lesson getByLessonNumber(int lessonNumber) {
        return null;
    }

    public void update(Lesson lesson) {

    }

    public void remove(Lesson lesson) {

    }
}

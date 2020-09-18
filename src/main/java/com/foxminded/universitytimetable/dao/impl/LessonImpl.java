package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.LessonDAO;
import com.foxminded.universitytimetable.dao.impl.repositories.LessonRepository;
import com.foxminded.universitytimetable.models.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lessonImplBean")
public class LessonImpl implements LessonDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonImpl.class);
    private static LessonRepository lessonRepository;

    @Override
    public int add(Lesson lesson) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add lesson " + lesson);
        }

        int id = lessonRepository.save(lesson).getId();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add lesson with id = " + id);
        }

        return id;
    }

    @Override
    public List<Lesson> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table lessons");
        }

        List<Lesson> lessons = lessonRepository.findAll();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + lessons);
        }

        return lessons;
    }

    @Override
    public Lesson getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get lesson by id = " + id);
        }

        Lesson lesson = lessonRepository.getOne(id);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + lesson);
        }

        return lesson;
    }

    @Override
    public int update(Lesson lesson) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update lesson: " + lesson);
        }

        int id = lessonRepository.save(lesson).getId();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update lesson with status = " + id);
        }

        return id;
    }

    @Override
    public int remove(int lessonId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove lesson with id = " + lessonId);
        }

        int status = lessonRepository.remove(lessonId);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove lesson with id: " + lessonId);
        }

        return status;
    }
}

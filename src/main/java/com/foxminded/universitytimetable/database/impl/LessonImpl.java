package com.foxminded.universitytimetable.database.impl;

import com.foxminded.universitytimetable.database.LessonDAO;
import com.foxminded.universitytimetable.database.impl.repositories.LessonRepository;
import com.foxminded.universitytimetable.models.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lessonImplBean")
public class LessonImpl implements LessonDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonImpl.class);
    private LessonRepository lessonRepository;

    @Autowired
    public LessonImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Lesson add(Lesson lesson) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add lesson " + lesson);
        }

        Lesson returnedLesson = lessonRepository.save(lesson);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add lesson with id = " + returnedLesson);
        }

        return returnedLesson;
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
    public Lesson update(Lesson lesson) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update lesson: " + lesson);
        }

        Lesson returnedLesson = lessonRepository.save(lesson);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update lesson with status = " + returnedLesson);
        }

        return returnedLesson;
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

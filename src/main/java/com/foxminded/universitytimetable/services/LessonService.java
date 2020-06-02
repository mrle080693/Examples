package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.dao.LessonDAO;
import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.EntityValidationException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service("lessonServiceBean")
public class LessonService {
    private final LessonDAO lessonDAO;
    private final GroupDAO groupDAO;
    private final ProfessorDAO professorDAO;
    private Logger LOGGER;

    @Autowired
    public LessonService(LessonDAO lessonDAO, GroupDAO groupDAO, ProfessorDAO professorDAO) {
        this.lessonDAO = lessonDAO;
        this.groupDAO = groupDAO;
        this.professorDAO = professorDAO;
        LOGGER = LoggerFactory.getLogger(LessonService.class);
    }

    public int add(Lesson lesson) {
        int lessonIdInTable;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add lesson " + lesson);
        }

        if (lesson == null) {
            String exMessage = "Lesson is null";
            IllegalArgumentException ex = new IllegalArgumentException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        try {
            checkLesson(lesson);

            if (lesson.getId() != 0) {
                String exMessage = "New lesson id is not 0. Actual value is: " + lesson.getId();
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }

            lessonIdInTable = lessonDAO.add(lesson);
        } catch (DataAccessException ex) {
            String exMessage = "Cant add lesson: " + lesson;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add lesson: " + lesson);
        }

        return lessonIdInTable;
    }

    public List<Lesson> getAll() {
        List<Lesson> lessons = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table lessons");
        }

        try {
            lessons = lessonDAO.getAll();

            if (lessons.isEmpty()) {
                String exMessage = "Table lessons is empty";
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant get all from table lessons: " + lessons;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + lessons);
        }

        return lessons;
    }

    public Lesson getById(int id) {
        Lesson lesson = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get lesson by id = " + id);
        }

        try {
            if (id == 0) {
                String exMessage = "Lesson id is 0. " + lesson;
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }

            lesson = lessonDAO.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            String exMessage = "Table lessons have not lessons with id = " + id;
            LOGGER.warn(exMessage);
            throw new NotFoundEntityException(exMessage, ex);
        } catch (DataAccessException ex) {
            String exMessage = "Cant get lesson by id from DB with id = " + id;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + lesson);
        }

        return lesson;
    }

    public int update(Lesson lesson) {
        int status;

        if (lesson == null) {
            String exMessage = "Lesson is null ";
            IllegalArgumentException ex = new IllegalArgumentException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update lesson: " + lesson);
        }

        try {
            checkLesson(lesson);

            if (lesson.getId() == 0) {
                String exMessage = "Lesson id is 0. " + lesson;
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }

            status = lessonDAO.update(lesson);

            if (status != 1) {
                String exMessage = "Lesson with input id doesnt exist. Id is: " + lesson.getId();
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant update table lessons. Input lesson: " + lesson;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update lesson: " + lesson);
        }

        return status;
    }

    public int remove(int lessonId) {
        int status;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove lesson with id = " + lessonId);
        }

        try {
            status = lessonDAO.remove(lessonId);

            if (status != 1) {
                String exMessage = "Lesson with id: " + lessonId + " does not exist";
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant remove from table lessons. Group id: " + lessonId;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove lesson with id: " + lessonId);
        }

        return status;
    }

    private void checkLesson(Lesson lesson) {
        Date date = lesson.getDate();
        int lessonNumber = lesson.getLessonNumber();
        int groupId = lesson.getGroupId();
        int professorId = lesson.getProfessorId();
        String building = lesson.getBuilding();
        String classroom = lesson.getClassroom();

        Date todayDate = new Date(Calendar.getInstance().getTime().getTime());

        if (date == null || building == null || classroom == null) {
            String exMessage = "Lesson date, building or classroom is null: " + lesson;
            EntityValidationException ex = new EntityValidationException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        if (building.trim().isEmpty() || classroom.trim().isEmpty()) {
            String exMessage = "Lesson building or classroom is empty: " + lesson;
            EntityValidationException ex = new EntityValidationException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        if (lessonNumber == 0 || groupId == 0 || professorId == 0) {
            String exMessage = "lessonNumber, groupId or professorId is zero: " + lesson;
            EntityValidationException ex = new EntityValidationException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        if (date.after(todayDate)) {
            String exMessage = "Lesson date is earlier then today: " + lesson;
            EntityValidationException ex = new EntityValidationException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        try {
            groupDAO.getById(groupId);
        } catch (EmptyResultDataAccessException ex) {
            String exMessage = "Try add lesson to group which not exists: " + lesson;
            LOGGER.error(exMessage);
            throw new NotFoundEntityException(exMessage, ex);
        }

        try {
            professorDAO.getById(professorId);
        } catch (EmptyResultDataAccessException ex) {
            String exMessage = "Try add lesson to professor which not exists: " + lesson;
            LOGGER.error(exMessage);
            throw new NotFoundEntityException(exMessage, ex);
        }
    }
}

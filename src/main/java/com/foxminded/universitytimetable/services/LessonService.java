package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.EntityValidationException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service("lessonServiceBean")
public class LessonService {
    private final LessonImpl lessonImpl;
    private final GroupImpl groupImpl;
    private final ProfessorImpl professorImpl;

    @Autowired
    public LessonService(LessonImpl lessonImpl, GroupImpl groupImpl, ProfessorImpl professorImpl) {
        this.lessonImpl = lessonImpl;
        this.groupImpl = groupImpl;
        this.professorImpl = professorImpl;
    }


    public int add(Lesson lesson) {
        int lessonIdInTable;

        try {
            checkLesson(lesson);

            if (lesson.getId() != 0) {
                throw new EntityValidationException("New lesson id must be 0. \n" +
                        "If you want update lesson you have to use update method");
            }

            lessonIdInTable = lessonImpl.add(lesson);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant add lesson", ex);
        }

        return lessonIdInTable;
    }

    public List<Lesson> getAll() {
        List<Lesson> lessons;

        try {
            lessons = lessonImpl.getAll();

            if (lessons.isEmpty()) {
                throw new NotFoundEntityException("Table lessons is empty");
            }
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get all from table lessons", ex);
        }

        return lessons;
    }

    public Lesson getById(int id) {
        Lesson lesson;

        try {
            if (id == 0) {
                throw new EntityValidationException("Lesson id cant be 0");
            }

            lesson = lessonImpl.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Table lessons have not rows with input id", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get by id from table lessons", ex);
        }

        return lesson;
    }

    public int update(Lesson lesson) {
        int status;

        try {
            checkLesson(lesson);

            if (lesson.getId() == 0) {
                throw new EntityValidationException("New lesson id must not be 0. \n" +
                        "If you want add new lesson you have to use add method");
            }

            status = lessonImpl.update(lesson);

            if (status != 1) {
                throw new NotFoundEntityException("Lesson with input id doesnt exist");
            }
        } catch (DataAccessException ex) {
            throw new DAOException("Cant update table lessons", ex);
        }

        return status;
    }

    public int remove(int lessonId) {
        int status;

        try {
            status = lessonImpl.remove(lessonId);

            if (status != 1) {
                throw new NotFoundEntityException("Lesson with input id doesnt exist");
            }
        } catch (DataAccessException ex) {
            throw new DAOException("Cant remove element of table lessons", ex);
        }

        return status;
    }

    private void checkLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson for add or update cant be null");
        }

        Date date = lesson.getDate();
        int lessonNumber = lesson.getLessonNumber();
        int groupId = lesson.getGroupId();
        int professorId = lesson.getProfessorId();
        String building = lesson.getBuilding();
        String classroom = lesson.getClassroom();

        Date todayDate = new Date(Calendar.getInstance().getTime().getTime());

        if (date == null || building == null || classroom == null) {
            throw new EntityValidationException("Lesson date, building, classroom cant be null");
        }

        if (building.trim().isEmpty() || classroom.trim().isEmpty()) {
            throw new EntityValidationException("Lesson date, building, classroom cant be null");
        }

        if (lessonNumber == 0 || groupId == 0 || professorId == 0) {
            throw new EntityValidationException("lessonNumber, groupId, professorId cant be zero");
        }

        if (date.after(todayDate)) {
            throw new EntityValidationException("Lesson date for add or update cant be earlier then today");
        }

        try {
            groupImpl.getById(groupId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("You try add lesson to group which not exists", ex);
        }

        try {
            professorImpl.getById(professorId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("You try add lesson to professor which not exist", ex);
        }
    }
}

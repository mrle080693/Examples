package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.dao.impl.StatisticsImpl;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
    private StatisticsImpl statisticsImpl = context.getBean("statisticsImplBean", StatisticsImpl.class);
    private GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);
    private ProfessorImpl professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);
    private LessonImpl lessonImpl = context.getBean("lessonImplBean", LessonImpl.class);

    private Group group = new Group("Test");
    private Professor professor = new Professor("Name", "Surname", "Patronymic", "Math");
    private Lesson lesson = new Lesson(new Date(2000, 1, 1), 1, 1,
            1, "Building", "Classroom");

    private Date from = new Date(1919, 11, 11);
    private Date till = new Date(2020, 11, 11);

    @BeforeEach
    void dataSet() {
        groupImpl.add(group);
        professorImpl.add(professor);
    }

    // getGroupEmployment method
    @Test
    void getGroupEmploymentMustThrowNotFoundEntityExceptionIfGroupIsNotExists() {
        groupImpl.remove(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> statisticsImpl.getGroupEmployment(1,
                from, till));
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessons() {
        int expected = 0;
        int actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        int expected = 0;
        int actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectResult() {
        lessonImpl.add(lesson);

        int expected = 1;
        int actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectResultIfGroupHaveManyLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    // getProfessorEmployment method
    @Test
    void getProfessorEmploymentMustThrowNotFoundEntityExceptionIfProfessorIsNotExists() {
        professorImpl.remove(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> statisticsImpl.getProfessorEmployment(1,
                from, till));
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessons() {
        int expected = 0;
        int actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        int expected = 0;
        int actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectResult() {
        lessonImpl.add(lesson);

        int expected = 1;
        int actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectResultIfProfessorHaveManyLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }
}

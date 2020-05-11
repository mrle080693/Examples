package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.*;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimetableImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
    private TimetableImpl timetableImpl = context.getBean("timetableImplBean", TimetableImpl.class);
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
    void getGroupTimetableMustThrowNotFoundEntityExceptionIfGroupIsNotExists() {
        groupImpl.remove(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> timetableImpl.getGroupTimetable(1,
                from, till));
    }

    @Test
    void getGroupTimetableMustReturnZeroIfGroupHaveNotLessons() {
        int expected = 0;
        int actual = timetableImpl.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        int expected = 0;
        int actual = timetableImpl.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessonsQuantity() {
        lessonImpl.add(lesson);

        int expected = 1;
        int actual = timetableImpl.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = timetableImpl.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        List<Lesson> lessons = timetableImpl.getGroupTimetable(1, from, till);

        for (Lesson lessonFromDB : lessons) {
            Date expectedDate = lesson.getDate();
            Date actualDate = lessonFromDB.getDate();
            assertEquals(expectedDate, actualDate);

            int expectedLessonNumber = lesson.getLessonNumber();
            int actualLessonNumber = lessonFromDB.getLessonNumber();
            assertEquals(expectedLessonNumber, actualLessonNumber);

            int expectedGroupId = lesson.getGroupId();
            int actualGroupId = lessonFromDB.getGroupId();
            assertEquals(expectedGroupId, actualGroupId);

            int expectedProfessorId = lesson.getProfessorId();
            int actualProfessorId = lessonFromDB.getProfessorId();
            assertEquals(expectedProfessorId, actualProfessorId);

            String expectedBuilding = lesson.getBuilding();
            String actualBuilding = lessonFromDB.getBuilding();
            assertEquals(expectedBuilding, actualBuilding);

            String expectedClassroom = lesson.getClassroom();
            String actualClassroom = lessonFromDB.getClassroom();
            assertEquals(expectedClassroom, actualClassroom);
        }
    }

    // getProfessorEmployment method
    @Test
    void getProfessorTimetableMustThrowNotFoundEntityExceptionIfProfessorIsNotExists() {
        professorImpl.remove(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> timetableImpl.getProfessorTimetable(1,
                from, till));
    }

    @Test
    void getProfessorTimetableMustReturnZeroIfProfessorHaveNotLessons() {
        int expected = 0;
        int actual = timetableImpl.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        int expected = 0;
        int actual = timetableImpl.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessonsQuantity() {
        lessonImpl.add(lesson);

        int expected = 1;
        int actual = timetableImpl.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = timetableImpl.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        List<Lesson> lessons = timetableImpl.getProfessorTimetable(1, from, till);

        for (Lesson lessonFromDB : lessons) {
            Date expectedDate = lesson.getDate();
            Date actualDate = lessonFromDB.getDate();
            assertEquals(expectedDate, actualDate);

            int expectedLessonNumber = lesson.getLessonNumber();
            int actualLessonNumber = lessonFromDB.getLessonNumber();
            assertEquals(expectedLessonNumber, actualLessonNumber);

            int expectedGroupId = lesson.getGroupId();
            int actualGroupId = lessonFromDB.getGroupId();
            assertEquals(expectedGroupId, actualGroupId);

            int expectedProfessorId = lesson.getProfessorId();
            int actualProfessorId = lessonFromDB.getProfessorId();
            assertEquals(expectedProfessorId, actualProfessorId);

            String expectedBuilding = lesson.getBuilding();
            String actualBuilding = lessonFromDB.getBuilding();
            assertEquals(expectedBuilding, actualBuilding);

            String expectedClassroom = lesson.getClassroom();
            String actualClassroom = lessonFromDB.getClassroom();
            assertEquals(expectedClassroom, actualClassroom);
        }
    }
}

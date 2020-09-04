package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.dao.impl.TimetableImpl;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimetableImplTest {
    private DataSource dataSource;
    private static AnnotationConfigApplicationContext context;

    private static TimetableImpl timetableImpl;
    private static LessonImpl lessonImpl;
    private static ProfessorImpl professorImpl;
    private static GroupImpl groupImpl;

    private Lesson lesson;
    private Professor professor;
    private Group group;

    private static Date from;
    private static Date till;

    @BeforeAll
    static void initialize() {
        context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);

        timetableImpl = context.getBean("timetableImplBean", TimetableImpl.class);
        lessonImpl = context.getBean("lessonImplBean", LessonImpl.class);
        professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);
        groupImpl = context.getBean("groupImplBean", GroupImpl.class);

        from = new Date(1919, 11, 11);
        till = new Date(2020, 11, 11);
    }

    @BeforeEach
    void dataSet() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("test.sql")
                .build();

        lesson = new Lesson(new Date(1912, 12, 12), 1, 1, 1,
                "Building", "Classroom");
        professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        group = new Group("Group");

        professorImpl.add(professor);
        groupImpl.add(group);
        lessonImpl.add(lesson);
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
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = timetableImpl.getGroupTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = timetableImpl.getGroupTimetable(1, dateFrom, till).size();

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
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = timetableImpl.getProfessorTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = timetableImpl.getProfessorTimetable(1, dateFrom, till).size();

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

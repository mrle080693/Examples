package com.foxminded.universitytimetable.dao.jdbctemplate;

import com.foxminded.universitytimetable.dao.impl.jdbctemplate.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.ProfessorImpl;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LessonImplTest {
    private static LessonImpl lessonImpl;
    private Lesson lesson;

    @BeforeEach
    void dataSet() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("test.sql")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        lessonImpl = new LessonImpl(jdbcTemplate);
        ProfessorImpl professorImpl = new ProfessorImpl(jdbcTemplate);
        GroupImpl groupImpl = new GroupImpl(jdbcTemplate);

        lesson = new Lesson(new Date(1212, 12, 12), 1, 1, 1,
                "Building", "Classroom");
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        Group group = new Group("Group");

        professorImpl.add(professor);
        groupImpl.add(group);
        lessonImpl.add(lesson);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        int id = lessonImpl.add(lesson);

        assertTrue(id > 0);
    }

    @Test
    void addMustAddLessonWithCorrectDate() {
        Date expected = lesson.getDate();
        Date actual = lessonImpl.getById(1).getDate();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectLessonNumber() {
        int expected = lesson.getLessonNumber();
        int actual = lessonImpl.getById(1).getLessonNumber();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectGroupId() {
        int expected = lesson.getGroupId();
        int actual = lessonImpl.getById(1).getGroupId();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectProfessorId() {
        int expected = lesson.getProfessorId();
        int actual = lessonImpl.getById(1).getProfessorId();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectBuiding() {
        String expected = lesson.getBuilding();
        String actual = lessonImpl.getById(1).getBuilding();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectClassroom() {
        String expected = lesson.getClassroom();
        String actual = lessonImpl.getById(1).getClassroom();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        lessonImpl.remove(1);

        int expected = 0;
        int actual = lessonImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 100; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 100;
        int actual = lessonImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustReturnCorrectLesson() {
        Lesson lessonFromDB = lessonImpl.getById(1);

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

    @Test
    void updateMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputLessonId() {
        lesson.setId(33);

        Assertions.assertThrows(IllegalArgumentException.class, () -> lessonImpl.update(lesson));
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputLessonId() {
        lesson.setId(1);
        lesson.setClassroom("Updated");
        lessonImpl.update(lesson);

        String expected = "Updated";
        String actual = lessonImpl.getById(1).getClassroom();

        assertEquals(expected, actual);
    }

    // Candidate to move to the service
    @Test
    void removeMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputLessonId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> lessonImpl.remove(2));
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputLessonId() {
        int rowsQuantityBeforeRemove = lessonImpl.getAll().size();

        lessonImpl.remove(1);
        int rowsQuantityAfterRemove = lessonImpl.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}

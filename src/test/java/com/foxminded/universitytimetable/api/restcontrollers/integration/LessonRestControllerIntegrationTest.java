package com.foxminded.universitytimetable.api.restcontrollers.integration;

import com.foxminded.universitytimetable.api.restcontrollers.LessonRestController;
import com.foxminded.universitytimetable.database.GroupDAO;
import com.foxminded.universitytimetable.database.LessonDAO;
import com.foxminded.universitytimetable.database.ProfessorDAO;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LessonRestControllerIntegrationTest {
    private LessonDAO lessonDAO;
    private GroupDAO groupDAO;
    private ProfessorDAO professorDAO;
    private LessonService lessonService;
    private LessonRestController lessonRestController;
    // For forge database connection troubles
    private LessonDAO wrongLessonDao = null;

    private Date DATE = new Date(Calendar.getInstance().getTime().getTime());
    private final int LESSON_NUMBER = 1;
    private final int GROUP_ID = 1;
    private final int PROFESSOR_ID = 1;
    private final String BUILDING = "NOT";
    private final String CLASSROOM = "Be Confused";

    @BeforeEach
    void dataSet(@Autowired LessonDAO lessonDAO, @Autowired GroupDAO groupDAO, @Autowired ProfessorDAO professorDAO) {
        this.lessonDAO = lessonDAO;
        this.groupDAO = groupDAO;
        this.professorDAO = professorDAO;
        lessonService = new LessonService(lessonDAO, professorDAO, groupDAO);
        lessonRestController = new LessonRestController(lessonService);
    }

    @Test
    void addHaveToAddObjectToDB() {
        lessonRestController.add(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);

        int expected = 1;
        int actual = lessonDAO.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void addHaveToReturnCorrectResult() {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lesson.setId(1);

        String expected = lesson.toString();
        String actual = lessonService.add(new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM)).toString();

        assertEquals(expected, actual);
    }

    @Test
    void addHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> lessonRestController.add(DATE, LESSON_NUMBER, GROUP_ID,
                PROFESSOR_ID, BUILDING, null));
        try {
            lessonRestController.add(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, null);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        lessonService = new LessonService(wrongLessonDao, professorDAO, groupDAO);
        lessonRestController = new LessonRestController(lessonService);
        assertThrows(Exception.class, () -> lessonRestController.add(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID,
                BUILDING, CLASSROOM));
        try {
            lessonRestController.add(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getAllHaveToReturnCorrectResult() {
        List<Lesson> expectedLessons = new ArrayList<>();
        Lesson lesson;
        for (int i = 1; i <= 10; i++) {
            lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
            lesson.setId(i);
            expectedLessons.add(lesson);
        }


        for (int i = 1; i <= 10; i++) {
            lessonRestController.add(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        }
        List<Lesson> actualLessons = lessonRestController.getAll();


        String expected = expectedLessons.toString();
        String actual = actualLessons.toString();
        assertEquals(expected, actual);
    }

    @Test
    void getAllHaveToThrowCorrectException() {
        lessonService = new LessonService(wrongLessonDao, professorDAO, groupDAO);
        lessonRestController = new LessonRestController(lessonService);
        assertThrows(Exception.class, () -> lessonRestController.getAll());
        try {
            lessonRestController.getAll();
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getByIdHaveToReturnCorrectResult() {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonDAO.add(lesson);

        lesson.setId(1);
        String expected = lesson.toString();
        String actual = lessonRestController.getById(1).toString();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> lessonRestController.getById(-12));
        try {
            lessonRestController.getById(-12);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        lessonService = new LessonService(wrongLessonDao, professorDAO, groupDAO);
        lessonRestController = new LessonRestController(lessonService);
        assertThrows(Exception.class, () -> lessonRestController.getById(1));
        try {
            lessonRestController.getById(1);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void updateHaveToUpdateObjectInDB() {
        lessonRestController.add(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonRestController.update(1, DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, "Updated");

        String expected = "Updated";
        String actual = lessonDAO.getById(1).getClassroom();

        assertEquals(expected, actual);
    }

    @Test
    void updateHaveToReturnCorrectResult() {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonDAO.add(lesson);

        String expected = "newName";
        String actual = lessonRestController.update(1, DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING,
                "newName").getClassroom();

        assertEquals(expected, actual);
    }

    @Test
    void updateHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> lessonRestController.update(1, null, LESSON_NUMBER,
                GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM));
        try {
            lessonRestController.update(1, null, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        lessonService = new LessonService(wrongLessonDao, professorDAO, groupDAO);
        lessonRestController = new LessonRestController(lessonService);
        assertThrows(Exception.class, () -> lessonRestController.update(1, DATE, LESSON_NUMBER, GROUP_ID,
                PROFESSOR_ID, BUILDING, CLASSROOM));
        try {
            lessonRestController.update(1, DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void removeHaveToDeleteObjectFromDB() {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonDAO.add(lesson);
        // Check if group was added
        int expected = 1;
        int actual = lessonDAO.getAll().size();
        assertEquals(expected, actual);

        // Check group was deleted
        lessonRestController.remove(1);
        expected = 0;
        actual = lessonDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeHaveToReturnCorrectResult() {
        Lesson lesson = new Lesson(DATE, LESSON_NUMBER, GROUP_ID, PROFESSOR_ID, BUILDING, CLASSROOM);
        lessonDAO.add(lesson);
        lesson.setId(1);
        int expected = 1;
        int actual = lessonRestController.remove(1);

        assertEquals(expected, actual);
    }

    @Test
    void removeHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> lessonRestController.remove(-9));
        try {
            lessonRestController.remove(-5);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        lessonService = new LessonService(wrongLessonDao, professorDAO, groupDAO);
        lessonRestController = new LessonRestController(lessonService);
        assertThrows(Exception.class, () -> lessonRestController.remove(1));
        try {
            lessonRestController.remove(1);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }
}

package com.foxminded.universitytimetable.api.restcontrollers.integration;

import com.foxminded.universitytimetable.api.restcontrollers.TimetableRestController;
import com.foxminded.universitytimetable.database.LessonDAO;
import com.foxminded.universitytimetable.database.TimetableDAO;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.GroupService;
import com.foxminded.universitytimetable.services.ProfessorService;
import com.foxminded.universitytimetable.services.TimetableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TimetableRestControllerIntegrationTest {
    private TimetableService timetableService;
    private TimetableRestController timetableRestController;
    private LessonDAO lessonDAO;
    private GroupService groupService;
    private ProfessorService professorService;
    // For forge database connection troubles
    private TimetableDAO wrongTimetableDao = null;

    private final Date FROM = new Date(Calendar.getInstance().getTime().getTime() - 999999999);
    private final Date TILL = new Date(Calendar.getInstance().getTime().getTime() + 999999999);
    private Lesson lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1, 1,
            1, "Any", "Any");

    @BeforeEach
    void dataSet(@Autowired TimetableDAO timetableDAO, @Autowired LessonDAO lessonDAO,
                 @Autowired GroupService groupService, @Autowired ProfessorService professorService) {
        this.lessonDAO = lessonDAO;
        this.groupService = groupService;
        this.professorService = professorService;
        timetableService = new TimetableService(timetableDAO, groupService, professorService);
        timetableRestController = new TimetableRestController(timetableService);

        Group group = new Group("Test");
        groupService.add(group);
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        professorService.add(professor);
    }

    @Test
    void getGroupTimetableHaveToReturnCorrectResult() {
        List<Lesson> lessons = timetableService.getGroupTimetable(1, FROM, TILL);
        long expected = 0;
        long actual = lessons.size();
        assertEquals(expected, actual);

        lessonDAO.add(lesson);

        lessons = timetableService.getGroupTimetable(1, FROM, TILL);
        expected = 1;
        actual = lessons.size();
        assertEquals(expected, actual);

        lesson.setId(1);
        String expectedStr = lesson.toString();
        String actualStr = lessons.get(0).toString();
        assertEquals(expectedStr, actualStr);
    }

    @Test
    void getGroupTimetableHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> timetableRestController.getGroupTimetable(-12,
                FROM, TILL));
        assertThrows(ResponseStatusException.class, () -> timetableRestController.getGroupTimetable(1,
                null, TILL));
        try {
            timetableRestController.getGroupTimetable(-12, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        try {
            timetableRestController.getGroupTimetable(1, null, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        timetableService = new TimetableService(wrongTimetableDao, groupService, professorService);
        timetableRestController = new TimetableRestController(timetableService);
        assertThrows(Exception.class, () -> timetableRestController.getGroupTimetable(1, FROM, TILL));
        try {
            timetableRestController.getGroupTimetable(1, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getProfessorTimetableHaveToReturnCorrectResult() {
        List<Lesson> lessons = timetableService.getProfessorTimetable(1, FROM, TILL);
        long expected = 0;
        long actual = lessons.size();
        assertEquals(expected, actual);

        lessonDAO.add(lesson);

        lessons = timetableService.getProfessorTimetable(1, FROM, TILL);
        expected = 1;
        actual = lessons.size();
        assertEquals(expected, actual);

        lesson.setId(1);
        String expectedStr = lesson.toString();
        String actualStr = lessons.get(0).toString();
        assertEquals(expectedStr, actualStr);
    }

    @Test
    void getProfessorTimetableHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> timetableRestController.getProfessorTimetable(-12,
                FROM, TILL));
        assertThrows(ResponseStatusException.class, () -> timetableRestController.getProfessorTimetable(1,
                null, TILL));
        try {
            timetableRestController.getProfessorTimetable(-12, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        try {
            timetableRestController.getProfessorTimetable(1, null, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        timetableService = new TimetableService(wrongTimetableDao, groupService, professorService);
        timetableRestController = new TimetableRestController(timetableService);
        assertThrows(Exception.class, () -> timetableRestController.getProfessorTimetable(1, FROM, TILL));
        try {
            timetableRestController.getProfessorTimetable(1, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }
}

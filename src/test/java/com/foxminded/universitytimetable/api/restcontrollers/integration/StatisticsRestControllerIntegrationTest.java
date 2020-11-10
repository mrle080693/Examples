package com.foxminded.universitytimetable.api.restcontrollers.integration;

import com.foxminded.universitytimetable.api.restcontrollers.StatisticsRestController;
import com.foxminded.universitytimetable.database.LessonDAO;
import com.foxminded.universitytimetable.database.StatisticsDAO;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.GroupService;
import com.foxminded.universitytimetable.services.ProfessorService;
import com.foxminded.universitytimetable.services.StatisticsService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StatisticsRestControllerIntegrationTest {
    private StatisticsService statisticsService;
    private StatisticsRestController statisticsRestController;
    private LessonDAO lessonDAO;
    private GroupService groupService;
    private ProfessorService professorService;
    // For forge database connection troubles
    private StatisticsDAO wrongStatisticsDao = null;

    private final Date FROM = new Date(Calendar.getInstance().getTime().getTime() - 999999999);
    private final Date TILL = new Date(Calendar.getInstance().getTime().getTime() + 999999999);
    private Lesson lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1, 1,
            1, "Any", "Any");

    @BeforeEach
    void dataSet(@Autowired StatisticsDAO statisticsDAO, @Autowired LessonDAO lessonDAO,
                 @Autowired GroupService groupService, @Autowired ProfessorService professorService) {
        this.lessonDAO = lessonDAO;
        this.groupService = groupService;
        this.professorService = professorService;
        statisticsService = new StatisticsService(statisticsDAO, groupService, professorService);
        statisticsRestController = new StatisticsRestController(statisticsService);

        Group group = new Group("Test");
        groupService.add(group);
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        professorService.add(professor);
    }

    @Test
    void getGroupEmploymentHaveToReturnCorrectResult() {
        long expected = 0;
        long actual = statisticsRestController.getGroupEmployment(1, FROM, TILL);
        assertEquals(expected, actual);

        lessonDAO.add(lesson);

        expected = 1;
        actual = statisticsRestController.getGroupEmployment(1, FROM, TILL);
        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> statisticsRestController.getGroupEmployment(-12, FROM,
                TILL));
        assertThrows(ResponseStatusException.class, () -> statisticsRestController.getGroupEmployment(1, null,
                TILL));
        try {
            statisticsRestController.getGroupEmployment(-12, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        try {
            statisticsRestController.getGroupEmployment(1, null, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        statisticsService = new StatisticsService(wrongStatisticsDao, groupService, professorService);
        statisticsRestController = new StatisticsRestController(statisticsService);
        assertThrows(Exception.class, () -> statisticsRestController.getGroupEmployment(1, FROM, TILL));
        try {
            statisticsRestController.getGroupEmployment(1, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getProfessorEmploymentHaveToReturnCorrectResult() {
        long expected = 0;
        long actual = statisticsService.getProfessorEmployment(1, FROM, TILL);
        assertEquals(expected, actual);

        lessonDAO.add(lesson);

        expected = 1;
        actual = statisticsService.getProfessorEmployment(1, FROM, TILL);
        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> statisticsRestController.getProfessorEmployment(-12, FROM,
                TILL));
        assertThrows(ResponseStatusException.class, () -> statisticsRestController.getProfessorEmployment(1, null,
                TILL));
        try {
            statisticsRestController.getProfessorEmployment(-12, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.
                    getStatus().
                    value();

            assertEquals(expected, actual);
        }

        try {
            statisticsRestController.getProfessorEmployment(1, null, TILL);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        statisticsService = new StatisticsService(wrongStatisticsDao, groupService, professorService);
        statisticsRestController = new StatisticsRestController(statisticsService);
        assertThrows(Exception.class, () -> statisticsRestController.getProfessorEmployment(1, FROM, TILL));
        try {
            statisticsRestController.getProfessorEmployment(1, FROM, TILL);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }
}

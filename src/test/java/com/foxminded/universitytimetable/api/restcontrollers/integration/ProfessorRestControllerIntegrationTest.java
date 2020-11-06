package com.foxminded.universitytimetable.api.restcontrollers.integration;

import com.foxminded.universitytimetable.api.restcontrollers.ProfessorRestController;
import com.foxminded.universitytimetable.database.ProfessorDAO;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProfessorRestControllerIntegrationTest {
    private ProfessorDAO professorDAO;
    private ProfessorService professorService;
    private ProfessorRestController professorRestController;
    // For forge database connection troubles
    private ProfessorDAO wrongProfessorDao = null;

    private final String NAME = "TestName";
    private final String SURNAME = "TestSurname";
    private final String PATRONYMIC = "TestPatronymic";
    private final String SUBJECT = "TestSubject";

    @BeforeEach
    void dataSet(@Autowired ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
        professorService = new ProfessorService(professorDAO);
        professorRestController = new ProfessorRestController(professorService);
    }

    @Test
    void addHaveToAddObjectToDB() {
        professorRestController.add(NAME, SURNAME, PATRONYMIC, SUBJECT);

        int expected = 1;
        int actual = professorDAO.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void addHaveToReturnCorrectResult() {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professor.setId(1);

        String expected = professor.toString();
        String actual = professorRestController.add(NAME, SURNAME, PATRONYMIC, SUBJECT).toString();

        assertEquals(expected, actual);
    }

    @Test
    void addHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> professorRestController.add(null, SURNAME, PATRONYMIC,
                SUBJECT));
        try {
            professorRestController.add(null, SURNAME, PATRONYMIC, SUBJECT);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        professorService = new ProfessorService(wrongProfessorDao);
        professorRestController = new ProfessorRestController(professorService);
        assertThrows(Exception.class, () -> professorRestController.add(NAME, SURNAME, PATRONYMIC, SUBJECT));
        try {
            professorRestController.add(NAME, SURNAME, PATRONYMIC, SUBJECT);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getAllHaveToReturnCorrectResult() {
        List<Professor> expectedProfessors = new ArrayList<>();
        Professor professor;
        for (int i = 1; i <= 10; i++) {
            professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
            professor.setId(i);
            expectedProfessors.add(professor);
        }


        for (int i = 1; i <= 10; i++) {
            professorRestController.add(NAME, SURNAME, PATRONYMIC, SUBJECT);
        }
        List<Professor> actualProfessors = professorRestController.getAll();


        String expected = expectedProfessors.toString();
        String actual = actualProfessors.toString();
        assertEquals(expected, actual);
    }

    @Test
    void getAllHaveToThrowCorrectException() {
        professorService = new ProfessorService(wrongProfessorDao);
        professorRestController = new ProfessorRestController(professorService);
        assertThrows(Exception.class, () -> professorRestController.getAll());
        try {
            professorRestController.getAll();
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getByIdHaveToReturnCorrectResult() {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);

        professor.setId(1);
        String expected = professor.toString();
        String actual = professorRestController.getById(1).toString();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> professorRestController.getById(-12));
        try {
            professorRestController.getById(-12);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        professorService = new ProfessorService(wrongProfessorDao);
        professorRestController = new ProfessorRestController(professorService);
        assertThrows(Exception.class, () -> professorRestController.getById(1));
        try {
            professorRestController.getById(1);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void getBySurnameHaveToReturnCorrectResult() {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);

        Professor expectedProfessor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        expectedProfessor.setId(1);
        String expected = expectedProfessor.toString();
        String actual = professorRestController.getBySurname(SURNAME).get(0).toString();

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> professorRestController.getBySurname(null));
        try {
            professorRestController.getBySurname(null);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        professorService = new ProfessorService(wrongProfessorDao);
        professorRestController = new ProfessorRestController(professorService);
        assertThrows(Exception.class, () -> professorRestController.getBySurname("Random"));
        try {
            professorRestController.getBySurname("Random");
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void updateHaveToUpdateObjectInDB() {
        professorRestController.add(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorRestController.update(1, "newName", SURNAME, PATRONYMIC, SUBJECT);

        String expected = "newName";
        String actual = professorDAO.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void updateHaveToReturnCorrectResult() {
        professorRestController.add(NAME, SURNAME, PATRONYMIC, SUBJECT);

        String expected = "newName";
        String actual = professorRestController.update(1, "newName", SURNAME, PATRONYMIC, SUBJECT).getName();

        assertEquals(expected, actual);
    }

    @Test
    void updateHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> professorRestController.update(1, null, SURNAME,
                PATRONYMIC, SUBJECT));
        try {
            professorRestController.update(1, null, SURNAME, PATRONYMIC, SUBJECT);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        professorService = new ProfessorService(wrongProfessorDao);
        professorRestController = new ProfessorRestController(professorService);
        assertThrows(Exception.class, () -> professorRestController.update(1, NAME, SURNAME, PATRONYMIC, SUBJECT));
        try {
            professorRestController.update(1, NAME, SURNAME, PATRONYMIC, SUBJECT);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }

    @Test
    void removeHaveToDeleteObjectFromDB() {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);
        // Check if group was added
        int expected = 1;
        int actual = professorDAO.getAll().size();
        assertEquals(expected, actual);

        // Check group was deleted
        professorRestController.remove(1);
        expected = 0;
        actual = professorDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeHaveToReturnCorrectResult() {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);
        professor.setId(1);
        int expected = 1;
        int actual = professorRestController.remove(1);

        assertEquals(expected, actual);
    }

    @Test
    void removeHaveToThrowCorrectException() {
        assertThrows(ResponseStatusException.class, () -> professorRestController.remove(-9));
        try {
            professorRestController.remove(-5);
        } catch (ResponseStatusException e) {
            int expected = 400;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }

        professorService = new ProfessorService(wrongProfessorDao);
        professorRestController = new ProfessorRestController(professorService);
        assertThrows(Exception.class, () -> professorRestController.remove(1));
        try {
            professorRestController.remove(1);
        } catch (ResponseStatusException e) {
            int expected = 500;
            int actual = e.getStatus().value();

            assertEquals(expected, actual);
        }
    }
}

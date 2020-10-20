package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.database.impl.repositories.ProfessorRepository;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfessorRepositoryTest {
    @Autowired
    private ProfessorRepository professorRepository;
    private Professor professor = new Professor("Test", "Test", "Test", "Test");

    @Test
    void addMustAddProfessorWithCorrectName() {
        professorRepository.save(professor);
        Professor professorFromDB = professorRepository.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getName();
        String actual = professorFromDB.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSurname() {
        professorRepository.save(professor);
        Professor professorFromDB = professorRepository.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getSurname();
        String actual = professorFromDB.getSurname();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectPatronymic() {
        professorRepository.save(professor);
        Professor professorFromDB = professorRepository.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getPatronymic();
        String actual = professorFromDB.getPatronymic();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSubjectName() {
        professorRepository.save(professor);
        Professor professorFromDB = professorRepository.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getSubject();
        String actual = professorFromDB.getSubject();

        assertEquals(expected, actual);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        Professor professor1 = professorRepository.save(professor);
        Professor id = professor1.getId();

        assertTrue(id > 0);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        professorRepository.remove(1);

        int expected = 0;
        int actual = professorRepository.findAll().size();

        assertEquals(expected, actual);
    }

    // Single running test
    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 0; index < 100; index++) {
            professor.setId(index + 1);
            professorRepository.save(professor);
        }

        int expected = 100;
        int actual = professorRepository.findAll().size();

        assertEquals(expected, actual);
    }

    // Single running test
    @Test
    void getByIdMustReturnProfessorWithCorrectName() {
        professorRepository.save(professor);

        String expected = "Test";
        String actual = professorRepository.getOne(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameMustReturnEmptyListIfTableIsNotContainsProfessorWithSuchSurname() {
        int professorsQuantity = professorRepository.getBySurname("Test").size();
        assertEquals(0, professorsQuantity);
    }

    @Test
    void getBySurnameMustReturnProfessorsWithInputSurnameIfTableContainsSuchProfessorSurname() {
        professorRepository.save(professor);
        List<Professor> professors = professorRepository.getBySurname("Test");

        String expected = "Test";
        String actual = professors.get(0).getSurname();

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameMustReturnAllProfessorsWithInputSurnameIfTableContainsSuchProfessorSurname() {
        for (int index = 0; index < 100; index++) {
            professor.setId(index + 1);
            professorRepository.save(professor);
        }

        int expected = 100;
        int actual = professorRepository.getBySurname("Test").size();

        assertEquals(expected, actual);
    }

    // Single running test
    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputProfessorId() {
        professorRepository.save(professor);

        professor.setId(1);
        professor.setSurname("Updated");
        professorRepository.save(professor);

        String expected = "Updated";
        String actual = professorRepository.getBySurname("Updated").get(0).getSurname();

        assertEquals(expected, actual);
    }

    // Single running test
    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputProfessorId() {
        professorRepository.save(professor);
        int rowsQuantityBeforeRemove = professorRepository.findAll().size();

        professorRepository.remove(1);
        int rowsQuantityAfterRemove = professorRepository.findAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}

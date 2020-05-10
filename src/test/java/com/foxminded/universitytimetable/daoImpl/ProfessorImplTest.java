package com.foxminded.universitytimetable.daoImpl;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfessorImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
    private ProfessorImpl professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);
    private Professor professor;

    // add method
    @Test
    void addMustAddProfessorToDB() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        int professorsQuantity = professorImpl.getAll().size();

        assertTrue(professorsQuantity > 0);
    }

    @Test
    void addMustAddProfessorWithCorrectName() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        Professor professorFromDB = professorImpl.getBySurname(professor.getSurName()).get(0);

        String expected = professor.getName();
        String actual = professorFromDB.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSurname() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        Professor professorFromDB = professorImpl.getBySurname(professor.getSurName()).get(0);

        String expected = professor.getSurName();
        String actual = professorFromDB.getSurName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectPatronymic() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        Professor professorFromDB = professorImpl.getBySurname(professor.getSurName()).get(0);

        String expected = professor.getPatronymic();
        String actual = professorFromDB.getPatronymic();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSubjectName() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        Professor professorFromDB = professorImpl.getBySurname(professor.getSurName()).get(0);

        String expected = professor.getSubject();
        String actual = professorFromDB.getSubject();

        assertEquals(expected, actual);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        int id = professorImpl.add(professor);

        assertTrue(id > 0);
    }

    // getAll method
    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        int expected = 0;
        int actual = professorImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");

        for (int index = 0; index < 100; index++) {
            professorImpl.add(professor);
        }

        int expected = 100;
        int actual = professorImpl.getAll().size();

        assertEquals(expected, actual);
    }

    // getById method
    @Test
    void getByIdMustReturnCorrectResult() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);


        String expected = "Name";
        String actual = professorImpl.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfTableIsNotContainsSuchId() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> professorImpl.getById(6));
    }

    // getBySurname method
    @Test
    void getBySurnameMustReturnEmptyListIfTableIsNotContainsProfessorWithSuchSurname() {
        int professorsQuantity = professorImpl.getBySurname("Test").size();
        assertTrue(professorsQuantity == 0);
    }

    @Test
    void getBySurnameMustReturnProfessorsWithInputSurnameIfTableContainsSuchProfessorSurname() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        List<Professor> professors = professorImpl.getBySurname("Surname");

        String expected = "Surname";
        String actual = professors.get(0).getSurName();

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameMustReturnAllProfessorsWithInputSurnameIfTableContainsSuchProfessorSurname() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");

        for (int index = 0; index < 1000; index++) {
            professorImpl.add(professor);
        }

        int expected = 1000;
        int actual = professorImpl.getBySurname("Surname").size();

        assertEquals(expected, actual);
    }

    // update method
    @Test
    void updateMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputProfessorId() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professor.setId(33);

        Assertions.assertThrows(IllegalArgumentException.class, () -> professorImpl.update(professor));
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputProfessorId() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        professor.setId(1);
        professor.setName("Updated");
        professorImpl.update(professor);

        String expected = "Updated";
        String actual = professorImpl.getBySurname("Surname").get(0).getName();

        assertEquals(expected, actual);
    }

    // remove method
    @Test
    void removeMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputProfessorId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> professorImpl.remove(1));
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputProfessorId() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl.add(professor);

        int rowsQuantityBeforeRemove = professorImpl.getAll().size();

        professorImpl.remove(1);
        int rowsQuantityAfterRemove = professorImpl.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}

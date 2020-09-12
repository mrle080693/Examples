package com.foxminded.universitytimetable.dao.hibernatejpa;

import com.foxminded.universitytimetable.dao.impl.hibernatejpa.ProfessorImplHibernate;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfessorImplHibernateTest {
    private ProfessorImplHibernate professorImplHibernate;
    private Professor professor;

    @BeforeEach
    void dataSet() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");

        professorImplHibernate = new ProfessorImplHibernate(entityManagerFactory);
        professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        professorImplHibernate.add(professor);
    }

    @Test
    void addMustAddProfessorWithCorrectName() {
        Professor professorFromDB = professorImplHibernate.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getName();
        String actual = professorFromDB.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSurname() {
        Professor professorFromDB = professorImplHibernate.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getSurname();
        String actual = professorFromDB.getSurname();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectPatronymic() {
        Professor professorFromDB = professorImplHibernate.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getPatronymic();
        String actual = professorFromDB.getPatronymic();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSubjectName() {
        Professor professorFromDB = professorImplHibernate.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getSubject();
        String actual = professorFromDB.getSubject();

        assertEquals(expected, actual);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        int id = professorImplHibernate.add(professor);
        assertTrue(id > 0);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        professorImplHibernate.remove(1);

        int expected = 0;
        int actual = professorImplHibernate.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 100; index++) {
            professorImplHibernate.add(professor);
        }

        int expected = 100;
        int actual = professorImplHibernate.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustReturnProfessorWithCorrectName() {
        String expected = "Name";
        String actual = professorImplHibernate.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameMustReturnEmptyListIfTableIsNotContainsProfessorWithSuchSurname() {
        int professorsQuantity = professorImplHibernate.getBySurname("Test").size();
        assertEquals(0, professorsQuantity);
    }

    @Test
    void getBySurnameMustReturnProfessorsWithInputSurnameIfTableContainsSuchProfessorSurname() {
        List<Professor> professors = professorImplHibernate.getBySurname("Surname");

        String expected = "Surname";
        String actual = professors.get(0).getSurname();

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameMustReturnAllProfessorsWithInputSurnameIfTableContainsSuchProfessorSurname() {
        for (int index = 1; index < 1000; index++) {
            professorImplHibernate.add(professor);
        }

        int expected = 1000;
        int actual = professorImplHibernate.getBySurname("Surname").size();

        assertEquals(expected, actual);
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputProfessorId() {
        professor.setId(1);
        professor.setName("Updated");
        professorImplHibernate.update(professor);

        String expected = "Updated";
        String actual = professorImplHibernate.getBySurname("Surname").get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputProfessorId() {
        int rowsQuantityBeforeRemove = professorImplHibernate.getAll().size();

        professorImplHibernate.remove(1);
        int rowsQuantityAfterRemove = professorImplHibernate.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}

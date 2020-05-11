package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfessorImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
    private ProfessorImpl professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);
    private Professor professor = new Professor("Name", "Surname", "Patronymic", "Math");

    @BeforeEach
    void dataSet() {
        professorImpl.add(professor);
    }

    // add method
    @Test
    void addMustAddProfessorToDB() {
        int professorsQuantity = professorImpl.getAll().size();

        assertTrue(professorsQuantity > 0);
    }

    @Test
    void addMustAddProfessorWithCorrectName() {
        Professor professorFromDB = professorImpl.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getName();
        String actual = professorFromDB.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSurname() {
        Professor professorFromDB = professorImpl.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getSurname();
        String actual = professorFromDB.getSurname();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectPatronymic() {
        Professor professorFromDB = professorImpl.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getPatronymic();
        String actual = professorFromDB.getPatronymic();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddProfessorWithCorrectSubjectName() {
        Professor professorFromDB = professorImpl.getBySurname(professor.getSurname()).get(0);

        String expected = professor.getSubject();
        String actual = professorFromDB.getSubject();

        assertEquals(expected, actual);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        int id = professorImpl.add(professor);
        assertTrue(id > 0);
    }

    // getAll method
    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        professorImpl.remove(1);

        int expected = 0;
        int actual = professorImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 100; index++) {
            professorImpl.add(professor);
        }

        int expected = 100;
        int actual = professorImpl.getAll().size();

        assertEquals(expected, actual);
    }

    // getById method
    @Test
    void getByIdMustReturnCorrectResult() {
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
        List<Professor> professors = professorImpl.getBySurname("Surname");

        String expected = "Surname";
        String actual = professors.get(0).getSurname();

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameMustReturnAllProfessorsWithInputSurnameIfTableContainsSuchProfessorSurname() {
        for (int index = 1; index < 1000; index++) {
            professorImpl.add(professor);
        }

        int expected = 1000;
        int actual = professorImpl.getBySurname("Surname").size();

        assertEquals(expected, actual);
    }

    // update method
    @Test
    void updateMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputProfessorId() {
        professor.setId(33);

        Assertions.assertThrows(IllegalArgumentException.class, () -> professorImpl.update(professor));
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputProfessorId() {
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> professorImpl.remove(2));
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputProfessorId() {
        int rowsQuantityBeforeRemove = professorImpl.getAll().size();

        professorImpl.remove(1);
        int rowsQuantityAfterRemove = professorImpl.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }

    @Test
    void removeMustSetZeroToLessonsWithItsProfessor() {
        Lesson lesson = new Lesson(new Date(1212, 12, 12), 1, 1,
                1, "Building", "Classroom");
        Group group = new Group("Test");

        LessonImpl lessonImpl = context.getBean("lessonImplBean", LessonImpl.class);
        GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);

        groupImpl.add(group);
        lessonImpl.add(lesson);

        Lesson lessonFromDB = lessonImpl.getById(1);
        System.out.println(lessonFromDB.getProfessorId());

        professorImpl.remove(1);
        lessonFromDB = lessonImpl.getById(1);
        int professorId = lessonFromDB.getProfessorId();

        assertTrue(professorId == 0);
    }
}

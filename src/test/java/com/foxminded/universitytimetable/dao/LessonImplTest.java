package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LessonImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);

    private LessonImpl lessonImpl = context.getBean("lessonImplBean", LessonImpl.class);
    private ProfessorImpl professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);
    private GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);

    private Lesson lesson = new Lesson(new Date(1212, 12, 12), 1, 1,
            1, "Building", "Classroom");
    private Professor professor = new Professor("Name", "Surname", "Patronymic",
            "Subject");
    private Group group = new Group("Group");

    @BeforeEach
    void dataSet() {
        professorImpl.add(professor);
        groupImpl.add(group);
        lessonImpl.add(lesson);
    }

    // add method
    @Test
    void addMustAddLessonToDB() {
        int lessonsQuantity = lessonImpl.getAll().size();
        assertTrue(lessonsQuantity > 0);
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
    void addMustThrowDAOExceptionIfProfessorWithInputProfessorIdNotExists() {
        lesson.setProfessorId(2);
        Assertions.assertThrows(DAOException.class, () -> lessonImpl.add(lesson));
    }

    @Test
    void addMustThrowDAOExceptionIfGroupWithInputGroupIdNotExists() {
        lesson.setGroupId(2);
        Assertions.assertThrows(DAOException.class, () -> lessonImpl.add(lesson));
    }


    // getAll method
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

    // getById method
    @Test
    void getByIdMustReturnCorrectResult() {
        String expected = "Building";
        String actual = lessonImpl.getById(1).getBuilding();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfTableIsNotContainsSuchId() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> lessonImpl.getById(6));
    }

    // update method
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

    // remove method
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

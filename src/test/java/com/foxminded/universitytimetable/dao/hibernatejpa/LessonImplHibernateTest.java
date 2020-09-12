package com.foxminded.universitytimetable.dao.hibernatejpa;

import com.foxminded.universitytimetable.dao.impl.hibernatejpa.GroupImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.LessonImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.ProfessorImplHibernate;

import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LessonImplHibernateTest {
    private EntityManagerFactory entityManagerFactory;
    private LessonImplHibernate lessonImplHibernate;
    private Lesson lesson;

    @BeforeEach
    void dataSet() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");

        lessonImplHibernate = new LessonImplHibernate(entityManagerFactory);
        ProfessorImplHibernate professorImplHibernate = new ProfessorImplHibernate(entityManagerFactory);
        GroupImplHibernate groupImplHibernate = new GroupImplHibernate(entityManagerFactory);

        lesson = new Lesson(new Date(1212, 12, 12), 1, 1, 1,
                "Building", "Classroom");
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        Group group = new Group("Group");

        professorImplHibernate.add(professor);
        groupImplHibernate.add(group);
        lessonImplHibernate.add(lesson);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        int id = lessonImplHibernate.add(lesson);

        assertTrue(id > 0);
    }

    @Test
    void addMustAddLessonWithCorrectDate() {
        Date expected = lesson.getDate();
        Date actual = lessonImplHibernate.getById(1).getDate();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectLessonNumber() {
        int expected = lesson.getLessonNumber();
        int actual = lessonImplHibernate.getById(1).getLessonNumber();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectGroupId() {
        int expected = lesson.getGroupId();
        int actual = lessonImplHibernate.getById(1).getGroupId();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectProfessorId() {
        int expected = lesson.getProfessorId();
        int actual = lessonImplHibernate.getById(1).getProfessorId();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectBuiding() {
        String expected = lesson.getBuilding();
        String actual = lessonImplHibernate.getById(1).getBuilding();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectClassroom() {
        String expected = lesson.getClassroom();
        String actual = lessonImplHibernate.getById(1).getClassroom();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        lessonImplHibernate.remove(1);

        int expected = 0;
        int actual = lessonImplHibernate.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 100; index++) {
            lessonImplHibernate.add(lesson);
        }

        int expected = 100;
        int actual = lessonImplHibernate.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustReturnCorrectLesson() {
        Lesson lessonFromDB = lessonImplHibernate.getById(1);

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
    void updateMustUpdateRowInTableWithIdEqualsInputLessonId() {
        lesson.setId(1);
        lesson.setClassroom("Updated");
        lessonImplHibernate.update(lesson);

        String expected = "Updated";
        String actual = lessonImplHibernate.getById(1).getClassroom();

        assertEquals(expected, actual);
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputLessonId() {
        int rowsQuantityBeforeRemove = lessonImplHibernate.getAll().size();

        lessonImplHibernate.remove(1);
        int rowsQuantityAfterRemove = lessonImplHibernate.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}

package com.foxminded.universitytimetable.dao.hibernatejpa;

import com.foxminded.universitytimetable.dao.impl.hibernatejpa.GroupImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.LessonImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.ProfessorImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.TimetableImplHibernate;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimetableImplHibernateTest {
    private TimetableImplHibernate timetableImplHibernate;
    private LessonImplHibernate lessonImplHibernate;
    private Lesson lesson;
    private Date from;
    private Date till;

    @BeforeEach
    void dataSet() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");

        timetableImplHibernate = new TimetableImplHibernate(entityManagerFactory);
        lessonImplHibernate = new LessonImplHibernate(entityManagerFactory);
        ProfessorImplHibernate professorImplHibernate = new ProfessorImplHibernate(entityManagerFactory);
        GroupImplHibernate groupImplHibernate = new GroupImplHibernate(entityManagerFactory);

        from = new Date(1919, 11, 11);
        till = new Date(2020, 11, 11);

        lesson = new Lesson(new Date(1912, 12, 12), 1, 1, 1,
                "Building", "Classroom");
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        Group group = new Group("Group");

        professorImplHibernate.add(professor);
        groupImplHibernate.add(group);
        lessonImplHibernate.add(lesson);
    }

    @Test
    void getGroupTimetableMustReturnZeroIfGroupHaveNotLessons() {
        int expected = 0;
        int actual = timetableImplHibernate.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImplHibernate.add(lesson);

        int expected = 0;
        int actual = timetableImplHibernate.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = timetableImplHibernate.getGroupTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImplHibernate.add(lesson);
        }

        int expected = 1000;
        int actual = timetableImplHibernate.getGroupTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImplHibernate.add(lesson);
        }

        List<Lesson> lessons = timetableImplHibernate.getGroupTimetable(1, from, till);

        for (Lesson lessonFromDB : lessons) {
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
    }

    @Test
    void getProfessorTimetableMustReturnZeroIfProfessorHaveNotLessons() {
        int expected = 0;
        int actual = timetableImplHibernate.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImplHibernate.add(lesson);

        int expected = 0;
        int actual = timetableImplHibernate.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = timetableImplHibernate.getProfessorTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImplHibernate.add(lesson);
        }

        int expected = 1000;
        int actual = timetableImplHibernate.getProfessorTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessons() {
        for (int index = 0; index < 1000; index++) {
            lessonImplHibernate.add(lesson);
        }

        List<Lesson> lessons = timetableImplHibernate.getProfessorTimetable(1, from, till);

        for (Lesson lessonFromDB : lessons) {
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
    }
}

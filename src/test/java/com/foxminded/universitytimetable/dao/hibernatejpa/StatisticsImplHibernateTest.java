package com.foxminded.universitytimetable.dao.hibernatejpa;

import com.foxminded.universitytimetable.dao.impl.hibernatejpa.GroupImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.LessonImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.ProfessorImplHibernate;
import com.foxminded.universitytimetable.dao.impl.hibernatejpa.StatisticsImplHibernate;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsImplHibernateTest {
    private StatisticsImplHibernate statisticsImplHibernate;
    private LessonImplHibernate lessonImplHibernate;
    private Lesson lesson;
    private Date from;
    private Date till;

    @BeforeEach
    void dataSet() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");

        statisticsImplHibernate = new StatisticsImplHibernate(entityManagerFactory);
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
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessons() {
        long expected = 0;
        long actual = statisticsImplHibernate.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImplHibernate.add(lesson);

        long expected = 0;
        long actual = statisticsImplHibernate.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        long expected = 1;
        long actual = statisticsImplHibernate.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImplHibernate.add(lesson);
        }

        long expected = 1000;
        long actual = statisticsImplHibernate.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessons() {
        long expected = 0;
        long actual = statisticsImplHibernate.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImplHibernate.add(lesson);

        long expected = 0;
        long actual = statisticsImplHibernate.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        long expected = 1;
        long actual = statisticsImplHibernate.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImplHibernate.add(lesson);
        }

        int expected = 1000;
        long actual = statisticsImplHibernate.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }
}

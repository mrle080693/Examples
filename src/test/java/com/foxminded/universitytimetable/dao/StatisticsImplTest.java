package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringTestJdbcConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.dao.impl.StatisticsImpl;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsImplTest {
    private DataSource dataSource;
    private static AnnotationConfigApplicationContext context;

    private static StatisticsImpl statisticsImpl;
    private static LessonImpl lessonImpl;
    private static ProfessorImpl professorImpl;
    private static GroupImpl groupImpl;

    private Lesson lesson;
    private Professor professor;
    private Group group;

    private static Date from;
    private static Date till;

    @BeforeAll
    static void initialize() {
        context = new AnnotationConfigApplicationContext(SpringTestJdbcConfig.class);

        statisticsImpl = context.getBean("statisticsImplBean", StatisticsImpl.class);
        lessonImpl = context.getBean("lessonImplBean", LessonImpl.class);
        professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);
        groupImpl = context.getBean("groupImplBean", GroupImpl.class);

        from = new Date(1919, 11, 11);
        till = new Date(2020, 11, 11);
    }

    @BeforeEach
    void dataSet() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("test.sql")
                .build();

        lesson = new Lesson(new Date(1912, 12, 12), 1, 1, 1,
                "Building", "Classroom");
        professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        group = new Group("Group");

        professorImpl.add(professor);
        groupImpl.add(group);
        lessonImpl.add(lesson);
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessons() {
        int expected = 0;
        int actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        int expected = 0;
        int actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = statisticsImpl.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = statisticsImpl.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessons() {
        int expected = 0;
        int actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        int expected = 0;
        int actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = statisticsImpl.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        int actual = statisticsImpl.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }
}

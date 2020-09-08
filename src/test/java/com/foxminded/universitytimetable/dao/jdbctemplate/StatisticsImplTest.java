package com.foxminded.universitytimetable.dao.jdbctemplate;

import com.foxminded.universitytimetable.dao.impl.jdbctemplate.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.ProfessorImpl;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.StatisticsImpl;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsImplTest {
    private StatisticsImpl statisticsImpl;
    private LessonImpl lessonImpl;
    private Lesson lesson;
    private Date from;
    private Date till;

    @BeforeEach
    void dataSet() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("test.sql")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        statisticsImpl = new StatisticsImpl(jdbcTemplate);
        lessonImpl = new LessonImpl(jdbcTemplate);
        ProfessorImpl professorImpl = new ProfessorImpl(jdbcTemplate);
        GroupImpl groupImpl = new GroupImpl(jdbcTemplate);

        from = new Date(1919, 11, 11);
        till = new Date(2020, 11, 11);


        lesson = new Lesson(new Date(1912, 12, 12), 1, 1, 1,
                "Building", "Classroom");
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Subject");
        Group group = new Group("Group");

        professorImpl.add(professor);
        groupImpl.add(group);
        lessonImpl.add(lesson);
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessons() {
        long expected = 0;
        long actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        long expected = 0;
        long actual = statisticsImpl.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        long expected = 1;
        long actual = statisticsImpl.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        long expected = 1000;
        long actual = statisticsImpl.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessons() {
        long expected = 0;
        long actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        lessonImpl.add(lesson);

        long expected = 0;
        long actual = statisticsImpl.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        long expected = 1;
        long actual = statisticsImpl.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            lessonImpl.add(lesson);
        }

        int expected = 1000;
        long actual = statisticsImpl.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }
}

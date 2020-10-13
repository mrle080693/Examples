package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.database.impl.repositories.StatisticsRepository;
import com.foxminded.universitytimetable.models.Lesson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StatisticsRepositoryTest {
    @Autowired
    private StatisticsRepository statisticsRepository;
    private Lesson lesson = new Lesson(new Date(1212, 12, 12), 1, 1, 1,
            "Building", "Classroom");
    private Date from = new Date(1919, 11, 11);
    private Date till = new Date(2020, 11, 11);

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessons() {
        long expected = 0;
        long actual = statisticsRepository.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        statisticsRepository.save(lesson);

        long expected = 0;
        long actual = statisticsRepository.getGroupEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantity() {
        statisticsRepository.save(lesson);

        Date dateFrom = new Date(1819, 11, 11);

        long expected = 1;
        long actual = statisticsRepository.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupEmploymentMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        statisticsRepository.save(lesson);

        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 101; index++) {
            lesson.setId(index);
            statisticsRepository.save(lesson);
        }

        long expected = 100;
        long actual = statisticsRepository.getGroupEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessons() {
        long expected = 0;
        long actual = statisticsRepository.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        statisticsRepository.save(lesson);

        long expected = 0;
        long actual = statisticsRepository.getProfessorEmployment(1, from, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantity() {
        statisticsRepository.save(lesson);

        Date dateFrom = new Date(1819, 11, 11);

        long expected = 1;
        long actual = statisticsRepository.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorEmploymentMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        statisticsRepository.save(lesson);

        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            statisticsRepository.save(lesson);
        }

        int expected = 1000;
        long actual = statisticsRepository.getProfessorEmployment(1, dateFrom, till);

        assertEquals(expected, actual);
    }
}

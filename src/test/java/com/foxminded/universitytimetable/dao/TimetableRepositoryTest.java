package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.dao.impl.repositories.TimetableRepository;
import com.foxminded.universitytimetable.models.Lesson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TimetableRepositoryTest {
    @Autowired
    private TimetableRepository timetableRepository;
    private Lesson lesson = new Lesson(new Date(1212, 12, 12), 1, 1, 1,
            "Building", "Classroom");
    private Date from = new Date(1919, 11, 11);
    private Date till = new Date(2020, 11, 11);

    @Test
    void getGroupTimetableMustReturnZeroIfGroupHaveNotLessons() {
        int expected = 0;
        int actual = timetableRepository.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnZeroIfGroupHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        timetableRepository.save(lesson);

        int expected = 0;
        int actual = timetableRepository.getGroupTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = timetableRepository.getGroupTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessonsQuantityIfGroupHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 1000; index++) {
            timetableRepository.save(lesson);
        }

        int expected = 1000;
        int actual = timetableRepository.getGroupTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getGroupTimetableMustReturnCorrectLessons() {
        for (int index = 0; index < 101; index++) {
            lesson.setId(index);
            timetableRepository.save(lesson);
        }

        List<Lesson> lessons = timetableRepository.getGroupTimetable(1, from, till);

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
        int actual = timetableRepository.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnZeroIfProfessorHaveNotLessonsInInputPeriod() {
        lesson.setDate(new Date(1, 1, 1));
        timetableRepository.save(lesson);

        int expected = 0;
        int actual = timetableRepository.getProfessorTimetable(1, from, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessonsQuantity() {
        Date dateFrom = new Date(1819, 11, 11);

        int expected = 1;
        int actual = timetableRepository.getProfessorTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessonsQuantityIfProfessorHaveManyLessons() {
        Date dateFrom = new Date(1819, 11, 11);

        for (int index = 1; index < 101; index++) {

            timetableRepository.save(lesson);
        }

        int expected = 100;
        int actual = timetableRepository.getProfessorTimetable(1, dateFrom, till).size();

        assertEquals(expected, actual);
    }

    @Test
    void getProfessorTimetableMustReturnCorrectLessons() {
        for (int index = 1; index < 1001; index++) {
            lesson.setId(index);
            timetableRepository.save(lesson);
        }

        List<Lesson> lessons = timetableRepository.getProfessorTimetable(1, from, till);

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

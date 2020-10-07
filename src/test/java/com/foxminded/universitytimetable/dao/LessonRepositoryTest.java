package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.dao.impl.repositories.LessonRepository;
import com.foxminded.universitytimetable.models.Lesson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LessonRepositoryTest {
    @Autowired
    private LessonRepository lessonRepository;
    private Lesson lesson = new Lesson(new Date(1212, 12, 12), 1, 1, 1,
            "Building", "Classroom");

    @Test
    void addMustCreateIdNotEqualsZero() {
        Lesson lesson1 = lessonRepository.save(lesson);
        int id = lesson1.getId();

        assertTrue(id > 0);
    }

    @Test
    void addMustAddLessonWithCorrectDate() {
        lessonRepository.save(lesson);

        Date expected = lesson.getDate();
        Date actual = lessonRepository.getOne(1).getDate();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectLessonNumber() {
        lessonRepository.save(lesson);

        int expected = lesson.getLessonNumber();
        int actual = lessonRepository.getOne(1).getLessonNumber();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectGroupId() {
        lessonRepository.save(lesson);

        int expected = lesson.getGroupId();
        int actual = lessonRepository.getOne(1).getGroupId();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectProfessorId() {
        lessonRepository.save(lesson);

        int expected = lesson.getProfessorId();
        int actual = lessonRepository.getOne(1).getProfessorId();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectBuilding() {
        lessonRepository.save(lesson);

        String expected = lesson.getBuilding();
        String actual = lessonRepository.getOne(1).getBuilding();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddLessonWithCorrectClassroom() {
        lessonRepository.save(lesson);

        String expected = lesson.getClassroom();
        String actual = lessonRepository.getOne(1).getClassroom();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        lessonRepository.save(lesson);
        lessonRepository.remove(1);

        int expected = 0;
        int actual = lessonRepository.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 101; index++) {
            lesson.setId(index);
            lessonRepository.save(lesson);
        }

        int expected = 100;
        int actual = lessonRepository.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustReturnCorrectLesson() {
        lessonRepository.save(lesson);

        Lesson lessonFromDB = lessonRepository.getOne(1);

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
        lessonRepository.save(lesson);

        String expected = "Updated";
        String actual = lessonRepository.getOne(1).getClassroom();

        assertEquals(expected, actual);
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputLessonId() {
        lessonRepository.save(lesson);
        int rowsQuantityBeforeRemove = lessonRepository.findAll().size();

        lessonRepository.remove(1);
        int rowsQuantityAfterRemove = lessonRepository.findAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }

}

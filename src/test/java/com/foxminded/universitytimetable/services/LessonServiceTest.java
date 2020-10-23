package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.database.impl.GroupImpl;
import com.foxminded.universitytimetable.database.impl.LessonImpl;
import com.foxminded.universitytimetable.database.impl.ProfessorImpl;
import com.foxminded.universitytimetable.services.exceptions.DAOException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Lesson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LessonServiceTest {
    private LessonService lessonService;
    private LessonImpl lessonImpl;
    private GroupImpl groupImpl;
    private ProfessorImpl professorImpl;
    private Lesson lesson;

    @BeforeEach
    void dataSet() {
        lessonImpl = Mockito.mock(LessonImpl.class);
        groupImpl = Mockito.mock(GroupImpl.class);
        professorImpl = Mockito.mock(ProfessorImpl.class);

        //lessonService = new LessonService(lessonImpl, groupImpl, professorImpl);

        lesson = new Lesson(new Date(Calendar.getInstance().getTime().getTime()), 1, 1, 1,
                "Building", "Classroom");
    }

    @Test
    void addMustThrowIllegalArgumentExceptionIfInputLessonIsNull() {
        lesson = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonDateIsNull() {
        lesson.setDate(null);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonBuildingIsNull() {
        lesson.setBuilding(null);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonClassroomIsNull() {
        lesson.setClassroom(null);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonBuildingIsEmpty() {
        lesson.setBuilding("");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonClassroomIsEmpty() {
        lesson.setClassroom("");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonBuildingIsOnlySeparators() {
        lesson.setBuilding("    ");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonClassroomIsOnlySeparators() {
        lesson.setClassroom("    ");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonNumberIsZero() {
        lesson.setLessonNumber(0);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonGroupIdIsZero() {
        lesson.setGroupId(0);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonProfessorIdIsZero() {
        lesson.setProfessorId(0);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputLessonDateIsEarlierThenToday() {
        lesson.setDate(new Date(1993, 12, 11));
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowNotFoundEntityExceptionIfLessonGroupIsNotExists() {
        when(groupImpl.getById(1)).thenThrow(EmptyResultDataAccessException.class);
        lesson.setGroupId(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowNotFoundEntityExceptionIfLessonProfessorIsNotExists() {
        when(professorImpl.getById(1)).thenThrow(EmptyResultDataAccessException.class);
        lesson.setProfessorId(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfLessonIdIsNotZero() {
        lesson.setId(7);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.add(lesson));
    }

    @Test
    void addMustThrowDAOExceptionIfLessonImplThrowDataAccessException() {
        when(lessonImpl.add(lesson)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> lessonService.add(lesson));
    }

    @Test
    void getAllMustThrowsDAOExceptionIfLessonImplThrowsDataAccessException() {
        when(lessonImpl.getAll()).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> lessonService.getAll());
    }

    @Test
    void getAllMustThrowNotFoundEntityExceptionIfLessonImplReturnEmptyList() {
        List<Lesson> expected = new ArrayList<>();

        when(lessonImpl.getAll()).thenReturn(expected);

        Assertions.assertThrows(NotFoundEntityException.class, () -> lessonService.getAll());
    }

    @Test
    void getAllMustReturnTheSameThatGroupImplWasReturn() {
        List<Lesson> expected = new ArrayList<>();
        expected.add(lesson);

        when(lessonImpl.getAll()).thenReturn(expected);

        List<Lesson> actual = lessonService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustThrowEntityValidationExceptionIfInputLessonIdIsZero() {
        Assertions.assertThrows(ValidationException.class, () -> lessonService.getById(0));
    }

    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfLessonImplThrowEmptyResultDataAccessException() {
        when(lessonImpl.getById(1)).thenThrow(new EmptyResultDataAccessException(1));

        Assertions.assertThrows(NotFoundEntityException.class, () -> lessonService.getById(1));
    }

    @Test
    void getByIdMustThrowsDAOExceptionIfLessonImplThrowsDataAccessException() {
        when(lessonImpl.getById(1)).thenThrow(new DataAccessException("") {
        });

        Assertions.assertThrows(DAOException.class, () -> lessonService.getById(1));
    }

    @Test
    void getByIdMustReturnTheSameThatLessonImplWasReturn() {
        Lesson expected = lesson;

        when(lessonImpl.getById(1)).thenReturn(expected);

        Lesson actual = lessonService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void updateMustThrowIllegalArgumentExceptionIfInputLessonIsNull() {
        lesson = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonDateIsNull() {
        lesson.setDate(null);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonBuildingIsNull() {
        lesson.setBuilding(null);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonClassroomIsNull() {
        lesson.setClassroom(null);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonBuildingIsEmpty() {
        lesson.setBuilding("");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonClassroomIsEmpty() {
        lesson.setClassroom("");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonBuildingIsOnlySeparators() {
        lesson.setBuilding("    ");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonClassroomIsOnlySeparators() {
        lesson.setClassroom("    ");
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonNumberIsZero() {
        lesson.setLessonNumber(0);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonGroupIdIsZero() {
        lesson.setGroupId(0);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonProfessorIdIsZero() {
        lesson.setProfessorId(0);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputLessonDateIsEarlierThenToday() {
        lesson.setDate(new Date(1993, 12, 11));
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowNotFoundEntityExceptionIfLessonGroupIsNotExists() {
        when(groupImpl.getById(1)).thenThrow(EmptyResultDataAccessException.class);
        lesson.setGroupId(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowNotFoundEntityExceptionIfLessonProfessorIsNotExists() {
        when(professorImpl.getById(1)).thenThrow(EmptyResultDataAccessException.class);
        lesson.setProfessorId(1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfLessonIdIsZero() {
        lesson.setId(0);
        Assertions.assertThrows(ValidationException.class, () -> lessonService.update(lesson));
    }

    @Test
    void updateMustReturnTheSameThatLessonImplWasReturn() {
        lesson.setId(1);

        when(lessonImpl.update(lesson)).thenReturn(new Lesson());

        int expected = 1;
        int actual = lessonService.update(lesson).getId();

        assertEquals(expected, actual);
    }

    @Test
    void removeMustThrowDAOExceptionIfLessonImplThrowsDataAccessException() {
        when(lessonImpl.remove(7)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> lessonService.remove(7));
    }
}

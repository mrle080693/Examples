package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.database.impl.ProfessorImpl;
import com.foxminded.universitytimetable.services.exceptions.DAOException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProfessorServiceTest {
    private ProfessorImpl professorImpl;
    private ProfessorService professorService;
    private Professor professor;

    @BeforeEach
    void dataSet() {
        professor = new Professor("Name", "Surname", "Patronymic", "Math");
        professorImpl = Mockito.mock(ProfessorImpl.class);
        // professorService = new ProfessorService(professorImpl);
    }

    @Test
    void addMustThrowIllegalArgumentExceptionIfInputProfessorIsNull() {
        professor = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfInputProfessorIdIsNotZero() {
        professor.setId(7);
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowDAOExceptionIfProfessorImplThrowsDataAccessException() {
        when(professorImpl.add(professor)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorNameIsNull() {
        professor.setName(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorSurnameIsNull() {
        professor.setSurname(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorPatronymicIsNull() {
        professor.setPatronymic(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorSubjectIsNull() {
        professor.setSubject(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorNameIsEmpty() {
        professor.setName("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorSurnameIsEmpty() {
        professor.setSurname("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorPatronymicIsEmpty() {
        professor.setPatronymic("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorSubjectIsEmpty() {
        professor.setSurname("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorNameIsOnlySeparators() {
        professor.setName("     ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorSurnameOnlySeparators() {
        professor.setSurname("    ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorPatronymicOnlySeparators() {
        professor.setPatronymic("    ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustThrowEntityValidationExceptionIfProfessorSubjectOnlySeparators() {
        professor.setSurname("     ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.add(professor));
    }

    @Test
    void addMustReturnTheSameThatProfessorImplWasReturn() {
        when(professorImpl.add(professor)).thenReturn(1);

        int expected = 1;
        int actual = professorService.add(professor);

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustThrowsDAOExceptionIfProfessorImplThrowsDataAccessException() {
        when(professorImpl.getAll()).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> professorService.getAll());
    }

    @Test
    void getAllMustThrowNotFoundEntityExceptionIfProfessorImplReturnEmptyList() {
        List<Professor> expected = new ArrayList<>();

        when(professorImpl.getAll()).thenReturn(expected);

        Assertions.assertThrows(NotFoundEntityException.class, () -> professorService.getAll());
    }

    @Test
    void getAllMustReturnTheSameThatProfessorImplWasReturn() {
        List<Professor> expected = new ArrayList<>();
        expected.add(professor);

        when(professorImpl.getAll()).thenReturn(expected);

        List<Professor> actual = professorService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustThrowEntityValidationExceptionIfInputProfessorIdIsZero() {
        Assertions.assertThrows(ValidationException.class, () -> professorService.getById(0));
    }

    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfProfessorImplThrowEmptyResultDataAccessException() {
        when(professorImpl.getById(1)).thenThrow(new EmptyResultDataAccessException(1));

        Assertions.assertThrows(NotFoundEntityException.class, () -> professorService.getById(1));
    }

    @Test
    void getByIdMustThrowsDAOExceptionIfProfessorImplThrowsDataAccessException() {
        when(professorImpl.getById(1)).thenThrow(new DataAccessException("") {
        });

        Assertions.assertThrows(DAOException.class, () -> professorService.getById(1));
    }

    @Test
    void getByIdMustReturnTheSameThatProfessorImplWasReturn() {
        Professor expected = professor;

        when(professorImpl.getById(1)).thenReturn(expected);

        Professor actual = professorService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void getBySurnameMustThrowEntityValidationExceptionIfInputProfessorSurnameIsNull() {
        String professorSurname = null;
        Assertions.assertThrows(ValidationException.class, () -> professorService.getBySurname(professorSurname));
    }

    @Test
    void getBySurnameMustThrowEntityValidationExceptionIfInputProfessorSurnameIsEmpty() {
        String professorSurname = "";
        Assertions.assertThrows(ValidationException.class, () -> professorService.getBySurname(professorSurname));
    }

    @Test
    void getBySurnameMustThrowEntityValidationExceptionIfInputProfessorSurnameIsSeparators() {
        String groupName = "     ";
        Assertions.assertThrows(ValidationException.class, () -> professorService.getBySurname(groupName));
    }

    @Test
    void getBySurnameMustThrowNotFoundEntityExceptionIfProfessorImplThrowEmptyResultDataAccessException() {
        when(professorImpl.getBySurname("Test")).thenThrow(new EmptyResultDataAccessException(1));

        Assertions.assertThrows(NotFoundEntityException.class, () -> professorService.getBySurname("Test"));
    }

    @Test
    void getBySurnameMustThrowsDAOExceptionIfProfessorImplThrowsDataAccessException() {
        when(professorImpl.getBySurname("Test")).thenThrow(new DataAccessException("") {
        });

        Assertions.assertThrows(DAOException.class, () -> professorService.getBySurname("Test"));
    }

    @Test
    void getBySurnameMustReturnTheSameThatProfessorImplWasReturn() {
        List<Professor> expected = new ArrayList<>();
        expected.add(professor);

        when(professorImpl.getBySurname("Test")).thenReturn(expected);

        List<Professor> actual = professorService.getBySurname("Test");

        assertEquals(expected, actual);
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfInputProfessorIdIsZero() {
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowDAOExceptionIfProfessorImplThrowsDataAccessException() {
        professor.setId(1);

        when(professorImpl.update(professor)).thenThrow(new DataAccessException("") {
        });

        Assertions.assertThrows(DAOException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowIllegalArgumentExceptionIfInputProfessorIsNull() {
        professor = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorNameIsNull() {
        professor.setName(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorSurnameIsNull() {
        professor.setSurname(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorPatronymicIsNull() {
        professor.setPatronymic(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorSubjectIsNull() {
        professor.setSubject(null);
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorNameIsEmpty() {
        professor.setName("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorSurnameIsEmpty() {
        professor.setSurname("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorPatronymicIsEmpty() {
        professor.setPatronymic("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorSubjectIsEmpty() {
        professor.setSurname("");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorNameIsOnlySeparators() {
        professor.setName("     ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorSurnameOnlySeparators() {
        professor.setSurname("    ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorPatronymicOnlySeparators() {
        professor.setPatronymic("    ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowEntityValidationExceptionIfProfessorSubjectOnlySeparators() {
        professor.setSurname("     ");
        Assertions.assertThrows(ValidationException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustThrowNotFoundEntityExceptionIfProfessorImplReturnStatusNotOne() {
        professor.setId(8);

        when(professorImpl.update(professor)).thenReturn(-1);

        Assertions.assertThrows(NotFoundEntityException.class, () -> professorService.update(professor));
    }

    @Test
    void updateMustReturnTheSameThatProfessorImplWasReturn() {
        professor.setId(1);

        when(professorImpl.update(professor)).thenReturn(1);

        int expected = 1;
        int actual = professorService.update(professor);

        assertEquals(expected, actual);
    }

    @Test
    void removeMustThrowNotFoundEntityExceptionIfProfessorImplReturnStatusNotOne() {
        when(professorImpl.remove(7)).thenReturn(-1);
        Assertions.assertThrows(NotFoundEntityException.class, () -> professorService.remove(1));
    }

    @Test
    void removeMustThrowDAOExceptionIfProfessorImplThrowsDataAccessException() {
        when(professorImpl.remove(7)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> professorService.remove(7));
    }

    @Test
    void removeMustReturnStatusOneIfProfessorImplWasReturnStatusOne() {
        int expected = 1;
        when(professorImpl.remove(1)).thenReturn(expected);

        int actual = professorService.remove(1);

        assertEquals(expected, actual);
    }
}

package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.impl.TimetableImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;

import java.sql.Date;

import static org.mockito.Mockito.when;

class TimetableServiceTest {
    private TimetableService timetableService;
    private GroupService groupService;
    private ProfessorService professorService;

    private TimetableImpl timetableImpl;

    private Date from;
    private Date till;

    @BeforeEach
    void dataSet() {
        timetableImpl = Mockito.mock(TimetableImpl.class);
        groupService = Mockito.mock(GroupService.class);
        professorService = Mockito.mock(ProfessorService.class);

        timetableService = new TimetableService(timetableImpl, groupService, professorService);

        from = new Date(2019, 11, 11);
        till = new Date(2020, 5, 11);
    }

    @Test
    void getGroupTimetableMustThrowNotFoundEntityExceptionIfGroupServiceThrowNotFoundEntityException() {
        when(groupService.getById(7)).thenThrow(new NotFoundEntityException(""));
        Assertions.assertThrows(NotFoundEntityException.class, () -> timetableService.getGroupTimetable(7,
                from, till));
    }

    @Test
    void getGroupTimetableMustThrowDAOExceptionIfTimetableImplThrowDataAccessException() {
        when(timetableImpl.getGroupTimetable(7, from, till)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> timetableService.getGroupTimetable(7, from, till));
    }

    @Test
    void getGroupTimetableMustThrowEntityValidationExceptionIfInputDataWithNameFromIsNull() {
        from = null;
        Assertions.assertThrows(ValidationException.class, () -> timetableService.getGroupTimetable(7,
                from, till));
    }

    @Test
    void getGroupTimetableMustThrowEntityValidationExceptionIfInputDataWithNameTillIsNull() {
        till = null;
        Assertions.assertThrows(ValidationException.class, () -> timetableService.getGroupTimetable(7,
                from, till));
    }

    @Test
    void getProfessorTimetableMustThrowNotFoundEntityExceptionIfProfessorServiceThrowNotFoundEntityException() {
        when(professorService.getById(7)).thenThrow(new NotFoundEntityException(""));
        Assertions.assertThrows(NotFoundEntityException.class, () -> timetableService.getProfessorTimetable(7,
                from, till));
    }

    @Test
    void getProfessorTimetableMustThrowDAOExceptionIfTimetableImplThrowDataAccessException() {
        when(timetableImpl.getProfessorTimetable(7, from, till)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> timetableService.getProfessorTimetable(7, from, till));
    }

    @Test
    void getProfessorTimetableMustThrowEntityValidationExceptionIfInputDataWithNameFromIsNull() {
        from = null;
        Assertions.assertThrows(ValidationException.class, () -> timetableService.getProfessorTimetable(7,
                from, till));
    }

    @Test
    void getProfessorTimetableMustThrowEntityValidationExceptionIfInputDataWithNameTillIsNull() {
        till = null;
        Assertions.assertThrows(ValidationException.class, () -> timetableService.getProfessorTimetable(7,
                from, till));
    }
}

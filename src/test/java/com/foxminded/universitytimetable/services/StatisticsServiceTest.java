package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.impl.jdbctemplate.StatisticsImpl;
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

class StatisticsServiceTest {
    private StatisticsService statisticsService;
    private GroupService groupService;
    private ProfessorService professorService;

    private StatisticsImpl statisticsImpl;

    private Date from;
    private Date till;

    @BeforeEach
    void dataSet() {
        statisticsImpl = Mockito.mock(StatisticsImpl.class);
        groupService = Mockito.mock(GroupService.class);
        professorService = Mockito.mock(ProfessorService.class);

        //statisticsService = new StatisticsService(statisticsImpl, groupService, professorService);

        from = new Date(2019, 11, 11);
        till = new Date(2020, 5, 11);
    }

    @Test
    void getGroupEmploymentMustThrowNotFoundEntityExceptionIfGroupServiceThrowNotFoundEntityException() {
        when(groupService.getById(7)).thenThrow(new NotFoundEntityException(""));
        Assertions.assertThrows(NotFoundEntityException.class, () -> statisticsService.getGroupEmployment(7,
                from, till));
    }

    @Test
    void getGroupEmploymentMustThrowDAOExceptionIfStatisticsImplThrowDataAccessException() {
        when(statisticsImpl.getGroupEmployment(7, from, till)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> statisticsService.getGroupEmployment(7, from, till));
    }

    @Test
    void getGroupEmploymentMustThrowEntityValidationExceptionIfInputDataWithNameFromIsNull() {
        from = null;
        Assertions.assertThrows(ValidationException.class, () -> statisticsService.getGroupEmployment(7,
                from, till));
    }

    @Test
    void getGroupEmploymentMustThrowEntityValidationExceptionIfInputDataWithNameTillIsNull() {
        till = null;
        Assertions.assertThrows(ValidationException.class, () -> statisticsService.getGroupEmployment(7,
                from, till));
    }

    @Test
    void getProfessorEmploymentMustThrowNotFoundEntityExceptionIfProfessorServiceThrowNotFoundEntityException() {
        when(professorService.getById(7)).thenThrow(new NotFoundEntityException(""));
        Assertions.assertThrows(NotFoundEntityException.class, () -> statisticsService.getProfessorEmployment(7,
                from, till));
    }

    @Test
    void getProfessorEmploymentMustThrowDAOExceptionIfStatisticsImplThrowDataAccessException() {
        when(statisticsImpl.getProfessorEmployment(7, from, till)).thenThrow(new DataAccessException("") {
        });
        Assertions.assertThrows(DAOException.class, () -> statisticsService.getProfessorEmployment(7, from, till));
    }

    @Test
    void getProfessorEmploymentMustThrowEntityValidationExceptionIfInputDataWithNameFromIsNull() {
        from = null;
        Assertions.assertThrows(ValidationException.class, () -> statisticsService.getProfessorEmployment(7,
                from, till));
    }

    @Test
    void getProfessorEmploymentMustThrowEntityValidationExceptionIfInputDataWithNameTillIsNull() {
        till = null;
        Assertions.assertThrows(ValidationException.class, () -> statisticsService.getProfessorEmployment(7,
                from, till));
    }
}

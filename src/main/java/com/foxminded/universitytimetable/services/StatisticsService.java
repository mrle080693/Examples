package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.impl.StatisticsImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.EntityValidationException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("statisticsServiceBean")
public class StatisticsService {
    private final StatisticsImpl statisticsImpl;
    private final GroupService groupService;
    private final ProfessorService professorService;

    @Autowired
    public StatisticsService(StatisticsImpl statisticsImpl, GroupService groupService, ProfessorService professorService) {
        this.statisticsImpl = statisticsImpl;
        this.groupService = groupService;
        this.professorService = professorService;
    }

    public int getGroupEmployment(int groupId, Date from, Date till) {
        int lessonsQuantity;

        try {
            groupService.getById(groupId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Group is not exists", ex);
        }

        try {
            lessonsQuantity = statisticsImpl.getGroupEmployment(groupId, from, till);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get group employment", ex);
        }

        if (from == null || till == null) {
            throw new EntityValidationException("Date cant be null");
        }

        return lessonsQuantity;
    }

    public int getProfessorEmployment(int professorId, Date from, Date till) {
        int lessonsQuantity;

        try {
            professorService.getById(professorId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Professor is not exist", ex);
        }

        try {
            lessonsQuantity = statisticsImpl.getProfessorEmployment(professorId, from, till);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get professor employment", ex);
        }

        if (from == null || till == null) {
            throw new EntityValidationException("Date cant be null");
        }

        return lessonsQuantity;
    }
}

package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("statisticsServiceBean")
public class StatisticsService {
    private Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);
    private StatisticsDAO statisticsDAO;
    private final GroupService groupService;
    private final ProfessorService professorService;

    @Autowired
    public StatisticsService(@Qualifier("statisticsImplHibernateBean") StatisticsDAO statisticsDAO, GroupService groupService,
                             ProfessorService professorService) {
        this.statisticsDAO = statisticsDAO;
        this.groupService = groupService;
        this.professorService = professorService;
    }

    public long getGroupEmployment(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group employment. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        long lessonsQuantity;

        // For check if group exists
        groupService.getById(groupId);

        try {
            lessonsQuantity = statisticsDAO.getGroupEmployment(groupId, from, till);
        } catch (DataAccessException ex) {
            String exMessage = "Cant get group employment. Group id = " + groupId + "from: " + from + " till: " + till;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (from == null || till == null) {
            String exMessage = "Date is null. from = " + from + " till = " + till;
            ValidationException ex = new ValidationException(exMessage);
            LOGGER.warn(exMessage);
            throw ex;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get group employment. Group id = " + groupId + "from: " + from + "till: " + till);
        }

        return lessonsQuantity;
    }

    public long getProfessorEmployment(int professorId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor employment. Professor id: " + professorId + " from: " + from + "till: "
                    + till);
        }

        long lessonsQuantity;

        // For check if professor exists
        professorService.getById(professorId);

        try {
            lessonsQuantity = statisticsDAO.getProfessorEmployment(professorId, from, till);
        } catch (DataAccessException ex) {
            String exMessage = "Cant get professor employment. Professor id = " + professorId + " from: " + from
                    + "till: " + till;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (from == null || till == null) {
            String exMessage = "Date is null.  from = " + from + " till = " + till;
            ValidationException ex = new ValidationException(exMessage);
            LOGGER.warn(exMessage);
            throw ex;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get professor employment. Professor id = " + professorId + "from: " + from +
                    "till: " + till);
        }

        return lessonsQuantity;
    }
}

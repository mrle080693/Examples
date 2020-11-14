package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.database.TimetableDAO;
import com.foxminded.universitytimetable.services.exceptions.DAOException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("timetableServiceBean")
public class TimetableService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TimetableService.class);
    private TimetableDAO timetableDAO;
    private final GroupService groupService;
    private final ProfessorService professorService;

    @Autowired
    public TimetableService(TimetableDAO timetableDAO, GroupService groupService, ProfessorService professorService) {
        this.groupService = groupService;
        this.professorService = professorService;
        this.timetableDAO = timetableDAO;
    }

    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group timetable. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        List<Lesson> lessons;

        try {
            if (groupId < 1) {
                String exMessage = "Input id cant be < 1. Input is: " + groupId;
                ValidationException ex = new ValidationException(exMessage);
                LOGGER.warn(exMessage);
                throw ex;
            }

            lessons = timetableDAO.getGroupTimetable(groupId, from, till);
        } catch (DataAccessException ex) {
            String exMessage = "Cant get group timetable. Group id = " + groupId + "from: " + from + " till: " + till;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (from == null || till == null) {
            String exMessage = " Date is null. from = " + from + " till = " + till;
            ValidationException ex = new ValidationException(exMessage);
            LOGGER.warn(exMessage);
            throw ex;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get group timetable. Group id = " + groupId + "from: " + from + "till: " + till);
        }

        return lessons;
    }

    public List<Lesson> getProfessorTimetable(int professorId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor timetable. Professor id: " + professorId + " from: " + from + "till: "
                    + till);
        }

        List<Lesson> lessons;

        try {
            if (professorId < 1) {
                String exMessage = "Input id cant be < 1. Input is: " + professorId;
                ValidationException ex = new ValidationException(exMessage);
                LOGGER.warn(exMessage);
                throw ex;
            }

            lessons = timetableDAO.getProfessorTimetable(professorId, from, till);
        } catch (DataAccessException ex) {
            String exMessage = "Cant get professor timetable. Professor id = " + professorId + " from: " + from
                    + "till: " + till;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (from == null || till == null) {
            String exMessage = "Date is null.from = " + from + " till = " + till;
            ValidationException ex = new ValidationException(exMessage);
            LOGGER.warn(exMessage);
            throw ex;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get professor timetable. Professor id = " + professorId + "from: " + from +
                    "till: " + till);
        }

        return lessons;
    }
}

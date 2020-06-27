package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.TimetableDAO;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
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
    private final TimetableDAO timetableDAO;
    private final GroupService groupService;
    private final ProfessorService professorService;
    private Logger LOGGER;

    @Autowired
    public TimetableService(TimetableDAO timetableDAO, GroupService groupService, ProfessorService professorService) {
        this.timetableDAO = timetableDAO;
        this.groupService = groupService;
        this.professorService = professorService;
        LOGGER = LoggerFactory.getLogger(TimetableService.class);
    }

    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group timetable. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        List<Lesson> lessons;

        // For check if group exists
        groupService.getById(groupId);

        try {
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

        // For check if professor exists
        professorService.getById(professorId);

        try {
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

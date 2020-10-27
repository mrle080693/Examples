package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.TimetableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TimetableRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final TimetableService timetableService;
    private final ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "                        SORRY :( \n" +
                    "We know about this trouble and will correct it soon");

    @Autowired
    public TimetableRestController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @RequestMapping(Urls.API_REST_GET_TIMETABLE_GROUP_JSON)
    public List<Lesson> getGroupEmployment(@RequestParam int groupId,
                                           @RequestParam("from") Date from,
                                           @RequestParam("till") Date till) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Try get group timetable with id = " + groupId + " from: " + from + " till: " + till);

        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = timetableService.getGroupTimetable(groupId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Successfully got group timetable with " + lessons.size() + " lessons");

        return lessons;
    }

    @RequestMapping(Urls.API_REST_GET_TIMETABLE_PROFESSOR_JSON)
    public List<Lesson> getProfessorEmployment(@RequestParam int professorId,
                                               @RequestParam("from") Date from,
                                               @RequestParam("till") Date till) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Try get professor timetable with id = " + professorId + " from: " + from + " till: " + till);

        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = timetableService.getProfessorTimetable(professorId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Successfully got professor timetable with " + lessons.size() + " lessons");

        return lessons;
    }
}

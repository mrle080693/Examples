package com.foxminded.universitytimetable.api.restcontrollers;

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
@RequestMapping("/rest/timetable")
public class TimetableRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final TimetableService timetableService;

    @Autowired
    public TimetableRestController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @RequestMapping("/group_timetable")
    public List<Lesson> getGroupEmployment(@RequestParam int groupId,
                                           @RequestParam("from") Date from,
                                           @RequestParam("till") Date till) {
        LOGGER.debug("Try get group timetable with id = " + groupId + " from: " + from + " till: " + till);

        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = timetableService.getGroupTimetable(groupId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

        LOGGER.debug("Successfully got group timetable with " + lessons.size() + " lessons");

        return lessons;
    }

    @RequestMapping("/professor_timetable")
    public List<Lesson> getProfessorEmployment(@RequestParam int professorId,
                                               @RequestParam("from") Date from,
                                               @RequestParam("till") Date till) {
        LOGGER.debug("Try get professor timetable with id = " + professorId + " from: " + from + " till: " + till);

        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = timetableService.getProfessorTimetable(professorId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

        LOGGER.debug("Successfully got professor timetable with " + lessons.size() + " lessons");

        return lessons;
    }
}

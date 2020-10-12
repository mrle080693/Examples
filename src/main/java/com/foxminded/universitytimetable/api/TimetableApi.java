package com.foxminded.universitytimetable.api;

import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.TimetableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api_timetable")
public class TimetableApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsApi.class);
    private final TimetableService timetableService;

    @Autowired
    public TimetableApi(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @RequestMapping("/get_group_timetable")
    public List<Lesson> getGroupEmployment(@RequestParam int groupId,
                                           @RequestParam("from") Date from,
                                           @RequestParam("till") Date till) {
        LOGGER.debug("Try get group timetable with id = " + groupId + " from: " + from + " till: " + till);

        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = timetableService.getGroupTimetable(groupId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        if (lessons.isEmpty()) {
            LOGGER.debug("Group have not lessons");
        } else {
            LOGGER.debug("Successfully got group timetable with " + lessons.size() + " lessons");
        }

        return lessons;
    }

    @RequestMapping("/get_professor_timetable")
    public List<Lesson> getProfessorEmployment(@RequestParam int professorId,
                                               @RequestParam("from") Date from,
                                               @RequestParam("till") Date till) {
        LOGGER.debug("Try get professor timetable with id = " + professorId + " from: " + from + " till: " + till);

        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = timetableService.getProfessorTimetable(professorId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        if (lessons.isEmpty()) {
            LOGGER.debug("Professor have not lessons");
        } else {
            LOGGER.debug("Successfully got professor timetable with " + lessons.size() + " lessons");
        }

        return lessons;
    }
}

package com.foxminded.universitytimetable.api.controllers;

import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.TimetableService;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/timetable")
public class TimetableController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private TimetableService timetableService;

    @GetMapping
    public String getTimetablePage() {
        LOGGER.debug("Get statistics.html page");

        return "timetable";
    }

    @GetMapping("/get_group_timetable")
    @ResponseBody
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
        }

        LOGGER.debug("Successfully got group timetable with " + lessons.size() + " lessons");

        return lessons;
    }

    @GetMapping("/get_professor_timetable")
    @ResponseBody
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
        }

        LOGGER.debug("Successfully got professor timetable with " + lessons.size() + " lessons");

        return lessons;
    }
}

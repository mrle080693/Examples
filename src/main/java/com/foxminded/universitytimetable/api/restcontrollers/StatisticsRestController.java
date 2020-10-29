package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.services.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;

@RestController
public class StatisticsRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final StatisticsService statisticsService;
    private final ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "                        SORRY :( \n" +
                    "We know about this trouble and will correct it soon");

    @Autowired
    public StatisticsRestController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @RequestMapping(value = Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON, method = RequestMethod.GET)
    public long getGroupEmployment(@RequestParam int groupId,
                                   @RequestParam("from") Date from,
                                   @RequestParam("till") Date till) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Try to get group employment with id = " + groupId + " from: " + from + " till: " + till);

        long lessonsQuantity = 0;

        try {
            lessonsQuantity = statisticsService.getGroupEmployment(groupId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got with lessons quantity: " + lessonsQuantity);

        return lessonsQuantity;
    }

    @RequestMapping(value = Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON, method = RequestMethod.GET)
    public long getProfessorEmployment(@RequestParam int professorId,
                                       @RequestParam("from") Date from,
                                       @RequestParam("till") Date till) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Try get professor employment with id = " + professorId + " from: " + from + " till: " + till);

        long lessonsQuantity = 0;

        try {
            lessonsQuantity = statisticsService.getProfessorEmployment(professorId, from, till);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got with lessons quantity: " + lessonsQuantity);

        return lessonsQuantity;
    }
}

package com.foxminded.universitytimetable.restcontrollers;

import com.foxminded.universitytimetable.services.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/rest_statistics")
public class StatisticsRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsRestController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @RequestMapping("/get_group_employment")
    public long getGroupEmployment(@RequestParam int groupId,
                                   @RequestParam("from") Date from,
                                   @RequestParam("till") Date till) {
        LOGGER.debug("Try to get group employment with id = " + groupId + " from: " + from + " till: " + till);

        long lessonsQuantity = 0;

        try {
            lessonsQuantity = statisticsService.getGroupEmployment(groupId, from, till);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

        LOGGER.debug("Successfully got with lessons quantity: " + lessonsQuantity);

        return lessonsQuantity;
    }

    @RequestMapping("/get_professor_employment")
    public long getProfessorEmployment(@RequestParam int professorId,
                                       @RequestParam("from") Date from,
                                       @RequestParam("till") Date till) {
        LOGGER.debug("Try get professor employment with id = " + professorId + " from: " + from + " till: " + till);

        long lessonsQuantity = 0;

        try {
            lessonsQuantity = statisticsService.getProfessorEmployment(professorId, from, till);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

        LOGGER.debug("Successfully got with lessons quantity: " + lessonsQuantity);

        return lessonsQuantity;
    }
}

package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/statistics/groups/{id, from, till}")
public class StatisticsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private StatisticsService statisticsService;

    //

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getGroupEmployment(@RequestParam int id, @RequestParam Date from, @RequestParam Date till) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try get statistic of group with id = " + id + " from: " + from
                + " till: " + till);

        ModelAndView modelAndView = new ModelAndView("statistics");
        Integer lessonsQuantity = null;

        try {
            lessonsQuantity = statisticsService.getGroupEmployment(id, from, till);
            modelAndView.addObject("lessonsQuantity", lessonsQuantity);
        } catch (NotFoundEntityException e) {
            // Do nothing
        }

        if (lessonsQuantity != null) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("statistics.html successfully got with lessons quantity: " + lessonsQuantity);
        } else {
            LOGGER.debug("statistics.html successfully got without lessons quantity");
        }

        return modelAndView;
    }
}

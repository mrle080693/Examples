package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll() {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try get groups.html with all groups");

        ModelAndView modelAndView = new ModelAndView("groups");
        List<Group> groups = null;

        try {
            groups = groupService.getAll();
            modelAndView.addObject("groups", groups);
        } catch (NotFoundEntityException e) {
            // Do nothing
        }

        if (groups != null) {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("groups.html successfully got with groups: " + groups.size());
        } else {
            LOGGER.debug("groups.html successfully got without groups");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("id") int id) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try get groups.html with group with id = " + id);

        ModelAndView modelAndView = new ModelAndView("groups");

        try {
            Group group = groupService.getById(id);
            modelAndView.addObject("group", group);
        } catch (NotFoundEntityException e) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            LOGGER.warn("Try to get group with not existing id = " + id);
        }

        LOGGER.debug("groups.html successfully got with group with id = : " + id);

        return modelAndView;
    }

    @PostMapping("/save")
    public String save(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam String newName) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try save group with: " + "id = " + id + " name = " + newName);

        Group group = new Group(newName);
        if (id != 0) {
            try {
                group.setId(id);
                groupService.update(group);
            } catch (ValidationException | NotFoundEntityException e) {
                group.setId(0);
                groupService.add(group);
            }
        } else {
            groupService.add(group);
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully add group with id = " + id);

        return "redirect:/groups";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam int id) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to remove group with id = " + id);

        try {
            groupService.remove(id);
        } catch (NotFoundEntityException e) {
            // Do nothing
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully remove group with id: " + id);

        return "redirect:/groups";
    }
}

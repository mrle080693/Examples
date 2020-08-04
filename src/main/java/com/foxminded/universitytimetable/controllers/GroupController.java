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

@Controller("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;
    private Logger logger = LoggerFactory.getLogger(GroupController.class);

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView getAll() {
        if (logger.isDebugEnabled()) logger.debug("Try get groups.html with all groups");

        List<Group> groups = groupService.getAll();

        ModelAndView modelAndView = new ModelAndView("groups");
        modelAndView.addObject("groups", groups);

        if (logger.isDebugEnabled()) logger.debug("groups.html successfully got with groups: " + groups.size());

        return modelAndView;
    }

    @RequestMapping(value = "/groups/getById/{id}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("id") int id) {
        if (logger.isDebugEnabled()) logger.debug("Try get groups.html with group with id = " + id);

        ModelAndView modelAndView = new ModelAndView("groups");

        try {
            Group group = groupService.getById(id);
            modelAndView.addObject("group", group);
        } catch (NotFoundEntityException e) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        }

        if (logger.isDebugEnabled()) logger.debug("groups.html successfully got with group with id = : " + id);

        return modelAndView;
    }

    @PostMapping("/groups/save")
    public String save(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam String newName) {
        if (logger.isDebugEnabled()) logger.debug("Try save group with: " + "id = " + id + " name = " + newName);

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

        if (logger.isDebugEnabled()) logger.debug("Successfully add group with id = " + id);

        return "redirect:/groups";
    }

    @PostMapping("/groups/remove")
    public String remove(@RequestParam int id) {
        if (logger.isDebugEnabled()) logger.debug("Try to remove group with id = " + id);

        try {
            groupService.remove(id);
        } catch (NotFoundEntityException e) {
            // Do nothing
        }

        if (logger.isDebugEnabled()) logger.debug("Successfully remove group with id: " + id);

        return "redirect:/groups";
    }
}

package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("/groups")
public class GroupController {
    @Autowired
    GroupService groupService;
    private Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @GetMapping("/groups")
    public String read(Model model) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try get groups.html with all groups");
        }

        List<Group> groups = groupService.getAll();

        model.addAttribute("groups", groups);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("groups.html successfully got with groups: " + groups);
        }

        return "groups";
    }

    @PostMapping("/groups/save")
    public String save(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam String newName) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try save group with: " + "id = " + id + " name = " + newName);
        }

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

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add group with id = " + id);
        }

        return "redirect:/groups";
    }

    @PostMapping("/groups/remove")
    public String remove(@RequestParam int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove group with id = " + id);
        }

        try {
            groupService.remove(id);
        } catch (NotFoundEntityException e) {
            // Do nothing
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove group with id: " + id);
        }

        return "redirect:/groups";
    }

    // lessons.html have to return
}

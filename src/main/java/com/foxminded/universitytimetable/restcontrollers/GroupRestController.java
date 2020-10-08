package com.foxminded.universitytimetable.restcontrollers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/rest_groups")
public class GroupRestController {
    private final GroupService groupService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRestController.class);

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int add(@RequestParam Group group) {
        LOGGER.debug("Try save group: " + group.toString());
        int id = 0;

        try {
            id = groupService.add(group);
        } catch (ValidationException e) {
            LOGGER.warn(e.getEntityValidationExceptionMessage());
        }

        LOGGER.debug("Successfully save group " + group.toString());

        return id;
    }

    @RequestMapping("/get_all")
    public List<Group> getAll() {
        LOGGER.debug("Try get all groups");

        List<Group> groups = null;

        try {
            groups = groupService.getAll();
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        if (groups != null) {
            LOGGER.debug("Successfully got with groups: " + groups.size());
        } else {
            LOGGER.debug("Successfully got without groups");
        }

        return groups;
    }

    @RequestMapping("/get_by_id")
    public Group getById(@RequestParam int id) {
        LOGGER.debug("Try get group with id = " + id);
        Group group = null;

        try {
            group = groupService.getById(id);
        } catch (NotFoundEntityException e) {
            LOGGER.warn("Try to get group with not existing id = " + id);
        }

        if (group != null) {
            LOGGER.debug("Successfully got group: " + group.toString());
        }

        return group;
    }

    @RequestMapping("/get_by_name")
    public List<Group> getByName(@RequestParam String name) {
        LOGGER.debug("Try to get groups with name = " + name);
        List<Group> groups = null;

        try {
            groups = groupService.getByName(name);
        } catch (NotFoundEntityException e) {
            LOGGER.warn("No groups with name = " + name);
        }

        return groups;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestParam Group group) {
        LOGGER.debug("Try to update group" + group.toString());
        int status = 0;

        try {
            status = groupService.update(group);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        LOGGER.debug("Successfully updated");

        return status;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int remove(@RequestParam int groupId) {
        LOGGER.debug("Try to remove group with id = " + groupId);

        try {
            groupService.remove(groupId);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        LOGGER.debug("Successfully remove group with id: " + groupId);

        return groupService.remove(groupId);
    }
}

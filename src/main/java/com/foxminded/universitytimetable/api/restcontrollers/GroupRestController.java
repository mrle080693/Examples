package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {
    private final GroupService groupService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRestController.class);
    private final ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "                        SORRY :( \n" +
                    "We know about this trouble and will correct it soon");

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Group add(@RequestParam String name) {
        LOGGER.debug("Try save group with: " + " name = " + name);
        Group group = new Group(name);

        try {
            group = groupService.add(group);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            // For logging troubles we dont know
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        LOGGER.debug("Successfully save group " + group);

        return group;
    }

    @RequestMapping
    public List<Group> getAll() {
        LOGGER.debug("Try get all groups");

        List<Group> groups = new ArrayList<>();

        try {
            groups = groupService.getAll();
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        LOGGER.debug("Successfully got with groups: " + groups.size());

        return groups;
    }

    @RequestMapping("/{id}")
    public Group getById(@PathVariable int id) {
        LOGGER.debug("Try get group with id = " + id);
        Group group = new Group();

        try {
            group = groupService.getById(id);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        LOGGER.debug("Successfully got group: " + group);

        return group;
    }

    @RequestMapping("/{name}")
    public List<Group> getByName(@PathVariable String name) {
        LOGGER.debug("Try to get groups with name = " + name);
        List<Group> groups = new ArrayList<>();

        try {
            groups = groupService.getByName(name);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        LOGGER.debug("Successfully got with groups quantity: " + groups.size());

        return groups;
    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public Group update(@RequestParam int id, @RequestParam String name) {
        LOGGER.debug("Try to update group with id = " + id);
        Group group = new Group();

        try {
            group.setId(id);
            group.setName(name);

            group = groupService.update(group);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        LOGGER.debug("Successfully updated");

        return group;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int remove(@RequestParam int groupId) {
        LOGGER.debug("Try to remove group with id = " + groupId);
        int status = 0;

        try {
            status = groupService.remove(groupId);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        LOGGER.debug("Successfully remove group with id: " + groupId);

        return status;
    }
}

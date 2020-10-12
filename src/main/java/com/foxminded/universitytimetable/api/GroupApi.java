package com.foxminded.universitytimetable.api;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api_groups")
public class GroupApi {
    private final GroupService groupService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupApi.class);

    @Autowired
    public GroupApi(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int add(@RequestParam Group group) {
        LOGGER.debug("Try save group: " + group.toString());
        int id = 0;

        try {
            id = groupService.add(group);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
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
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
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
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
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
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        return groups;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestParam Group group) {
        LOGGER.debug("Try to update group" + group.toString());
        int status = 0;

        try {
            status = groupService.update(group);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully updated");

        return status;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int remove(@RequestParam int groupId) {
        LOGGER.debug("Try to remove group with id = " + groupId);

        try {
            groupService.remove(groupId);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully remove group with id: " + groupId);

        return groupService.remove(groupId);
    }
}

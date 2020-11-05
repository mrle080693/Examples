package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.api.constants.Urls;
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
public class GroupRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRestController.class);
    private final GroupService groupService;
    private final ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "\n                        SORRY :( \n" +
                    "We know about this trouble and will correct it soon");

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = Urls.API_REST_POST_GROUP_JSON, method = RequestMethod.POST)
    public Group add(@RequestParam String name) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try save group with: " + " name = " + name);
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

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully save group " + group);

        return group;
    }

    @RequestMapping(value = Urls.API_REST_GET_GROUPS_JSON, method = RequestMethod.GET)
    public List<Group> getAll() {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try get all groups");

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

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got with groups: " + groups.size());

        return groups;
    }

    @RequestMapping(value = Urls.API_REST_GET_GROUP_JSON_BY_ID, method = RequestMethod.GET)
    public Group getById(@PathVariable int id) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try get group with id = " + id);
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

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got group: " + group);

        return group;
    }

    @RequestMapping(value = Urls.API_REST_GET_GROUP_JSON_BY_NAME, method = RequestMethod.GET)
    public List<Group> getByName(@PathVariable String name) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to get groups with name = " + name);
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

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got with groups quantity: " + groups.size());

        return groups;
    }

    @RequestMapping(value = Urls.API_REST_PUT_GROUP_JSON, method = RequestMethod.PUT)
    public Group update(@RequestParam int id, @RequestParam String name) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to update group with id = " + id);
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

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully updated");

        return group;
    }

    @RequestMapping(value = Urls.API_REST_DELETE_GROUP_JSON, method = RequestMethod.DELETE)
    public Integer remove(@RequestParam int groupid) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to remove group with id = " + groupid);
        Integer status;

        try {
            status = groupService.remove(groupid);
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

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully remove group with id: " + groupid);

        return status;
    }
}

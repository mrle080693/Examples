package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.database.GroupDAO;
import com.foxminded.universitytimetable.services.exceptions.DAOException;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("groupServiceBean")
public class GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    private final GroupDAO groupDAO;

    @Autowired
    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public Group add(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group: " + group);
        }

        Group returnedGroup;

        try {
            checkGroup(group);

            if (group.getId() != 0) {
                String exMessage = "New group id is not 0. Actual value is: " + group.getId();
                ValidationException ex = new ValidationException(exMessage);
                LOGGER.warn(exMessage);
                throw ex;
            }

            returnedGroup = groupDAO.add(group);
        } catch (DataAccessException ex) {
            String exMessage = "Cant add group: " + group;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add group: " + group);
        }

        return returnedGroup;
    }

    public List<Group> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table groups");
        }

        List<Group> groups;

        try {
            groups = groupDAO.getAll();

            if (groups.isEmpty()) {
                String exMessage = "Table groups is empty";
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.warn(exMessage);
                throw ex;
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant get all from table groups";
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    public Group getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by id = " + id);
        }

        Group group = null;

        try {
            if (id == 0) {
                String exMessage = "Group id is 0. " + group;
                ValidationException ex = new ValidationException(exMessage);
                LOGGER.warn(exMessage);
                throw ex;
            }

            group = groupDAO.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            String exMessage = "Table groups have not groups with id = " + id;
            LOGGER.warn(exMessage);
            throw new NotFoundEntityException(exMessage, ex);
        } catch (DataAccessException ex) {
            String exMessage = "Cant get group from DB with id = " + id;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + group);
        }

        return group;
    }

    public List<Group> getByName(String name) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by name = " + name);
        }

        List<Group> groups;

        try {
            if (name == null) {
                String exMessage = "Input name is null";
                ValidationException ex = new ValidationException(exMessage);
                LOGGER.warn("ValidationException \n {}", exMessage);
                throw ex;
            }

            if (name.trim().isEmpty()) {
                String exMessage = "Group name  is empty";
                ValidationException ex = new ValidationException(exMessage);
                LOGGER.warn("ValidationException \n {}", exMessage);
                throw ex;
            }

            groups = groupDAO.getByName(name);
        } catch (EmptyResultDataAccessException ex) {
            String exMessage = "Table groups have not groups with name = " + name;
            LOGGER.warn(exMessage);
            throw new NotFoundEntityException(exMessage, ex);
        } catch (DataAccessException ex) {
            String exMessage = "Cant get group from DB with name = " + name;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    public Group update(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update group: " + group);
        }

        Group returnedGroup;

        try {
            checkGroup(group);

            if (group.getId() == 0) {
                String exMessage = "Group id is 0." + group;
                ValidationException ex = new ValidationException(exMessage);
                LOGGER.warn(exMessage);
                throw ex;
            }

            returnedGroup = groupDAO.update(group);

            if (returnedGroup.getId() != 1) {
                String exMessage = "Group with input id doesnt exist. " + group;
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.warn(exMessage);
                throw ex;
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant update table groups. Input group: " + group;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update group: " + group);
        }

        return returnedGroup;
    }

    public Group remove(int groupId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove group with id = " + groupId);
        }

        Group group;

        try {
            group = groupDAO.remove(groupId);
        } catch (DataAccessException ex) {
            String exMessage = "Cant remove from table groups. Group id: " + groupId;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove group with id: " + groupId);
        }

        return group;
    }

    private void checkGroup(Group group) {
        if (group == null) {
            String exMessage = "Group is null";
            IllegalArgumentException ex = new IllegalArgumentException(exMessage);
            LOGGER.warn(exMessage);
            throw ex;
        }

        String groupName = group.getName();

        if (groupName == null) {
            String exMessage = "Group is not have name";
            ValidationException ex = new ValidationException(exMessage);
            LOGGER.warn(exMessage);
            throw ex;
        }

        if (groupName.trim().isEmpty()) {
            String exMessage = "Group name is empty";
            ValidationException ex = new ValidationException(exMessage);
            LOGGER.warn(exMessage);
            throw ex;
        }
    }
}

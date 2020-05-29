package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.EntityValidationException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service("groupServiceBean")
public class GroupService {
    private final GroupDAO groupDAO;
    private static Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public int add(Group group) {
        int groupIdInTable;
        checkGroup(group);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group with id = " + group.getId());
        }

        try {
            if (group.getId() != 0) {
                throw new EntityValidationException("New group id must be 0. \n" +
                        "If you want update group you have to use update method");
            }

            groupIdInTable = groupDAO.add(group);
        } catch (DataAccessException ex) {
            String exMessage = "Cant add group";
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successful");
        }

        return groupIdInTable;
    }

    public List<Group> getAll() {
        List<Group> groups;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table groups");
        }

        try {
            groups = groupDAO.getAll();

            if (groups.isEmpty()) {
                throw new NotFoundEntityException("Table groups is empty");
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant get all from table groups";
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successful");
        }

        return groups;
    }

    public Group getById(int id) {
        Group group;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by id = " + id);
        }

        try {
            if (id == 0) {
                throw new EntityValidationException("Group id cant be 0");
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
            LOGGER.debug("Successful");
        }

        return group;
    }

    public List<Group> getByName(String name) {
        List<Group> groups;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by name = " + name);
        }

        try {
            if (name == null) {
                throw new EntityValidationException("Group must have name");
            }

            if (name.trim().isEmpty()) {
                throw new EntityValidationException("Group name must not be empty");
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
            LOGGER.debug("Successful");
        }

        return groups;
    }

    public int update(Group group) {
        int status;
        checkGroup(group);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update group with id = " + group.getId());
        }

        try {

            if (group.getId() == 0) {
                throw new EntityValidationException("New group id must not be 0. \n" +
                        "If you want add new group you have to use add method");
            }

            status = groupDAO.update(group);

            if (status != 1) {
                throw new NotFoundEntityException("Group with input id doesnt exist");
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant update table groups";
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successful");
        }

        return status;
    }

    public int remove(int groupId) {
        int status;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove group with id = " + groupId);
        }

        try {
            status = groupDAO.remove(groupId);

            if (status != 1) {
                throw new NotFoundEntityException("Group with input id doesnt exist");
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant remove from table groups";
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successful");
        }

        return status;
    }

    private void checkGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Group for add or update cant be null");
        }

        String name = group.getName();

        if (name == null) {
            throw new EntityValidationException("Group must have name");
        }

        if (name.trim().isEmpty()) {
            throw new EntityValidationException("Group name must not be empty");
        }
    }
}

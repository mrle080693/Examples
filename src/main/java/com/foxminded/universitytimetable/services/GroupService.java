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
    private static Logger LOGGER;

    @Autowired
    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
        LOGGER = LoggerFactory.getLogger(GroupService.class);
    }

    public int add(Group group) {
        int groupIdInTable;

        if (group == null) {
            String exMessage = "Group is null";
            IllegalArgumentException ex = new IllegalArgumentException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group with id = " + group.getId());
        }

        checkGroup(group);

        try {
            if (group.getId() != 0) {
                String exMessage = "New group id is not 0. Actual value is: " + group.getId();
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }

            groupIdInTable = groupDAO.add(group);
        } catch (DataAccessException ex) {
            String exMessage = "Cant add group: " + group;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add group: " + group);
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
                String exMessage = "Table groups is empty";
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.error(exMessage);
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
        Group group = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by id = " + id);
        }

        try {
            if (id == 0) {
                String exMessage = "Group id is 0. " + group;
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
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
        List<Group> groups;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by name = " + name);
        }

        try {
            if (name == null) {
                String exMessage = "Group isn't have name";
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }

            if (name.trim().isEmpty()) {
                String exMessage = "Group name  is empty";
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
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

    public int update(Group group) {
        int status;

        if (group == null) {
            String exMessage = "Group is null ";
            IllegalArgumentException ex = new IllegalArgumentException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        checkGroup(group);


        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update group: " + group);
        }

        try {

            if (group.getId() == 0) {
                String exMessage = "Group id is 0";
                EntityValidationException ex = new EntityValidationException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }

            status = groupDAO.update(group);

            if (status != 1) {
                String exMessage = "Group with input id doesnt exist. Id is: " + group.getId();
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.error(exMessage);
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
                String exMessage = "Group with input id: " + groupId + " does not exist";
                NotFoundEntityException ex = new NotFoundEntityException(exMessage);
                LOGGER.error(exMessage);
                throw ex;
            }
        } catch (DataAccessException ex) {
            String exMessage = "Cant remove from table groups. Group id: " + groupId;
            LOGGER.error(exMessage);
            throw new DAOException(exMessage, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove group with id: " + groupId);
        }

        return status;
    }

    private void checkGroup(Group group) {
        String name = group.getName();

        if (name == null) {
            String exMessage = "Group is not have name";
            EntityValidationException ex = new EntityValidationException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }

        if (name.trim().isEmpty()) {
            String exMessage = "Group name is empty";
            EntityValidationException ex = new EntityValidationException(exMessage);
            LOGGER.error(exMessage);
            throw ex;
        }
    }
}

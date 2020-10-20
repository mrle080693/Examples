package com.foxminded.universitytimetable.database.impl;

import com.foxminded.universitytimetable.database.GroupDAO;
import com.foxminded.universitytimetable.database.impl.repositories.GroupRepository;
import com.foxminded.universitytimetable.models.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("groupImplBean")
public class GroupImpl implements GroupDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupImpl.class);
    private GroupRepository groupRepository;

    @Autowired
    public GroupImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group add(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group: " + group);
        }

        Group returnedGroup = groupRepository.save(group);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add group: " + returnedGroup);
        }

        return returnedGroup;
    }

    @Override
    public List<Group> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table groups");
        }

        List<Group> groups = null;

        groups = groupRepository.findAll();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    @Override
    public Group getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by id = " + id);
        }

        Group group = groupRepository.getOne(id);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + group);
        }

        return group;
    }

    @Override
    public List<Group> getByName(String name) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by name = " + name);
        }

        List<Group> groups = groupRepository.getByName(name);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    @Override
    public Group update(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update group: " + group);
        }

        Group returnedGroup = groupRepository.save(group);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update group with id = " + returnedGroup);
        }

        return returnedGroup;
    }

    @Override
    public int remove(int groupId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove group with id = " + groupId);
        }

        int status = groupRepository.remove(groupId);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove group with id: " + groupId);
        }

        return status;
    }
}

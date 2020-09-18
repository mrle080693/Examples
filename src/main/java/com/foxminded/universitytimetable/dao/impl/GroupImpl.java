package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.dao.impl.repositories.GroupRepository;
import com.foxminded.universitytimetable.models.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("groupImplBean")
public class GroupImpl implements GroupDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupImpl.class);
    private static GroupRepository groupRepository;

    @Override
    public int add(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group: " + group);
        }

        int id = groupRepository.save(group).getId();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add group with id = " + id);
        }

        return id;
    }

    @Override
    public List<Group> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table groups");
        }

        List<Group> groups = null;

        try {
            groups = groupRepository.findAll();
        } catch (Exception e){
            e.printStackTrace();
        }
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
    public int update(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update group: " + group);
        }

        int id = groupRepository.save(group).getId();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update group with id = " + id);
        }

        return id;
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

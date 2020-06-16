package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.GroupMapper;
import com.foxminded.universitytimetable.models.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository("groupImplBean")
public class GroupImpl implements GroupDAO {
    private final JdbcTemplate jdbcTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(GroupImpl.class);

    @Autowired
    public GroupImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group: " + group);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(Queries.ADD_GROUP_QUERY, new String[]{"id"});
                    ps.setString(1, group.getName());
                    return ps;
                }
                , keyHolder);

        Number id = keyHolder.getKey();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add group with id = " + id);
        }

        return (int) id;
    }

    public List<Group> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table groups");
        }

        List<Group> groups = jdbcTemplate.query(Queries.GET_ALL_GROUPS_QUERY, new GroupMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    public Group getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by id = " + id);
        }

        Group group = jdbcTemplate.queryForObject(Queries.GET_GROUP_BY_ID_QUERY, new Object[]{id},
                new GroupMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + group);
        }

        return group;
    }

    public List<Group> getByName(String name) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by name = " + name);
        }

        List<Group> groups = jdbcTemplate.query(Queries.GET_GROUP_BY_NAME_QUERY, new Object[]{name},
                new GroupMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    public int update(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update group: " + group);
        }

        int status = jdbcTemplate.update(Queries.UPDATE_GROUP_QUERY, group.getName(), group.getId());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update group with status = " + status);
        }

        return status;
    }

    public int remove(int groupId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove group with id = " + groupId);
        }

        int status = jdbcTemplate.update(Queries.REMOVE_GROUP_QUERY, groupId);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove group with id: " + groupId);
        }

        return status;
    }
}

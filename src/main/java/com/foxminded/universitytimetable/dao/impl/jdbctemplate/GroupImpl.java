package com.foxminded.universitytimetable.dao.impl.jdbctemplate;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.dao.queries.SQLQueries;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.rowmappers.GroupMapper;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group: " + group);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(SQLQueries.ADD_GROUP, new String[]{"id"});
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

        List<Group> groups = jdbcTemplate.query(SQLQueries.GET_ALL_GROUPS, new GroupMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    public Group getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by id = " + id);
        }

        Group group = jdbcTemplate.queryForObject(SQLQueries.GET_GROUP_BY_ID, new Object[]{id},
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

        List<Group> groups = jdbcTemplate.query(SQLQueries.GET_GROUPS_BY_NAME, new Object[]{name},
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

        int status = jdbcTemplate.update(SQLQueries.UPDATE_GROUP, group.getName(), group.getId());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update group with status = " + status);
        }

        return status;
    }

    public int remove(int groupId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove group with id = " + groupId);
        }

        int status = jdbcTemplate.update(SQLQueries.DELETE_GROUP, groupId);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove group with id: " + groupId);
        }

        return status;
    }
}

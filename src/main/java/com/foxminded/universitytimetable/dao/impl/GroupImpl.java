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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger LOGGER= LoggerFactory.getLogger(GroupImpl.class);

    public int add(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(Queries.ADD_GROUP_QUERY, new String[]{"id"});
                    ps.setString(1, group.getName());
                    return ps;
                }
                , keyHolder);

        Number id = keyHolder.getKey();

        return (int) id;
    }

    public List<Group> getAll() {
        List<Group> groups;
        groups = jdbcTemplate.query(Queries.GET_ALL_GROUPS_QUERY, new GroupMapper());
        return groups;
    }

    public Group getById(int id) {
        Group group;
        group = jdbcTemplate.queryForObject(Queries.GET_GROUP_BY_ID_QUERY, new Object[]{id},
                new GroupMapper());

        return group;
    }

    public List<Group> getByName(String name) {
        List<Group> groups;
        groups = jdbcTemplate.query(Queries.GET_GROUP_BY_NAME_QUERY, new Object[]{name},
                new GroupMapper());
        return groups;
    }

    public int update(Group group) {
        int status = jdbcTemplate.update(Queries.UPDATE_GROUP_QUERY, group.getName(), group.getId());
        return status;
    }

    public int remove(int groupId) {
        int status = jdbcTemplate.update(Queries.REMOVE_GROUP_QUERY, groupId);
        return status;
    }
}

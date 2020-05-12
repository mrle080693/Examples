package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.GroupMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public int add(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(Queries.ADD_GROUP_QUERY, new String[]{"id"});
                        ps.setString(1, group.getName());
                        return ps;
                    }
                    , keyHolder);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant add group", ex);
        }

        Number id = keyHolder.getKey();
        return (int) id;
    }

    public List<Group> getAll() {
        List<Group> groups;

        try {
            groups = jdbcTemplate.query(Queries.GET_ALL_GROUPS_QUERY, new GroupMapper());

        } catch (DataAccessException ex) {
            throw new DAOException("Cant get all from table groups", ex);
        }

        return groups;
    }

    public Group getById(int id) {
        Group group;

        try {
            group = jdbcTemplate.queryForObject(Queries.GET_GROUP_BY_ID_QUERY, new Object[]{id},
                    new GroupMapper());

        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Table groups have not rows with input id", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get by id from table groups", ex);
        }

        return group;
    }

    public List<Group> getByName(String name) {
        List<Group> groups;

        try {
            groups = jdbcTemplate.query(Queries.GET_GROUP_BY_NAME_QUERY, new Object[]{name},
                    new GroupMapper());

        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Table groups have not rows with input name", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get by name from table groups", ex);
        }

        return groups;
    }

    public int update(Group group) {
        try {
            int status = jdbcTemplate.update(Queries.UPDATE_GROUP_QUERY, group.getName(), group.getId());

            return status;
        } catch (DataAccessException ex) {
            throw new DAOException("Cant update table groups", ex);
        }
    }

    public int remove(int groupId) {
        try {
            int status = jdbcTemplate.update(Queries.REMOVE_GROUP_QUERY, groupId);

            return status;
        } catch (DataAccessException ex) {
            throw new DAOException("Cant remove element of table groups", ex);
        }
    }
}

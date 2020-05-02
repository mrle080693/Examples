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
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupImpl implements GroupDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(Group group) {
        try {
            jdbcTemplate.update(Queries.ADD_GROUP_QUERY, group.getName());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant add group", dae);
        }
    }

    public List<Group> getAll() {
        List<Group> groups;

        try {
            groups = jdbcTemplate.query(Queries.GET_ALL_GROUPS_QUERY, new GroupMapper());

        } catch (DataAccessException dae) {
            throw new DAOException("Cant get all from table groups", dae);
        }

        return groups;
    }

    public Group getById(int id) {
        Group group;

        try {
            group = jdbcTemplate.queryForObject(Queries.GET_GROUP_BY_ID_QUERY, new Object[]{id},
                    new GroupMapper());

        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundEntityException("Wrong id", e);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get by id from table groups", dae);
        }

        return group;
    }

    public List<Group> getByName(String name) {
        List<Group> groups;

        try {
            groups = jdbcTemplate.query(Queries.GET_GROUP_BY_NAME_QUERY, new Object[]{name},
                    new GroupMapper());

        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundEntityException("No such name", e);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get by name from table groups", dae);
        }

        return groups;
    }

    public void update(Group group) {
        try {
            jdbcTemplate.update(Queries.UPDATE_GROUP_QUERY, group.getName(), group.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant update table groups", dae);
        }
    }

    public void remove(Group group) {
        try {
            jdbcTemplate.update(Queries.REMOVE_GROUP_QUERY, group.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant remove element of table groups", dae);
        }
    }
}

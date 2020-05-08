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

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class GroupImpl implements GroupDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        if (group == null || group.getName() == null) {
            throw new NullPointerException("Group and group name cant be null");
        }

        if (group.getId() != 0 || group.getName()
                .trim()
                .equals("")) {
            throw new IllegalArgumentException("1) Id for new group must be 0 becouse its auto generated in db." +
                    " If you want to update existing group you have to use update method" +
                    "\n 2) Group name cant be empty string or string with only separators");
        }

        try {
            jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(Queries.ADD_GROUP_QUERY, new String[]{"id"});
                        ps.setString(1, group.getName());
                        return ps;
                    }
                    , keyHolder);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant add group", dae);
        }

        Number id = keyHolder.getKey();
        return (int) id;
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

        if (name == null) {
            throw new NullPointerException("Group name cant be null");
        }

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

    public int update(Group group) {
        try {
            return jdbcTemplate.update(Queries.UPDATE_GROUP_QUERY, group.getName(), group.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant update table groups", dae);
        }
    }

    public int remove(Group group) {
        try {
            return jdbcTemplate.update(Queries.REMOVE_GROUP_QUERY, group.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant remove element of table groups", dae);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate.setDataSource(dataSource);
    }
}

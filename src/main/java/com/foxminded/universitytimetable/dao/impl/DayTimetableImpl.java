package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.DayTimetableDAO;
import com.foxminded.universitytimetable.dao.impl.rowmappers.DayTimetableMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.DayTimetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class DayTimetableImpl implements DayTimetableDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String ADD_DAY_TIMETABLE_QUERY = "INSERT INTO day_timetable (date) values(?)";
    private final String GET_ALL_DAY_TIMETABLE_QUERY = "select id, date from day_timetable";
    private final String GET_BY_ID_DAY_TIMETABLE_QUERY = "select id, date from day_timetable where id = ?";
    private final String GET_BY_DATE_DAY_TIMETABLE_QUERY = "select id, date from day_timetable where date = ?";
    private final String UPDATE_DAY_TIMETABLE_QUERY = "update day_timetable set date = ? where id = ?";
    private final String REMOVE_DAY_TIMETABLE_QUERY = "delete from day_timetable where id = ?";

    public void add(DayTimetable dayTimetable) {
        try {
            jdbcTemplate.update(ADD_DAY_TIMETABLE_QUERY, dayTimetable.getDate());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get all from table dayTimetable", dae);
        }
    }

    public List<DayTimetable> getAll() {
        List<DayTimetable> dayTimetables = null;

        try {
            dayTimetables = jdbcTemplate.query(GET_ALL_DAY_TIMETABLE_QUERY, new DayTimetableMapper());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get all from table dayTimetable", dae);
        }

        return dayTimetables;
    }

    public DayTimetable getById(int id) {
        DayTimetable timetableForDay = null;

        try {
            timetableForDay = jdbcTemplate.queryForObject(GET_BY_ID_DAY_TIMETABLE_QUERY, new Object[]{id},
                    new DayTimetableMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundEntityException("Wrong id", e);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get by id from table dayTimetable", dae);
        }

        return timetableForDay;
    }

    public DayTimetable getByDate(Date date) {
        DayTimetable timetableForDay = null;

        try {
            timetableForDay = jdbcTemplate.queryForObject(GET_BY_DATE_DAY_TIMETABLE_QUERY, new Object[]{date},
                    new DayTimetableMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundEntityException("No such date", e);
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get by date from table dayTimetable", dae);
        }

        return timetableForDay;
    }

    public void update(DayTimetable dayTimetable) {
        try {
            jdbcTemplate.update(UPDATE_DAY_TIMETABLE_QUERY, dayTimetable.getDate(), dayTimetable.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant update table dayTimetable", dae);
        }
    }

    public void remove(DayTimetable dayTimetable) {
        try {
            jdbcTemplate.update(REMOVE_DAY_TIMETABLE_QUERY, dayTimetable.getId());
        } catch (DataAccessException dae) {
            throw new DAOException("Cant remove element of table dayTimetable", dae);
        }
    }
}

package com.foxminded.universitytimetable.dao.impl.rowmappers;

import com.foxminded.universitytimetable.models.DayTimetable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DayTimetableMapper implements RowMapper<DayTimetable> {
    public DayTimetable mapRow(ResultSet resultSet, int i) throws SQLException {
        DayTimetable dayTimetable = new DayTimetable();

        dayTimetable.setId(resultSet.getInt("id"));
        dayTimetable.setDate(resultSet.getDate("date"));

        return dayTimetable;
    }
}

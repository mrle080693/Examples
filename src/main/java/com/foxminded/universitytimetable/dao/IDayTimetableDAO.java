package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.DayTimetable;

import java.util.Date;
import java.util.List;

public interface IDayTimetableDAO {
    // Create
    void add(DayTimetable dayTimetable);

    // Read
    List<DayTimetable> getAll();

    DayTimetable getByDate(Date date);

    // Update
    void update(DayTimetable dayTimetable);

    // Delete
    void remove(DayTimetable dayTimetable);
}

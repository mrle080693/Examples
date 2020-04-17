package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.DayTimetable;

import java.util.Date;
import java.util.List;

public interface DayTimetableDAO {
    void add(DayTimetable dayTimetable);

    List<DayTimetable> getAll();

    DayTimetable getById(int id);

    DayTimetable getByDate(Date date);

    void update(DayTimetable dayTimetable);

    void remove(DayTimetable dayTimetable);
}

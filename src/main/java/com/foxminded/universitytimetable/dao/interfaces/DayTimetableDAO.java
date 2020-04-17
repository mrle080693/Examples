package com.foxminded.universitytimetable.dao.interfaces;

import com.foxminded.universitytimetable.dao.models.DayTimetable;

import java.util.Date;
import java.util.List;

public interface DayTimetableDAO {
    void add(DayTimetable dayTimetable);

    List<DayTimetable> getAll();

    DayTimetable getByDate(Date date);

    void update(DayTimetable dayTimetable);

    void remove(DayTimetable dayTimetable);
}

package com.foxminded.universitytimetable.db.dao.daomodels;

import com.foxminded.universitytimetable.db.models.DayTimetable;

import java.util.Date;
import java.util.List;

public interface DayTimetableDAO {
    void add(DayTimetable dayTimetable);

    List<DayTimetable> getAll();

    DayTimetable getByDate(Date date);

    void update(DayTimetable dayTimetable);

    void remove(DayTimetable dayTimetable);
}

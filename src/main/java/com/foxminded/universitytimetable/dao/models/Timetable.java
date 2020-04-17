package com.foxminded.universitytimetable.dao.models;

import java.util.List;

public class Timetable {
    private int id;
    private List<DayTimetable> daysTimetable;

    public Timetable() {
    }

    public Timetable(int id, List<DayTimetable> daysTimetable) {
        this.id = id;
        this.daysTimetable = daysTimetable;
    }

    public List<DayTimetable> getDaysTimetable() {
        return daysTimetable;
    }

    public void setDaysTimetable(List<DayTimetable> daysTimetable) {
        this.daysTimetable = daysTimetable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

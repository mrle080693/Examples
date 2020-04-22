package com.foxminded.universitytimetable.models;

import java.util.Date;
import java.util.List;

public class DayTimetable {
    private int id;
    private List<Lesson> lessons;
    private Date date;

    public DayTimetable() {
    }

    public DayTimetable(Timetable timetableId, List<Lesson> lessons, Date date) {
        this.lessons = lessons;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

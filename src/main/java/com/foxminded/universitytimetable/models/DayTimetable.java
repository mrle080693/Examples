package com.foxminded.universitytimetable.models;

import java.util.Date;
import java.util.List;

public class DayTimetable {
    private int id = 0;
    private List<Lesson> lessons = null;
    private Date date = null;

    public DayTimetable() {
    }

    public DayTimetable(List<Lesson> lessons, Date date) {
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

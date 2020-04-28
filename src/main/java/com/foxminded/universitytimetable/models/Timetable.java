package com.foxminded.universitytimetable.models;

import java.util.List;

public class Timetable {
    private int id;
    private List<Lesson> lessons;

    public Timetable() {
    }

    public Timetable(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

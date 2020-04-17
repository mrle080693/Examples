package com.foxminded.universitytimetable.dao.models;

public class Group {
    private int id;
    private String name;

    public Group() {
    }

    public Group(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

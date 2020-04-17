package com.foxminded.universitytimetable.dao.models;

public class Professor {
    private int id;
    private String fullName;
    private String subject;

    public Professor() {
    }

    public Professor(int id, String fullName, String subject) {
        this.id = id;
        this.fullName = fullName;
        this.subject = subject;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

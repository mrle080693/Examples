package com.foxminded.universitytimetable.models;

public class Professor {
    private int id;
    private String name;
    private String surName;
    private String patronymic;
    private String subject;

    public Professor() {
    }

    public Professor(int id, String name, String surName, String patronymic, String subject) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.patronymic = patronymic;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}

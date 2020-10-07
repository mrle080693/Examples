package com.foxminded.universitytimetable.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "professors")
@Proxy(lazy =false)
public class Professor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name = "";
    @Column(name = "surname")
    private String surname = "";
    @Column(name = "patronymic")
    private String patronymic = "";
    @Column(name = "subject")
    private String subject = "";

    public Professor() {
    }

    public Professor(String name, String surname, String patronymic, String subject) {
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}

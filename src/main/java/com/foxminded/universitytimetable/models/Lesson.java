package com.foxminded.universitytimetable.models;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "lessons")
@Proxy(lazy =false)
public class Lesson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private Date date = null;
    @Column(name = "lessonNumber")
    private int lessonNumber = 0;
    @Column(name = "groupId")
    private int groupId = 0;
    @Column(name = "professorId")
    private int professorId = 0;
    @Column(name = "building")
    private String building = "";
    @Column(name = "classroom")
    private String classroom = "";

    public Lesson() {
    }

    public Lesson(Date date, int lessonNumber, int groupId, int professorId, String building, String classroom) {
        this.date = date;
        this.lessonNumber = lessonNumber;
        this.groupId = groupId;
        this.professorId = professorId;
        this.building = building;
        this.classroom = classroom;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

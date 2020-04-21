package com.foxminded.universitytimetable.models;

public class Lesson {
    private int id;
    private int lessonNumber;
    private Group group;
    private Professor professor;
    private String building;
    private String classroom;

    public Lesson() {
    }

    public Lesson(int id, DayTimetable dayTimetable, int lessonNumber, Group group, Professor professor, String building, String classroom) {
        this.id = id;
        this.lessonNumber = lessonNumber;
        this.group = group;
        this.professor = professor;
        this.building = building;
        this.classroom = classroom;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
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
}

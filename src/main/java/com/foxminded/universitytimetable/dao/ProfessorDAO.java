package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.models.Timetable;

import java.util.Date;
import java.util.List;

public interface ProfessorDAO {
    void add(Professor professor);

    List<Professor> getAll();

    Professor getById(int id);

    Professor getByFullName(String fullName);

    void update(Professor professor);

    void remove(Professor professor);

    Timetable getTimetable(int professorId, Date from, Date till);

    void addLesson(Lesson lesson);
}

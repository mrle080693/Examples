package com.foxminded.universitytimetable.dao.interfaces;

import com.foxminded.universitytimetable.dao.models.Lesson;
import com.foxminded.universitytimetable.dao.models.Professor;
import com.foxminded.universitytimetable.dao.models.Timetable;

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

package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Professor;

import java.util.List;

public interface ProfessorDAO {
    void add(Professor professor);

    List<Professor> getAll();

    Professor getById(int id);

    Professor getByFullName(String fullName);

    void update(Professor professor);

    void remove(Professor professor);
}

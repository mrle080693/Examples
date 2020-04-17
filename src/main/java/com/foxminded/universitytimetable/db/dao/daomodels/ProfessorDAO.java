package com.foxminded.universitytimetable.db.dao.daomodels;

import com.foxminded.universitytimetable.db.models.Professor;

import java.util.List;

public interface ProfessorDAO {
    void add(Professor professor);

    List<Professor> getAll();

    Professor getByFullName(String fullName);

    void update(Professor professor);

    void remove(Professor professor);
}

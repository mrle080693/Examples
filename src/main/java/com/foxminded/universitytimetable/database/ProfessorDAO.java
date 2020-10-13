package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.models.Professor;

import java.util.List;

public interface ProfessorDAO {
    int add(Professor professor);

    List<Professor> getAll();

    Professor getById(int id);

    List<Professor> getBySurname(String surname);

    int update(Professor professor);

    int remove(int professorId);
}

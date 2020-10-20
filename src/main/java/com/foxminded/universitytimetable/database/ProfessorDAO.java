package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.models.Professor;

import java.util.List;

public interface ProfessorDAO {
    Professor add(Professor professor);

    List<Professor> getAll();

    Professor getById(int id);

    List<Professor> getBySurname(String surname);

    Professor update(Professor professor);

    Professor remove(int professorId);
}

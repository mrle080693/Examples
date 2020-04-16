package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Professor;

import java.util.List;

public interface IProfessorDAO {
    // Create
    void add(Professor professor);

    // Read
    List<Professor> getAll();

    Professor getByFullName(String fullName);

    // Update
    void update(Professor professor);

    // Delete
    void remove(Professor professor);
}

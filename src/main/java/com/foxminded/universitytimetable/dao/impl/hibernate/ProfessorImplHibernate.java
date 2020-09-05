package com.foxminded.universitytimetable.dao.impl.hibernate;

import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.models.Professor;

import java.util.List;

public class ProfessorImplHibernate implements ProfessorDAO {
    @Override
    public int add(Professor professor) {
        return 0;
    }

    @Override
    public List<Professor> getAll() {
        return null;
    }

    @Override
    public Professor getById(int id) {
        return null;
    }

    @Override
    public List<Professor> getBySurname(String surname) {
        return null;
    }

    @Override
    public int update(Professor professor) {
        return 0;
    }

    @Override
    public int remove(int professorId) {
        return 0;
    }
}

package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Professor;

import java.util.List;

/*
Можно сделать save() вместо add() и update()
if id = 0 - addQuery
else - updateQuery

Можно сделать перегруженный load()
Без вход. параметров - getAll
Со входящим интом - getById
Со стрингой - getBySurname
 */


public interface ProfessorDAO {
    void add(Professor professor);

    List<Professor> getAll();

    Professor getById(int id);

    List<Professor> getBySurname(String surname);

    void update(Professor professor);

    void remove(Professor professor);
}
